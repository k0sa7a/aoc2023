(ns aoc2023.day01.part1
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; traverse each character in string and check if it is a numher using regex
;; if character is a number put into a atom-vector
(defn extract-nums
  [str-input]
  (def result (atom []))

  (doseq [element (str/split str-input #"")]
    (if (nil?  (re-matches #"[0-9]" element))
      nil
      (swap! result conj element)
      )
    )
  result
  )

;; get the first and last number from each vector and form an integer
(defn extract-firt-and-last [vect]
  (Integer/parseInt (str/join (conj [] (first @vect)  (last @vect))))
  )

(defn calculate
  []
  ;; read file with input into string
  (def file-str (slurp "/home/k0sa7a/code/k0sa7a/aoc2023/src/aoc2023/day01/input1.csv"))
  ;; split each new line into separate string
  (def split-by-line (str/split-lines file-str))


  (def result (reduce + (map extract-firt-and-last (map extract-nums split-by-line))))
  (println "Result is:" result)
  result
  )

(calculate)
