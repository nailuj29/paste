(ns paste.utils
  (:require [clojure.string :refer [join]]
            [clojure.data.json :as json]))

(defn join-paths [& paths]
  (join (System/getProperty "file.separator") paths))

(def paste-dir
  (join-paths (System/getProperty "user.home") ".pastes"))

(defn- extract-names
  [[k v]]
  (concat [(name k)] (if (string? (:alias v)) 
                      [(:alias v)]
                      (:alias v))))

(defn- process-prism-components
  []
  (let [prism-components-json (slurp "https://raw.githubusercontent.com/PrismJS/prism/master/components.json")
        prism-components (json/read-str prism-components-json
                                           :key-fn keyword)
        prism-languages (:languages prism-components)]
    (mapcat extract-names prism-languages)))

(def languages (process-prism-components))

(def alpha-num "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

(defn create-key
  []
  (apply str (repeatedly 8 #(rand-nth alpha-num))))