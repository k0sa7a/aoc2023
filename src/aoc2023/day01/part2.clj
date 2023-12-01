(ns aoc2023.day01.part2
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(def test1 "eightwothree")

(def digits ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])
(def digits-vals {"one" 1, "two" 2, "three" 3, "four" 4, "five" 5, "six" 6, "seven" 7, "eight" 8, "nine" 9})



(str/index-of test1 "nine")

;; helper function to get the integers of the string representations of the digits
(defn transform-digits
  [input-set]
  (def accumulated (atom []))

  (doseq [digit-hash input-set]

    (let [[k v] (first digit-hash)]
      (swap! accumulated conj {k (get digits-vals v)})))
  @accumulated)

;; checking the starting positions and values (represented as strings still) of digits within a string
(defn check-digits
  [input-string]
  (def accumulated (atom []))
  (doseq [digit (seq digits)]




    (loop [i 0]
      (when (< i (count input-string))
        (let [index (str/index-of input-string digit i)]
          ;; (println "i is" i)
          ;; (println "digit is" digit)
          ;; (println "index is" index)
          ;; (println "accumulated is" @accumulated)



          (if (not (nil? index))
            (swap! accumulated conj {index digit}))

          (recur (inc i))))))



  (transform-digits (into #{} @accumulated))
  )





(check-digits test1)
