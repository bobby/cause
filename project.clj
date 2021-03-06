(defproject cause "0.1.0"
            :description "An EDN-like CRDT (Causal Tree) for Clojure(Script) that automatically tracks history and resolves conflicts."
            :url "https://github.com/smothers/cause"

            :license {:name "MIT License"
                      :url "https://opensource.org/licenses/MIT"}

            :dependencies [[org.clojure/clojure "1.10.1" :scope "provided"]
                           [org.clojure/clojurescript "1.10.520" :scope "provided"]
                           [org.clojure/core.async "0.4.500"]
                           [nano-id "0.10.0"]]

            :plugins [[lein-doo "0.1.11"]
                      [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
                      [lein-figwheel "0.5.19"]
                      [metosin/bat-test "0.4.4"]]

            :profiles
            {:dev
             {:main user
              :source-paths ["dev"]
              :dependencies [[org.clojure/test.check "0.10.0"]
                             [pjstadig/humane-test-output "0.10.0"]
                             [walmartlabs/datascope "0.1.1"]
                             [com.taoensso/tufte "2.1.0"]
                             [criterium "0.4.5"]
                             [com.clojure-goes-fast/clj-memory-meter "0.1.2"]
                             [org.clojure/tools.nrepl "0.2.13"]
                             [proto-repl "0.3.1"]
                             [figwheel-sidecar "0.5.19"]
                             [com.bhauman/rebel-readline "0.1.4"]
                             [com.cemerick/piggieback "0.2.2"]]
              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

            :doo {:alias {:browsers [:electron :chrome :firefox]}}

            :bat-test {:fail-fast? true
                       :notify-command ["terminal-notifier" "-title" "Cause tests" "-message"]}

            :aliases {"deploy" ["do" "clean," "deploy" "clojars"]
                      "test" ["do" ["clean"] ["test"]]
                      "cljs-test" ["do" ["doo" "browsers" "browser-test" "once"]]
                      "cljs-test-phantom" ["do" ["doo" "phantom" "browser-test" "once"]]
                      "cljs-test-watch" ["do" ["doo" "browsers" "browser-test" "auto"]]}

            :clean-targets ^{:protect false} ["target"]

            :cljsbuild
            {:builds
             [{:id "browser-test"
               :source-paths ["src" "test"]
               :compiler {:main causal.runner
                          :output-to "target/browser-test/browser-test.js"
                          :output-dir "target/browser-test/out"
                          :optimizations :none
                          :parallel-build true}}
              {:id "dev"
               :figwheel {:open-urls ["http://localhost:3449"]}
               :source-paths ["src"]
               :compiler {:main causal.core
                          :asset-path "js/out"
                          :output-to "resources/public/js/main.js"
                          :output-dir "resources/public/js/out"
                          :verbose false
                          :optimizations :none
                          :cache-analysis true
                          :source-map true}}]})
