(ns transit-perf.read-handler
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.string :as str]
            [cognitect.transit :as t]
            [criterium.core :as c]
            [transit-schema.core :as ts])
  (:import [java.io ByteArrayInputStream]))

(defn gen-msg
  "Generates a Transit message with n keywords."
  [n]
  {:msg (.getBytes (str "[" (str/join "," (repeat n "\"~:foo\"")) "]"))
   :n n})

(defn read-msg [msg]
  (t/read (t/reader (ByteArrayInputStream. msg) :json
                    {:handlers ts/read-handlers})))

(defn bench [{:keys [msg n]}]
  (let [res (c/quick-benchmark (read-msg msg) {})]
    {:mean (first (:mean res))
     :lower-q (first (:lower-q res))
     :upper-q (first (:upper-q res))
     :res res
     :n n}))

(defn profile [{:keys [msg]}]
  (dotimes [_ 100000] (read-msg msg)))

(defn bench-all []
  (->> (concat #_(range 0 10) (range 200 400 10))
       (map gen-msg)
       (map bench)))

(defn write-res [res file]
  (with-open [out-file (io/writer file)]
    (let [data (map #(map % [:n :mean :lower-q :upper-q]) res)]
      (csv/write-csv out-file data :separator \space))))

(defn -main [& args]
  (println "Start Benchmark...")

  (-> (bench-all)
      (write-res "switch-ts-2.csv"))

  (println "Finished!"))

