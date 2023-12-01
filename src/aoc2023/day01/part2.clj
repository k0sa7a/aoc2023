(ns aoc2023.day01.part2
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(def test1 "two1nine")

(def digits [#"^one" #"two" #"three" #"four" #"five" #"six" #"seven" #"eight" #"nine"])

(str/index-of test1 "nine")

(defn check-digits
  [input-string]

  (loop [i 0]
    (when (< i (count input-string))
      ;; (println (nth input-string i))




      (recur (inc i)))

  )
)


(check-digits test1)
 (re-find #"^two" "two1nine")

(loop [i 0]
  (when (< i (count "two1nine"))
    (println "-------")
      (println (nth "two1nine" i))

    ;; (println (str/index-of "two1nine" "nine" i))

    (let [index (str/index-of "two1ninetwo" "two" i)]
      (println "i is" i)
      (println "index is" index)

      )


    (recur (inc i))))
