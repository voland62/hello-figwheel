(ns hello-figwheel.render.svg-renderer
  (:require [hello-figwheel.render.eye-renderer-protocol :as impl]))

(defn- render-eye-svg [{:keys [g-svg eye-ball-svg pupil-svg]}
                       {eye-radius :eye-radius pupil-radius :pupil-radius factor :factor
                        [pupil-x pupil-y] :pupil-pos
                        [x y] :eye-center}]
  (.setAttribute g-svg "transform" (str "translate(" x " " y ")"))
  (doto eye-ball-svg
    (.setAttribute "ry" eye-radius)
    (.setAttribute "rx" (/ eye-radius factor)))
  (doto pupil-svg
    (.setAttribute "cx" pupil-x)
    (.setAttribute "cy" pupil-y)
    (.setAttribute "r" pupil-radius)))


(defrecord SVGRenderer [svg-element
                        left-eye-svg
                        right-eye-svg]
  impl/IEyesRenderer
  (render-eyes [this [left-eye-model right-eye-model]]
    (render-eye-svg left-eye-svg left-eye-model)
    (render-eye-svg right-eye-svg right-eye-model)))




(defn- create-eye-svg [svg]
  (let [g (.createElementNS js/document
                                "http://www.w3.org/2000/svg"
                                "g")
        eye-ball (.createElementNS js/document
                                   "http://www.w3.org/2000/svg"
                                   "ellipse")
        pupil (.createElementNS js/document
                                "http://www.w3.org/2000/svg"
                                "circle")]
    (.setAttribute eye-ball "fill" "red")
    (.setAttribute pupil "fill" "blue")
    (.appendChild g eye-ball)
    (.appendChild g pupil)
    (.appendChild svg g)
    {:g-svg g :eye-ball-svg eye-ball :pupil-svg pupil}))



(defn create-svg-renderer [^String svg-name]
  (let [svg (.getElementById js/document svg-name)
        left-eye-svg (create-eye-svg svg)
        right-eye-svg (create-eye-svg svg)]
    (SVGRenderer. svg left-eye-svg right-eye-svg)))

;--- investigate if the element will be garbage collected after this invocation and father teardown in main thread..
(defn clean [{svg-element :svg-element
              {g-left :g-svg} :left-eye-svg
              {g-right :g-svg} :right-eye-svg}]
  (.removeChild svg-element g-left)
  (.removeChild svg-element g-right))
