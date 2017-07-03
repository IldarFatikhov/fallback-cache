(ns cache.cache
  (:require [taoensso.carmine :as car :refer (wcar)]
            [conf.config :as cfg]))

(def server-connection {:pool {}
                         :spec  {:host    cfg/db-host
                                 :port    cfg/db-port
                                 :timeout cfg/db-timeout}})

(defn cache-response [key value]
  (future (wcar server-connection (car/set key value))))

(defn retrieve-cached-response [address]
  (wcar server-connection (car/get address)))

