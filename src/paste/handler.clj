(ns paste.handler
  (:require [paste.html :as html]))

(defn new-paste-handler
  [req]
  {:status 200
   :body (get (:headers req) "content-type")})

(defn index
  [_]
  {:status 200
   :body (html/index)})