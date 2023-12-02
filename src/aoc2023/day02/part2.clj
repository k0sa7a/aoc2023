(ns aoc2023.day02.part2
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; game example (1 row) for testing purposes
(def game1 "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(defn game-steps-parser [game-row]

  (let [split-result (str/split game-row #":")
        game-string (str/trim (last split-result))
        game-steps (map str/trim (str/split game-string #";"))
        game-steps-with-elements (map #(str/split % #",") game-steps)
        element (atom [])
        steps (atom [])]

    (doseq [game-step game-steps-with-elements]
      ;; (prn game-step)

      (doseq [draw (map str/trim game-step)]
        ;; (prn draw)
        (swap! element conj (hash-map (last (str/split draw #" ")) (Integer/parseInt (first (str/split draw #" ")))))
        ;; (prn @element)
        )
      (swap! steps conj @element)
      (reset! element []))
    @steps))

(defn power-of-mins [game-row]
  ;; (println "GAME DETAILS")
  ;; (prn (game-name-parser game-row))
  ;; (println "------------")
  ;; (println "ROW DETAILS")
  ;; (prn (game-steps-parser game-row))
  (let [row (game-steps-parser game-row)
        min-cubes (atom {"red" 0 "green" 0 "blue" 0}) ]

    (doseq [steps row]
      (doseq [draw steps]
        ;; (prn draw)
        ;; (println "KEY:" (first (keys draw)))

        ;; (println "CURRENT VAL:" (first (keys draw)) (get draw (first (keys draw))))
        ;; (println "MIN VAL:" (first (keys draw)) (get @min-cubes (first (keys draw))))


        (if (nil? (get draw (first (keys draw))))
         nil
         (do
           (if (> (get draw (first (keys draw))) (get @min-cubes (first (keys draw))))
            (swap! min-cubes assoc (first (keys draw)) (get draw (first (keys draw))))
            )
           )
          )

        )
      )

    (reduce * (vals @min-cubes))
    )
  )

(defn calculate []
  ;; read file with input into string
  (def file-str (slurp "/home/k0sa7a/code/k0sa7a/aoc2023/src/aoc2023/day02/input2.csv"))
  ;; split each new line into separate string
  (def split-by-line (str/split-lines file-str))

  (reduce + (map power-of-mins split-by-line))


  )


(calculate)
