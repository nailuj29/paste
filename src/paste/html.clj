(ns paste.html
  (:require [hiccup.core :refer [html]]
            [paste.utils :refer [languages]]))

(def datalist
  [:datalist#languages
   (for [language languages]
     [:option {:value language}])])

(defn paste
  [content language]
  (str "<!DOCTYPE html>\n"
       (html {:mode :html}
             [:html
              [:head
               [:link {:rel "stylesheet" :href "/css/prism.css"}]
               [:link {:rel "stylesheet" :href "/css/paste.css"}]
               [:meta {:charset "UTF-8"}]]
              [:body
               [:pre
                [:code {:class (str "language-" language)} content]]
               [:script {:src "/js/prism.js"} ""]]])))

(defn index
  []
  (str "<!DOCTYPE html>\n"
       (html {:mode :html}
             [:html
              [:head
               [:link {:rel "stylesheet" :href "/css/main.css"}]
               [:meta {:charset "UTF-8"}]]
              [:body [:h1 "Paste"]
               [:form#paste-form
                datalist
                [:label {:for "content"} "Content"]
                [:textarea#content {:placeholder "Content goes here..."
                                    :rows 24 :cols 80} ""] ; TODO: this is bad, maybe submit a PR to hiccup
                [:label {:for "language"} "Language"]
                [:input#language {:list "languages" :placeholder "Language"}]
                [:input {:type "Submit" :content "Submit"}]]

               [:script {:src "/js/main.js"} ""]]])))
