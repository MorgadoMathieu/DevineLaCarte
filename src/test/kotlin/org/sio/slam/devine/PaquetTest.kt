package org.sio.slam.devine

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.sio.slam.devine.core.Carte
import org.sio.slam.devine.core.Paquet
import org.sio.slam.devine.enum.CouleurCarte
import org.sio.slam.devine.enum.NomCarte
import org.sio.slam.devine.fabrique.createJeu32Cartes
import org.sio.slam.devine.fabrique.createJeu52Cartes

internal class PaquetTest {

    @Test
    fun cardinal2Cartes() {
        val paquet2Cartes = Paquet(listOf(
            Carte(NomCarte.VALET, CouleurCarte.COEUR),
            Carte(NomCarte.DIX, CouleurCarte.TREFLE),
        ))
        assertEquals(2, paquet2Cartes.cardinal())
    }

    @Test
    fun testToString2Cartes() {
        val paquet2Cartes = Paquet(listOf(
            Carte(NomCarte.VALET, CouleurCarte.COEUR),
            Carte(NomCarte.DIX, CouleurCarte.TREFLE),
        ))
        assertEquals("Paquet de 2 cartes", paquet2Cartes.toString() )
    }

    @Test
    fun testGetCartes() {
        val paquet2Cartes = Paquet(listOf(
            Carte(NomCarte.VALET, CouleurCarte.COEUR),
            Carte(NomCarte.DIX, CouleurCarte.TREFLE),
        ))
        var test: List<Carte> = paquet2Cartes.cartes
        assertEquals(NomCarte.VALET,test[0].nom)
        assertEquals(CouleurCarte.COEUR,test[0].couleur)
    }


    @Test
    fun fabriqueDe32Cartes() {
        val test = Paquet(createJeu32Cartes())
        assertEquals(32,test.cartes.size)
        assertEquals(NomCarte.SEPT,test.cartes[0].nom)
    }

    @Test
    fun fabriqueDe52Cartes() {
        val test = Paquet(createJeu52Cartes())
        assertEquals(52,test.cartes.size)
    }
    @Test
    fun melangeDeCartes(){
        val test = Paquet(createJeu52Cartes())
        assertEquals("DEUX",test.cartes[0].nom.toString())

        test.melange()
        assertNotEquals("DEUX",test.cartes[0].nom)
    }
}
