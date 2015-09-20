(ns hello-figwheel.render.canvas-renderer
  (:require
   [hello-figwheel.render.eye-renderer-protocol :as impl]
   [hello-figwheel.point :as point]))

;; --- util --------------------------
(defn draw-circle [cxt [x y] r color & stroke?]
  (if stroke?
    (doto cxt
      .beginPath
      (aset "strokeStyle" color)
      (.arc x y r 0 (* 2 js/Math.PI))
      .stroke)
    (doto cxt
      .beginPath
      (aset "fillStyle" color)
      (.arc x y r 0 (* 2 js/Math.PI))
      .fill)))

(defn draw-ellipse [cxt [x y] r s]
  (doto cxt
    .save
    (.translate x y)
    (.scale s 1)
    .beginPath
    (.arc 0 0 r 0 (* 2 js/Math.PI))
    .restore
    (aset "fillStyle" "red")
    .fill))




(defn- clean-context [{:keys [cxt canvas]}]
  (let [w (.-width canvas)
        h (.-height canvas)]
    (.clearRect cxt 0 0 w h)))

(defn- render-eye [{cxt :cxt}
                   {:keys [eye-center eye-radius pupil-pos pupil-radius factor]}]
  "This method specific for canvas renderer only"
  (draw-ellipse cxt eye-center (+ eye-radius pupil-radius) (/ 1 factor))
  (draw-circle cxt (point/add eye-center pupil-pos) pupil-radius "blue"))

;;--- canvas renderer ---------------------------------------
(defrecord CanvasRenderer [^String canvas-nameи
                           canvas
                           cxt]
  impl/IEyesRenderer
  (render-eyes [this [left-eye-model right-eye-model]]
    (render-eye this left-eye-model)
    (render-eye this right-eye-model)))


(defn create-canvas-renderer [^String canvas-name]
  (let [canvas (.getElementById js/document canvas-name)
        cxt (.getContext canvas "2d")]
    (CanvasRenderer. canvas-name canvas cxt)))
