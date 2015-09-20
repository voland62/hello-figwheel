(ns hello-figwheel.point)

(defn add [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn sub [[x1 y1] [x2 y2]]
  [(- x1 x2) (- y1 y2)])

(defn mul [[x1 y1] k]
  [(* x1 k) (* y1 k)])

(defn length [[x y]]
  (Math/sqrt (+ (* x x) (* y y))))

(defn distance [a b]
  (length (sub a b)))

(defn norm [[x y :as v] d]
  (let [d-orig (length v)
        k (/ d d-orig)]
    [(* k x) (* k y)]))
