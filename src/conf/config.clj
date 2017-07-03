(ns conf.config
  (:require [aero.core :as aero :refer (read-config root-resolver resource-resolver)]
            [clojure.java.io :as io]))

(def config
  (aero/read-config (io/resource 
                     "config.edn" )))

(def response-timeout-ms 
  (:response-timeout-ms config))

(def port
  (:port config))

(def retry-num
  (get-in config [:retry :retry-num]))

(def delay-ms
  (get-in config [:retry :delay-ms]))

(def db-host
  (get-in config [:db :host]))

(def db-port
  (Integer/parseInt (get-in config [:db :port])))

(def db-timeout
  (get-in config [:db :timeout]))

(def api-key
  (:api-key config))
