(ns paste.routes
  (:require [muuntaja.core :as m]
            [paste.handler :as handle]
            [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja :refer [format-request-middleware
                                                     format-response-middleware
                                                     format-negotiate-middleware]]))

(def app (ring/ring-handler
          (ring/router [["/" {:get {:handler handle/index}}]
                        ["/pastes" {:post {:handler handle/new-paste-handler}}]
                        ["/js/*" {:get {:handler (ring/create-resource-handler {:root "js"})}}]
                        ["/css/*" {:get {:handler (ring/create-resource-handler {:root "css"})}}]
                        ["/:id" {:get {:handler handle/paste} :conflicting true}]]
                  
                       {:data {:muuntaja m/instance
                               :middleware [format-request-middleware
                                            format-response-middleware
                                            format-negotiate-middleware]}
                        :conflicts nil})
            (ring/create-default-handler)))