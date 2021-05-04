(ns paste.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]]
            [paste.routes :refer [app]]))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main []
  (println "Server at port 8080")
  (reset! server (run-server app {:port 8080})))

(comment
  @server
  (stop-server)
  (-main))
