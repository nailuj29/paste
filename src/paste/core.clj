(ns paste.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]]
            [paste.routes :refer [app]]
            [paste.utils :refer [paste-dir]]
            [clojure.java.io :as io]))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main []
  (.mkdir (io/file paste-dir))
  (println "Server at port 8080")
  (reset! server (run-server app {:port 8080})))

(comment
  @server
  (stop-server)
  (-main))

