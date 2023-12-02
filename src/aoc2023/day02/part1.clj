(ns aoc2023.day02.part1
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; max values for each cube color
(def max-cubes {"red" 12 "green" 13 "blue" 14 })

;; game example (1 row) for testing purposes
(def game1 "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")

;; function to extract the game num from the game-row string
(defn game-row-parser [game-row]

  (let [split-result (str/split game-row #":")
        game-name-string (first split-result)]


    (->> game-name-string
        (re-find #"[0-9]+")
        (Integer/parseInt))
    )
  )

;; function to extract the game parts from the game-row string
(defn game-steps-parser [game-row]

  (let [split-result (str/split game-row #":")
        game-string (str/trim (last split-result))
        game-steps (map str/trim (str/split game-string #";"))
        game-steps-with-elements (map #(str/split % #",") game-steps)
        element (atom [])
        step (atom [])
        ;; game-steps-mappified (map #(hash-map (last %) (first %)) game-steps-with-elements)

        ]

     (doseq [game-step game-steps-with-elements]
       (prn game-step)
      ;;  (map #(hash-map (last %) (first %)) game-step)
       (doseq [draw (map str/trim game-step)]
         (prn draw)
         (swap! element conj (hash-map (last (str/split draw #" ")) (first (str/split draw #" "))))
         (prn @element)
         )
       (swap! step conj @element)
       (reset! element [])
       (prn @step)
       )

    ))

(game-steps-parser game1)
