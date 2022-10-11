package org.sio.slam.devine

import org.sio.slam.devine.core.Carte
import org.sio.slam.devine.core.Jeu
import org.sio.slam.devine.core.Paquet
import org.sio.slam.devine.enum.CouleurCarte
import org.sio.slam.devine.enum.NomCarte
import org.sio.slam.devine.enum.getCouleurCarteFromString
import org.sio.slam.devine.enum.getNomCarteFromString
import org.sio.slam.devine.fabrique.createJeu32Cartes
import org.sio.slam.devine.fabrique.createJeu52Cartes
fun main(args: Array<String>) {
    var abandon = false
    var aide = false
    var restart = 0
    var tenta = 0
    var reussite = 0
    var nbreCarteTiree = 0
    println("Voulez-vous de l'aide ? [Oui/Non]")
    if(readLine().toString().uppercase().startsWith("OUI")) aide = true
    if(aide) println("L'aide a été activée")
    if(!aide) println("L'aide n'a pas été activée")
    // TODO (A) demander au joueur s'il souhaite avoir de l'aide pour sa partie

    println("Paquet de 32 ou de 52 cartes ? [32/52] ")
    val rep = readLine()
    var paqueDeCartes = Paquet(createJeu32Cartes())
    if(rep =="52") paqueDeCartes = Paquet(createJeu52Cartes()) else if(rep=="32") paqueDeCartes = Paquet(createJeu32Cartes())
    // TODO (A) demander au joueur avec quel jeu de cartes 32 ou 52 il souhaite jouer

    println(" ==== Instanciation du jeu, début de la partie. ====")
    var jeu = Jeu(aide, paqueDeCartes)
    var carteTiree = jeu.carteADeviner
    do {
        println(carteTiree) //(Utilisé pour faire des tests)
        println("Entrez un nom de carte dans le jeu (exemples : Roi, sept, six, As...) :")
        // TODO (optionnel) permettre de saisir un chiffre au lieu d'une chaine : 7 au lieu de Sept...
        val nomCarteUserStr: String = readLine() + ""
        val nomCarteUser: NomCarte? =
            if (nomCarteUserStr in (2..10).toSet().toString()) {
                val words = arrayOf("deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix")
                val nomCarteUserInt = nomCarteUserStr.toInt()
                val result = words[nomCarteUserInt - 2]
                getNomCarteFromString(result.trim().uppercase())
            } else
                getNomCarteFromString(nomCarteUserStr.trim().uppercase())

        println("Entrez un nom de \"couleur\" de carte parmi : Pique, Trefle, Coeur, Carreau : ")
        val couleurCarteUserStr: String = readLine() + ""
        val couleurCarteUser: CouleurCarte? = getCouleurCarteFromString(couleurCarteUserStr.trim().uppercase())
        tenta += 1

        if (nomCarteUser != null && couleurCarteUser != null) {
            // prise en compte de la carte du joueur
            val carteDuJoueur = Carte(nomCarteUser, couleurCarteUser)

            if (jeu.isMatch(carteDuJoueur)) {
                println("Bravo, vous avez trouvé la bonne carte !")
                restart++
                reussite+=1
                nbreCarteTiree += 1
            } else {
                println("Ce n'est pas la bonne carte !")
                println("votre proposition  : $carteDuJoueur")
                if (aide) {
                    when (couleurCarteUser.compareTo(jeu.carteADeviner.couleur)){
                        0 -> println("La couleur est la bonne, ")
                        in 1..Int.MAX_VALUE-> println("La couleur de la carte à trouver est plus petite\nRappel : Coeur > Carreau > Pique > Trefle")
                        in Int.MIN_VALUE..-1 -> println("La couleur de la carte à trouver est plus grande\nRappel : Coeur > Carreau > Pique > Trefle")
                    }
                    when (nomCarteUser.compareTo(jeu.carteADeviner.nom)){
                        0 -> println("La valeur de la carte est bonne.")
                        in 1..Int.MAX_VALUE -> println("La valeur à trouver est plus petite.")
                        in Int.MIN_VALUE..-1 -> println("La valeur à trouver est plus grande.")
                    }
                    // TODO: (A) si l'aide est activée, alors dire si la carte proposée est
                    //  plus petite ou plus grande que la carte à deviner
                }
            }
        } else {
            // utilisateur a mal renseigné sa carte
            val nomCarte = if (nomCarteUserStr == "") "?" else nomCarteUserStr
            val couleurCarte = if (couleurCarteUserStr == "") "?" else couleurCarteUserStr

            println("Désolé, mauvaise définition de carte ! (${nomCarte} de ${couleurCarte})")
        }

        if(reussite==0){
            println("Voulez-vous abandonner ? [Oui/Non]")
        }
        val answer = readLine()?.uppercase()
        if(answer == "OUI"){
            abandon = true
            restart++
        }
    }while(restart != 1)

    // TODO (A) permettre au joueur de retenter une autre carte (sans relancer le jeu) ou d'abandonner la partie
    println(" ==== Fin prématurée de la partie ====")

    // TODO (A) Présenter à la fin la carte à deviner
    val pronom = when (carteTiree.nom) {
        NomCarte.DAME -> "la "
        NomCarte.AS -> "l'"
        else -> "le "
    }
    val prcentReussite = ((reussite.toDouble()/tenta.toDouble())*100)
    println("La carte à deviner était $pronom${carteTiree.nom} de ${carteTiree.couleur}, vous avez eu ${String.format("%.2f",prcentReussite)}% de réussite")
    // TODO (challenge-4) la stratégie de jeu est à implémenter... à faire lorsque les autres TODOs auront été réalisés
    println(jeu.strategiePartie(tenta,abandon))

    println(" ==== Fin de la partie. ====")
}
