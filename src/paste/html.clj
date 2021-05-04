(ns paste.html
  (:require [hiccup.core :refer [html]]
            [paste.utils :refer [languages]]))

(def datalist
  [:datalist#languages
   (for [language languages]
     [:option {:value language}])])

(defn template
  [body]
  [:html
   [:head
    [:link {:rel "stylesheet" :href "/css/prism.css"}]
    [:meta {:charset "UTF-8"}]]
   [:body body
    [:form
     datalist
     [:label {:for "language"} "Language:"]
     [:input#language {:list "languages"}]]
    [:script {:src "/js/prism.js"} ""]
    [:script {:src "/js/main.js"} ""]]])


(defn index
  []
  (str "<!DOCTYPE html>\n" (html {:mode :pretty} (template [:h1 "Hello there"]))))
