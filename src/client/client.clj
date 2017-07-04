(ns client.client
  (:require [org.httpkit.client :as http]
            [cache.cache :as ch]
            [diehard.core :as dh]
            [conf.config :as cfg]
            [clojure.tools.logging :as log]))

(defn options [address, api-key]
  {:as      :text
   :timeout 2000
   :query-params {:address address
                  :key api-key}})

(def retry-opts {:retry-on nil
                 :max-retries cfg/retry-num
                 :delay-ms cfg/delay-ms})

(def url "https://maps.googleapis.com/maps/api/geocode/json")

(defn get-and-cache-response [address]
  (http/get url  (options address cfg/api-key)
            (fn [{:keys [status headers body]}]
              (log/info "Request status -- " status)
              (if (= 200 status)
                (do
                  (ch/cache-response address body)
                  body)))))

(defn with-random-lags [address]
  (let [lag (rand-int cfg/lag-bound)]
    (log/info "lag -- " lag)
    (Thread/sleep lag)
    (get-and-cache-response address)))

(defn get-response-with-retries[address]
  (dh/with-retry retry-opts (with-random-lags address)))

