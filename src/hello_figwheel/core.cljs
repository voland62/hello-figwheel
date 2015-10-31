(ns hello-figwheel.core
    (:require
     [hello-figwheel.zelkova-based-eyes :as modul-1]
     [hello-figwheel.core-async-based-eyes :as modul-2]
     ))

(enable-console-print!)

(println "Hi. This is an eye-apps page - eye widgets than use different FRP frameworks")

;; ------------------------------------------
(def aaa 23)

;; --- infrastructure --------------------------------------------
(defn setup []
  (modul-1/setup "canvas")
  (modul-2/setup "canvas-2"))

(defn teardown []
  (modul-1/teardown)
  (modul-2/teardown))

(teardown)
(setup)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (println "Hello Andrew from hello-figwheel.core")
  (teardown)
  (setup))
