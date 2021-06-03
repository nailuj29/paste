(ns paste.handler
  (:require [paste.html :as html]
            [paste.utils :refer [create-key paste-dir join-paths]]
            [clojure.string :as string])
  (:import [java.io FileNotFoundException]))

(defn new-paste-handler
  [req]
  (let [syntax (get (:headers req) "x-syntax")
        content (-> req
                    :body
                    slurp
                    (string/replace "&" "&amp;")
                    (string/replace "<" "&lt;")
                    (string/replace ">" "&gt;"))
        key (create-key)]
    (spit (join-paths paste-dir key) content)
    (spit (join-paths paste-dir (str key ".syntax")) syntax)
    {:status 200
     :body key}))

(defn paste
  [req]
  (let [base-path (join-paths paste-dir (:id (:path-params req)))]
    (try
      {:status 200
       :body (html/paste (slurp base-path) (slurp (str base-path ".syntax")))}
      (catch FileNotFoundException _
        {:status 404
         :body "Paste not found"}))))

(defn raw
  [req]
  (let [base-path (join-paths paste-dir (:id (:path-params req)))]
    (try
      {:status 200
       :headers {"Content-Type" "text/plain; charset=utf8"}
       :body (slurp base-path)}
      (catch FileNotFoundException _
        {:status 404
         :body "Paste not found"}))))

(defn index
  [_]
  {:status 200
   :body (html/index)})