(ns hello-figwheel.module-2
    (:require
     [hello-figwheel.core-async-based-eyes :as m2]
     ))

(enable-console-print!)

(println "This is testing entry point for module-2(core.async based)")

;; ------------------------------------------
(def aaa 23)

;; --- infrastructure --------------------------------------------

(m2/teardown)
(m2/setup "canvas-2")
