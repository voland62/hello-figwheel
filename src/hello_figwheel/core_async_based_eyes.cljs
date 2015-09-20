(ns hello-figwheel.core-async-based-eyes
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as async
             :refer [>! <! put! chan alts! close!]]
            [hello-figwheel.common :as common]
            [goog.events :as events]
            ;;[hello-figwheel.point :as point]
            [hello-figwheel.render.eye-renderer-protocol :as impl]
            [hello-figwheel.render.canvas-renderer :as canvas-renderer])
  (:import [goog.events EventType]))

(enable-console-print!)

;;--- util -------------------------------------------
(defn events->chan
  "Given a target DOM element and event type return a channel of
  observed events. Can supply the channel to receive events as third
  optional argument."
  ([el event-type] (events->chan el event-type (chan)))
  ([el event-type c]
   (events/listen el event-type
                  (fn [e] (put! c e)))
   c))

(defn mouse-loc->vec
  "Given a Google Closure normalized DOM mouse event return the
  mouse x and y position as a two element vector."
  [e]
  [(.-clientX e) (.-clientY e)])

;;--- business ----------------------------------------

#_(
   Что если мы создадим модельный канал - канал из которого будет заберать - рендер-процесс
   Здесь мы просто делаем в один)


(defn do-stuff [ch renderer]
  (go
    (loop []
      (if-let [eye-model (<! ch)]
        (do
          ;;(println (str "cool " eye-model))
          (canvas-renderer/clean-context renderer)
          (impl/render-eyes renderer eye-model)
          (recur))
        (println "exit loop cause somebody closed channel")))))




(defn create-model-tranceducer [[x y]]
  (let [d 20
        left-eye-center [(- x d) y]
        right-eye-center [(+ x d) y]
        factor 2
        eye-radius 20
        pupil-radius 10]
    (comp  (map mouse-loc->vec)
           (map (fn [pos]
                  (let [left-pupil-pos (common/calc-pos-ellipse left-eye-center pos eye-radius factor)
                        right-pupil-pos (common/calc-pos-ellipse right-eye-center pos eye-radius factor)]
                    [
                     {:eye-center left-eye-center :pupil-pos left-pupil-pos
                      :eye-radius eye-radius
                      :pupil-radius pupil-radius
                      :factor factor}
                     {:eye-center right-eye-center :pupil-pos right-pupil-pos
                      :eye-radius eye-radius
                      :pupil-radius pupil-radius
                      :factor factor}]))))))

;;--- setup ----------------------------------------------
(defn setup [id]
  ;; It's a question: should it be broadcaster ?
  (def element (.getElementById js/document id))
  (def model-chan (chan 1 (create-model-tranceducer [100 50])))
  (events->chan element EventType.MOUSEMOVE model-chan)
  (def canvas-renderer (canvas-renderer/create-canvas-renderer id))
  (do-stuff model-chan canvas-renderer))


(defn teardown []
  (if element (events/removeAll element))
  (if canvas-renderer (canvas-renderer/clean-context canvas-renderer))
  (if model-chan (close! model-chan)))
