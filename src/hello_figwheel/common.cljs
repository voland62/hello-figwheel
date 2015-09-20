(ns hello-figwheel.common
  (:require [hello-figwheel.point :as point]))


(defn element-rect [element-name]
  (-> (.getElementById js/document element-name)
      (.getBoundingClientRect)))

(defn calc-pos-round [center mouse r]
  "this is a pure function that calculates position of the
   pupil in coordinates of the center. This is for round eye"
  (if (< (point/distance mouse center) r)
    mouse
    (-> mouse
        (point/sub center)
        (point/norm r)
        (point/add center))))

(defn calc-pos-ellipse [center mouse r f]
  "this is pure function for calc position of pupil for oval eye
   in coordinates of eye center - relative, not absolute position"
  (let [[mx my :as mouse-v] (point/sub mouse center)
        mouse-modified [(* mx f) my]
        [px py :as pupil-modified] (point/norm mouse-modified r)]
    [(/ px f) py]))
