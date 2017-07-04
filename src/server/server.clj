(ns server.server
  (:require [org.httpkit.server :as http-kit]
            [compojure.route :refer (not-found)]
            [compojure.handler :refer (site)]
            [compojure.core :refer (defroutes GET context)]
            [cache.cache :as cache]
            [client.client :as client]
            [conf.config :as cfg]
            [cache.cache :as ch]
            [clojure.tools.logging :as log]))

(defn with-timeout [timeout-ms callback or-else]
  (let [fut (future (callback))
        ret (deref fut timeout-ms ::timed-out)]
    (if (= ret ::timed-out)
      (or-else)
      ret)))

(defn ask [address]
  (log/info address)
  (with-timeout
    cfg/response-timeout-ms
    #(client/get-response-with-retries address)
    #(cache/retrieve-cached-response address)))

(defn cache-first-ask [address]
  (log/info address)
  (let [cached-result (cache/retrieve-cached-response address)]
    (client/get-response-with-retries address)
    cached-result))

(defroutes routes
  (GET "/geocode" [address] (ask address))
  (GET "/cache-first/geocode" [address] (cache-first-ask address))
  (not-found "Page not found."))

(defn start-me []
  (log/info "started on port:" cfg/port)
  (http-kit/run-server (site #'routes) {:port cfg/port}))
