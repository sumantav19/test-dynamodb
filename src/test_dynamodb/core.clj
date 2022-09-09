(ns test-dynamodb.core
  (:require [taoensso.faraday :as far]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def client-opts
           {:access-key "" ;; required
            :secret-key "" ;; required
            :endpoint "http://localhost:8000"})

(far/list-tables client-opts)


;; Create table which records the number of site visits of a site-url

(far/create-table client-opts :site-visits
                  [:site-url :s]
                  {:throughput {:read 1 :write 1}
                   :block? true 
                   })

(far/put-item client-opts :site-visits {:site-url "google.com"  :visits  0 })

(far/get-item client-opts :site-visits {:site-url "google.com"})

(far/update-item client-opts :site-visits {:site-url     "google.com"}
                 {:update-expr "SET visits = visits + :inc"
                  :expr-attr-vals {":inc" 1}
                  :return :updated-new})