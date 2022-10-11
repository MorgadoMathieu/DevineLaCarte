package org.sio.slam.devine.core

import org.sio.slam.devine.fabrique.*
import java.util.Collections.shuffle

class Paquet constructor(var cartes: List<Carte> = ArrayList<Carte>()) {
    init {
        if (this.cartes.isEmpty()) {
            this.cartes = createJeu32Cartes()
        }
    }

    /**
     * Donne le nombre de cartes dans le paquet
     */
    fun cardinal(): Int = cartes.size

    /**
     * Représentation textuelle de l'état du paquet
     */
    override fun toString(): String {
        return "Paquet de ${cardinal()} cartes"
    }
    fun melange(): Unit = shuffle(cartes)


    /**
     * C'est le paquet qui décide quelle sera la carte à deviner
     * @see [org.sio.slam.Jeu]
     */
    fun getCarteADeviner(): Carte {
        var carteR = this.cartes[(cartes.indices).random()]
        //println(carteR)
        return carteR
    }

}
