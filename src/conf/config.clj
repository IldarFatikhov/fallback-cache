(ns conf.config
  (:require [aero.core :as aero :refer (read-config root-resolver resource-resolver)]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]))

(def config
  (aero/read-config (io/resource "config.edn" )))

(def response-timeout-ms 
  (:response-timeout-ms config))

(def port
  (let [server-port (:port config)]
    (if (nil? server-port)
      (log/error "Server-port is not defined"))
    server-port))

(def retry-num
  (let [retry (get-in config [:retry :retry-num])]
    (if (nil? retry)
      (log/error "Retry count is not defined"))
    retry))

(def delay-ms
  (let [delay (get-in config [:retry :delay-ms])]
    (if (nil? delay)
      (log/error "Delay is not defined"))
    delay))

(def db-host
  (let [host (get-in config [:db :host])]
    (if (nil? host)
      (log/error "Db host is not defined"))
    host))

(def db-port
  (let [port (get-in config [:db :port])]
    (if (nil? port)
      (log/error "Db port is not defined"))
  (Integer/parseInt port)))

(def db-timeout
  (let [timeout (get-in config [:db :timeout])]
    (if (nil? timeout)
      (log/error "Db timeout is not defined"))
    timeout))

(def api-key
  (let [key (:api-key config)]
    (if (nil? key)
      (log/error "Key is not defined"))
    key)) 

(def lag-bound
  (let [lag-bound (:lag-bound config)]
    (if (nil? lag-bound)
      (log/error "Lag bound is not defined"))
    lag-bound))
