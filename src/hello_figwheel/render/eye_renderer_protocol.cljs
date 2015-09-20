(ns hello-figwheel.render.eye-renderer-protocol)

(defprotocol IEyesRenderer
  (render-eyes [this eye-model] ))
