(ns core.core
  (:require [server.server :as serv]
            [client.client :as clnt])
  (:gen-class))

(defn -main[& args]
  (serv/start-me))
