(ns transit-perf.reader-factory
  (:require [clojure.string :as str]
            [cognitect.transit :as t])
  (:import [java.io ByteArrayOutputStream ByteArrayInputStream]))

(def msg
  (let [msg {:key-1 "value-1"
             :key-2 45345345
             :key-3 :value-3
             :key-4 (str/join " " (repeat 10 "Longer text is here."))}
        out (ByteArrayOutputStream.)]
    (t/write (t/writer out :json) msg)
    (.toByteArray out)))

(def handlers
  (->> (for [n (range 20)]
         [(str "some.longer.tagname." n) (t/read-handler identity)])
       (into {})))

(defn read-msg [msg]
  (t/read (t/reader (ByteArrayInputStream. msg) :json {:handlers handlers})))

(defn profile [msg]
  (dotimes [_ 100000] (read-msg msg)))

(comment

  (read-msg msg)
  (profile msg)

  )
