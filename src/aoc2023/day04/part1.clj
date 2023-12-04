(ns aoc2023.day04.part1
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; helper function to split a row into 2 sets with numbers and clean the string numbers from whitespace
(defn split-row [row]
  (let [two-parts-row (str/split row #" \| ")
        winning-vect-dirty (-> two-parts-row
                               (first)
                               (str/split #":")
                               (last)
                               (str/trim)
                               (str/split #" "))
        game-numbers-vect-dirty (-> two-parts-row
                                    (last)
                                    (str/split #":")
                                    (last)
                                    (str/trim)
                                    (str/split #" "))]

    (conj [] (set (filter #(not (= % ""))  winning-vect-dirty)) (set (filter #(not (= % ""))  game-numbers-vect-dirty))))
  )

;; count winning numbers and calculate points
(defn count-winning-numbers [row]
  (let [win-and-my-numbs-vec (split-row row)
        winning-numbers-count  (count (clojure.set/intersection (first win-and-my-numbs-vec) (last win-and-my-numbs-vec)))]
    (int
    (Math/pow 2 (- winning-numbers-count 1))))
  )

(defn calculate []
  ;; read file with input into string
  (def file-str (slurp "/home/k0sa7a/code/k0sa7a/aoc2023/src/aoc2023/day04/input1.csv"))
  ;; split each new line into separate string
  (def split-by-line (str/split-lines file-str))

  (reduce + (map count-winning-numbers split-by-line)))

(calculate)
