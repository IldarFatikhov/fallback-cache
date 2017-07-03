(ns server.server
  (:require [org.httpkit.server :as http-kit]
            [compojure.route :refer (not-found)]
            [compojure.handler :refer (site)]
            [compojure.core :refer (defroutes GET context)]
            [cache.cache :as cache]
            [client.client :as client]
            [conf.config :as cfg]))

(defn with-timeout [timeout-ms callback or-else]
  (let [fut (future (callback))
        ret (deref fut timeout-ms ::timed-out)]
    (if (= ret ::timed-out)
      (or-else)
      ret)))

(defn ask [address]
  (println address)
  (with-timeout cfg/response-timeout-ms
    #(client/get-response address)
    #(cache/retrieve-cached-response address)))

(defroutes routes
  (GET "/geocode" [address] (ask address))
  (not-found "<p>Page not found.</p>"))

(defn start-me []
  (println "started on port:" cfg/port)
  (http-kit/run-server (site #'routes) {:port cfg/port}))
