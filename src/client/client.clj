(ns client.client
  (:require [org.httpkit.client :as http]
            [cache.cache :as ch]
            [diehard.core :as dh]
            [conf.config :as cfg]))

(defn options [address, api-key]
  {:timeout 2000
   :query-params {:address address
                  :key api-key}})

(def retry-opts {:retry-on nil
                 :max-retries cfg/retry-num
                 :delay-ms cfg/delay-ms})

(def url "https://maps.googleapis.com/maps/api/geocode/json")

(defn send-request [address]
  (http/get url  (options address cfg/api-key)
            (fn [{:keys [status headers body]}]
              (if (= 200 status)
                (do
                  (ch/cache-response address body)
                  body)))))

(defn get-response[address]
  (dh/with-retry retry-opts (send-request address)))

