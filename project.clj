(defproject hello-figwheel "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [org.clojure/core.async "0.2.371"]
                 [org.omcljs/om "0.9.0"]
                 [jamesmacaulay/zelkova "0.4.0"]]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-figwheel "0.4.1"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {
              :builds [
                       {:id "dev"
                        :source-paths ["src"]
                        :figwheel { :on-jsload "hello-figwheel.core/on-js-reload" }
                        :compiler {:main hello-figwheel.core
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/hello_figwheel.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :source-map-timestamp true }}

                       {:id "module-2"
                        :source-paths ["src"]
                        :figwheel { :on-jsload "hello-figwheel.module-2/on-js-reload"}
                        :compiler {:main hello-figwheel.module-2
                                   :asset-path "js/compiled/out_module_2"
                                   :output-to "resources/public/js/compiled/module_2.js"
                                   :output-dir "resources/public/js/compiled/out_module_2"
                                   :source-map-timestamp true }}
                       {:id "module-3"
                        :source-paths ["src"]
                        :figwheel { :on-jsload "hello-figwheel.module-3/on-js-reload"}
                        :compiler {:main hello-figwheel.module-3
                                   :asset-path "js/compiled/out_module_3"
                                   :output-to "resources/public/js/compiled/module_3.js"
                                   :output-dir "resources/public/js/compiled/out_module_3"
                                   :source-map-timestamp true }}
                       {:id "min"
                        :source-paths ["src"]
                        :compiler {:output-to "resources/public/js/compiled/hello_figwheel.js"
                                   :main hello-figwheel.core
                                   :optimizations :advanced
                                   :pretty-print false}}]}

  :figwheel {
             ;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             :nrepl-port 7888

             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl"]

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             })
