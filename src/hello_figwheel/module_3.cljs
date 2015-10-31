(ns hello-figwheel.module-3
    (:require
     [hello-figwheel.core-async-based-eyes-3 :as m]
     ))

(enable-console-print!)

(println "This is testing entry point for module-3(core.async based)")

;; --- we need this to first setup. this is called only once on startup.
;; other refresh - from on-js-reload. this ns is not reloading on every other ns change!
(m/teardown)
(m/setup "canvas-3")

(defn on-js-reload []
  (println "Hello Andrew from hello-figwheel.module-3")
  (m/teardown)
  (m/setup "canvas-3"))
