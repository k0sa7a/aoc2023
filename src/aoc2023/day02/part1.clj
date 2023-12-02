(ns aoc2023.day02.part1
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; max values for each cube color
(def max-cubes {"red" 12 "green" 13 "blue" 14 })

;; game example (1 row) for testing purposes
(def game1 "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

;; function to extract the game num from the game-row string
(defn game-name-parser [game-row]

  (let [split-result (str/split game-row #":")
        game-name-string (first split-result)]


    (->> game-name-string
        (re-find #"[0-9]+")
        (Integer/parseInt))
    )
  )

;; function to extract the game parts from the game-row string (resulting in an array of arrays of hashes)
(defn game-steps-parser [game-row]

  (let [split-result (str/split game-row #":")
        game-string (str/trim (last split-result))
        game-steps (map str/trim (str/split game-string #";"))
        game-steps-with-elements (map #(str/split % #",") game-steps)
        element (atom [])
        steps (atom [])
        ]

    (doseq [game-step game-steps-with-elements]
      ;; (prn game-step)

      (doseq [draw (map str/trim game-step)]
        ;; (prn draw)
        (swap! element conj (hash-map (last (str/split draw #" ")) (Integer/parseInt (first (str/split draw #" ")))))
        ;; (prn @element)
        )
      (swap! steps conj @element)
      (reset! element [])
     )
    @steps
    ))

;; function to check if the cubes exceed maximum every step
(defn check-possible [game-row]
  ;; (println "GAME DETAILS")
  ;; (prn (game-name-parser game-row))
  ;; (println "------------")
  ;; (println "ROW DETAILS")
  ;; (prn (game-steps-parser game-row))
  (let [row (game-steps-parser game-row)
        check (atom true)]

    (doseq [steps row]
      (doseq [draw steps]
        ;; (prn draw)
        ;; (println "KEY:" (first (keys draw)))
        ;; (println "MAX VAL:" (first (keys draw)) (get max-cubes (first (keys draw))))
        ;; (println "CURRENT VAL:" (first (keys draw)) (get draw (first (keys draw))))

        (if (> (get draw (first (keys draw))) (get max-cubes (first (keys draw))))
        ;;  (println "IMPOSIBLEEEEEEEEEEEEEE")
          (reset! check false))))
    @check
    )

  )

(defn calculate []
  ;; read file with input into string
  (def file-str (slurp "/home/k0sa7a/code/k0sa7a/aoc2023/src/aoc2023/day02/input1.csv"))
  ;; split each new line into separate string
  (def split-by-line (str/split-lines file-str))

  (let [result (atom 0)]
    (doseq [line split-by-line]
      (if (check-possible line)
        (swap! result + (game-name-parser line))))
    @result
    )
  )

(calculate)
