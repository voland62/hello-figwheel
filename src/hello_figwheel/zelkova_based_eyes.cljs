(ns hello-figwheel.zelkova-based-eyes
  (:require [jamesmacaulay.zelkova.signal :as z]
            [jamesmacaulay.zelkova.time :as time]
            [jamesmacaulay.zelkova.mouse :as mouse]
            [jamesmacaulay.zelkova.keyboard :as keyboard]
            [cljs.core.async :as async]
            [hello-figwheel.point :as point]
            [hello-figwheel.common :as common]
            [hello-figwheel.render.eye-renderer-protocol :as impl]
            [hello-figwheel.render.canvas-renderer :as canvas-renderer]))

;;-----------------------------
(defn create-mouse-pos-stream [shift]
  (z/map #(point/sub % shift) mouse/position))

(defn create-pupil-position-stream [center r f shift]
  (z/map
   (fn [mouse] (common/calc-pos-ellipse center mouse r f))
   (create-mouse-pos-stream shift)))


(defn create-eye-model-stream [eye-center eye-radius factor shift]
  "this is a stream that holds (return) whole eye model"
  (let [pupil-positon-stream (create-pupil-position-stream eye-center eye-radius factor shift)]
    (z/map
     (fn [pupil-pos] {:eye-center eye-center :pupil-pos pupil-pos
                      :eye-radius eye-radius
                      :pupil-radius 10
                      :factor factor})
     pupil-positon-stream)))



(defn create-eyes-model-stream [[x y] eye-radius factor shift]
  "This constructor returns inactive graph of the whole 'eyes' stream
   it shouldn't depend on any outside values"
  (let [d 30
        left-eye-center [(- x d) y]
        right-eye-center [(+ x d) y]
        left-eye-model-stream (create-eye-model-stream left-eye-center eye-radius factor shift)
        right-eye-model-stream (create-eye-model-stream right-eye-center eye-radius factor shift)]
    (z/combine [left-eye-model-stream right-eye-model-stream])))


;; --- business ----------------------------------
#_(
   as soon as our model depends on positon of render element
      I can't avoid knowledge about position of such element...
      I dont know how to do better777
   )

;;--- Some imperative(statefull) setup -------------------------
(declare canvas-renderer)

(defn render-eyes-by-canvas-renderer [eyes-model]

  (canvas-renderer/clean-context canvas-renderer)
  (impl/render-eyes canvas-renderer eyes-model))


;;--- TODO: I kill and reinit stream here. Does it actually garbage collected? ---
(defn setup [element-name]
  (def canvas-shift [(.-left (common/element-rect element-name))
                     (.-top (common/element-rect element-name))])
  (def input
    (z/sample-on
     (time/fps 25)
     (create-eyes-model-stream [100 70] 30 2 canvas-shift)))

  (def canvas-renderer (canvas-renderer/create-canvas-renderer element-name))

  (def main
    (z/map
     (fn [eyes-model]
       (render-eyes-by-canvas-renderer eyes-model))
     input))

  (def spawned (z/spawn main))
  )

(defn teardown []
  (if canvas-renderer (canvas-renderer/clean-context canvas-renderer))
  (if spawned (async/close! spawned))
  )
