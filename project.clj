(defproject fallback-cache "1.0.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.2.0"]
                 [com.taoensso/carmine "2.16.0"]
                 [compojure "1.6.0"]
                 [ring/ring-core "1.6.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [diehard "0.5.2"]
                 [aero "1.1.2"]
                 [org.clojure/tools.logging "0.4.0"]
                 [log4j/log4j "1.2.17"]]
  :main core.core)
