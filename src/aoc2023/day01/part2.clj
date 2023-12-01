(ns aoc2023.day01.part2
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(def test1 "two1nine")

(def digits ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])
(def digits-vals {"one" 1, "two" 2, "three" 3, "four" 4, "five" 5, "six" 6, "seven" 7, "eight" 8, "nine" 9})

(def numbers ["1" "2" "3" "4" "5" "6" "7" "8" "9"])
(def numbers-vals {"1" 1, "2" 2, "3" 3, "4" 4, "5" 5, "6" 6, "7" 7, "8" 8, "9" 9})

(str/index-of test1 "nine")

;; helper function to transform the string number values into integers
(defn transform-digits
  [input-set hash-map-for-check]
  (def accumulated (atom []))

  (doseq [digit-hash input-set]

    (let [[k v] (first digit-hash)]
      (swap! accumulated conj {k (get hash-map-for-check v)})))
  @accumulated)



;; checking the starting index and values (represented as strings still) of digits (numbers and substrings) within a string
(defn check-digits
  [input-string sequence-for-check hash-map-for-check]
  (def accumulated (atom []))
  (doseq [digit (seq sequence-for-check)]




    (loop [i 0]
      (when (< i (count input-string))
        (let [index (str/index-of input-string digit i)]

          (if (not (nil? index))
            (swap! accumulated conj {index digit}))

          (recur (inc i))))))



  (transform-digits (into #{} @accumulated) hash-map-for-check)
  )


;; extract both word and integer digits from a string and get the final integer from first and last digit
(defn get-first-last-digit [input-string]
  (def result (atom []))
  (def step1 (into [] (check-digits input-string digits digits-vals)))
  (def step2 (into step1 (check-digits input-string numbers numbers-vals)))
  (def step3 (into [] (sort-by first step2)))
  (println step2)


  ;; (apply max (apply concat (map keys step2)))
  ;; the above needed refactoring using ->>

  (def max-ind
    (->> step3
         (map keys)
         (apply concat)
         (apply max)))

  (def min-ind
    (->> step3
         (map keys)
         (apply concat)
         (apply min)))
  (println "min index is" min-ind)
  (println "max index is" max-ind)

  (doseq [map-with-numbers step3]
    (println "Map with numbers:" map-with-numbers)
    ;; (println "MIN:" (get map-with-numbers min-ind))
    ;; (println "MAX:" (get map-with-numbers max-ind))



    (if (contains? map-with-numbers min-ind)
      (swap! result conj (map-with-numbers min-ind)))

    (if (contains? map-with-numbers max-ind)
      (swap! result conj (map-with-numbers max-ind))))

    (->> @result
         (map str)
         (apply str)
         (Integer/parseInt)))

(defn calculate
  []
  ;; read file with input into string
  (def file-str (slurp "/home/k0sa7a/code/k0sa7a/aoc2023/src/aoc2023/day01/input2.csv"))
  ;; split each new line into separate string
  (def split-by-line (str/split-lines file-str))


  (def result (reduce + (map get-first-last-digit split-by-line)))
  (println "Result is" result)
  result
  )

;; (get-first-last-digit "7pqrstsixteen")
(calculate)
;; (map get-first-last-digit ["two1nine" "eightwothree" "abcone2threexyz" "xtwone3four" "4nineeightseven2" "zoneight234" "7pqrstsixteen"])
