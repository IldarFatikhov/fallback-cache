(ns cache.cache
  (:require [taoensso.carmine :as car :refer (wcar)]
            [conf.config :as cfg]
            [clojure.tools.logging :as log]))

(def server-connection {:pool {}
                        :spec {:host    cfg/db-host
                               :port    cfg/db-port
                               :timeout cfg/db-timeout}})

(defn cache-response [key value]
  (log/info "Info by key \"" key "\"added to cache")
  (future (wcar server-connection (car/set key value))))

(defn retrieve-cached-response [address]
  (log/info "Info by key " address "retrieved from cache")
  (wcar server-connection (car/get address)))

