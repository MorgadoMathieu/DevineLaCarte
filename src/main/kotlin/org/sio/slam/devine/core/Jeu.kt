package org.sio.slam.devine.core
import kotlin.math.log2

class Jeu(val avecAide: Boolean, val paquet: Paquet, paramCarteADeviner: Carte? = null) {
    val carteADeviner: Carte
        // le getter par défaut, inutile de le redéclarer (juste pour la démonstration)
        // field est ici synonyme de carteADeviner (implicite backing memory de la propriété)
        // REM : faire référence à carteADeviner au lieu de field entrainerait une récursion incontrôlée
        get() = field
    // set(value) { field = value } <== impossible car la propriété est en lecture seule (val)

    init {
        // si le paramètre paramCarteADeviner du constructeur a comme valeur null
        // alors on demande au paquet de nous fournir une carte à deviner
        // sinon on retient la valeur de carte transmise
        this.carteADeviner = paramCarteADeviner ?: this.paquet.getCarteADeviner()
    }

    /**
     * Permettre de savoir si la proposition de carte EST bien la carte à deviner, ou non
     */
    fun isMatch(carteProposee: Carte): Boolean {
        return this.carteADeviner == carteProposee
    }

    /**
     * Analyse la partie du joueur, a-t-il abandonné la partie,
     *  a-t-il trouvé la carte en un nombre de fois "convenable" ou "inconvenable",
     *  a-t-il eu de la chance ?
     */
    fun strategiePartie(nbEssais : Int, restart: Boolean): String {
        if(!restart){
            if(avecAide){
                val dichoSearch : Double = log2(paquet.cartes.size.toDouble())
                if(nbEssais.toDouble() >= dichoSearch*1.80){
                    return "Avec votre recherche dichotomique peu précise, vous avez fais $nbEssais essais"
                }
                else if (nbEssais.toDouble() >= dichoSearch +1 && nbEssais.toDouble() < dichoSearch*1.80){
                    return "Avec votre recherche dichotomique assez précise, trouvé $nbEssais essais"
                }
                else if (nbEssais == dichoSearch.toInt()) {
                    return "Avec votre recherche dichotomique très précise, trouvé en $nbEssais essai(s)"
                }
                else{
                    return "Peu de stratégie sûrement de la chance, trouvé en $nbEssais essai(s)"
                }
            }
            else{
                val pourcentChance : Double = (nbEssais.toDouble() / paquet.cartes.size.toDouble())*100.0
                return if (nbEssais / paquet.cartes.size <= 0.25){
                    "Avec une stratégie linéaire, vous aviez ${pourcentChance.toInt()}% de chance de trouver, vous avez $nbEssais essais"
                }
                else{
                    "Avec une stratégie linéaire, vous aviez ${pourcentChance.toInt()}% de chance de trouver, vous avez $nbEssais essais"
                }
            }
            return "Erreur"
        }
        else{
            return "Pas de stratégie, vous avez abandonné"
        }
    }
}


//        var fin = this.paquet.cartes.size-1
//     var milieu = (debut+fin)/2
//        var tour = 0
//        do{
//            var milieu = (debut+fin)/2
//            when(this.paquet.cartes[milieu].compareTo(this.carteADeviner)){
//                in 1..Int.MAX_VALUE -> fin = milieu -1
//                in -1..Int.MIN_VALUE -> debut = milieu + 1
//                else -> break
//            }
//            tour+=1
//        }while(this.paquet.cartes[milieu].compareTo(this.carteADeviner) != 0 && debut < fin)