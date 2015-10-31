(ns hello-figwheel.module-2
    (:require
     [hello-figwheel.core-async-based-eyes :as m2]
     ))

(enable-console-print!)

(println "This is testing entry point for module-2(core.async based)")

;; ------------------------------------------
(def aaa 23)

;; --- we need this to first setup. this is called only once on startup.
;; other refresh - from on-js-reload. this ns is not reloading on every other ns change!
(m2/teardown)
(m2/setup "canvas-2")

(defn on-js-reload []
  (println "Hello Andrew from hello-figwheel.module-2")
  (m2/teardown)
  (m2/setup "canvas-2"))
