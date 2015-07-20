(defproject transit-perf "0.1-SNAPSHOT"
  :description "Transit Performance Evaluation"
  :url "https://github.com/alexanderkiel/transit-perf"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [com.cognitect/transit-clj "0.8.275"]
                 ;[com.cognitect/transit-java "dev"]
                 [org.clojars.akiel/transit-schema "0.1-SNAPSHOT"]
                 [criterium "0.4.3"]])
