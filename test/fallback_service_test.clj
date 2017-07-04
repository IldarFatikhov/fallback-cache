(ns fallback-service-test
  (:require
   [server.server :as serv]
   [clojure.test :as test]))

(def time-limit 1000)

(defn slow[]
  (Thread/sleep (+ time-limit 1000))
  "slow")

(defn fast[]
  (Thread/sleep (- time-limit 500))
  "fast")

(defn fastest[]
  "fast")

(test/testing "Should return result of function that executes faster than time limit"
  (test/is (= "fast" (serv/with-timeout time-limit #(slow) #(fast)))))

(test/testing "If both functions satisfy the time limit, then should return result of the first one"
  (test/is (= "fast" (serv/with-timeout time-limit #(fast) #(fastest)))))

