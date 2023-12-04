(ns aoc2023.day04.part2
   (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; a global vector of rows; a row is a map: key is times to check, value is the 2 sets with the numbers (winning and ones to check)
;; Something like: [{1 [#{"83" "17" "48" "41" "86"} #{"9" "83" "17" "48" "53" "6" "31" "86"}]}, {1 [#{"61" "20" "13" "32" "16"} #{"82" "61" "68" "30" "19" "17" "24" "32"}]} ]
(def game-rows-cleaned (ref []))




(defn check-winning-numbers-count [index]


  (let [vector-with-number-sets (first (vals (nth @game-rows-cleaned index)))
        winning-numbers-count  (count (clojure.set/intersection (first vector-with-number-sets) (last vector-with-number-sets) ))
        ]
    winning-numbers-count
    )
  )

;; function to check number of matching numbers on a row, recursive?
;; maybe take as arguments vector of integers for the indexes of following rows
;; in case count of that vector is 0 we dont execute (we execute if count > 0)
(defn go-through-rows [vect-indexes]
(def max-ind (dec (count @game-rows-cleaned)) )
  (let [next-vec-indexes (atom [])]

    (if (> (count vect-indexes) 0)

      (do

        (doseq [index vect-indexes]
          (let [winning-numbers-count-checked (check-winning-numbers-count index)]

          (if (> winning-numbers-count-checked 0)
            (do





              (doseq [ind-row (vec (range (inc index) (+ index (inc winning-numbers-count-checked))))]

                (dosync
                 (let [current-state @game-rows-cleaned
                       map-to-change (nth current-state index)
                       old-key (first (keys map-to-change))
                       new-key (inc old-key)
                       updated-map (assoc (dissoc map-to-change old-key) new-key (get map-to-change old-key))
                       new-state (assoc current-state index updated-map)]
                   (alter game-rows-cleaned (constantly new-state)))))

              (if (= index max-ind)
                (go-through-rows [(vec (range (inc index) (inc (inc index))))])
                (go-through-rows (vec (range (inc index) (+ index (inc winning-numbers-count-checked))))))

              ))
            )


              )

        )


      )

    )

    )



;; helper function to prepare the structure of each element within game-rows-cleaned (vector of maps, with key tries and value a vector of 2 sets of numbers)
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
                                    (str/split #" "))
        game-numbers-vect-cleaned (conj [] (set (filter #(not (= % ""))  winning-vect-dirty)) (set (filter #(not (= % ""))  game-numbers-vect-dirty)))]

    (dosync
     (alter game-rows-cleaned conj (assoc {} 1 game-numbers-vect-cleaned)))
    )


  )

(defn prepare-input []
    ;; read file with input into string
  (def file-str (slurp "/home/k0sa7a/code/k0sa7a/aoc2023/src/aoc2023/day04/input2.csv"))
    ;; split each new line into separate string
  (def split-by-line (str/split-lines file-str))


  (doseq [row split-by-line]
    (split-row row)
    )
  )

;; helper function to get all the keys of game-rows-cleaned and sum them up
(defn calculate-total-cards []
  (reduce + (flatten (map keys @game-rows-cleaned)))
  )


(prepare-input)

(go-through-rows (range 0 (count split-by-line)))

(println (calculate-total-cards))
