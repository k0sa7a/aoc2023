(ns aoc2023.day01.part2
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(def test1 "abcone2threexyz")

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
  (def step1 (into [] (check-digits test1 digits digits-vals)))
  (def step2 (into step1 (check-digits test1 numbers numbers-vals)))

  (println step2)


  ;; (apply max (apply concat (map keys step2)))
  ;; the above needed refactoring using ->>

  (def max-ind
    (->> step2
         (map keys)
         (apply concat)
         (apply max)))

  (def min-ind
    (->> step2
         (map keys)
         (apply concat)
         (apply min)))

  (doseq [map-with-numbers step2]
    (println map-with-numbers)
    (println (get map-with-numbers min-ind))

    (if (contains? map-with-numbers min-ind)
      (swap! result conj (map-with-numbers min-ind))
      )

    (if (contains? map-with-numbers max-ind)
      (swap! result conj (map-with-numbers max-ind)))


    )

  (->> @result
       (map str)
       (apply str)
       (Integer/parseInt)
       )
  )

(get-first-last-digit test1)
