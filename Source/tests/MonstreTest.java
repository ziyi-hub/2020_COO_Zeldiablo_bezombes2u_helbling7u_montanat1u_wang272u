import jeu.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MonstreTest {

    /**
     * Verifie qu'on ne peut pas placer un monstre en dehors de l'arene
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_01_Monstre() {
        //jeu de donnees
        Jeu j = new Jeu();
        Personnage pH = new Monstre(11, 11, j);
    }

    /**
     * Verifie que le monstre est bien place aux bonnes coordonnees
     */
    @Test
    public void test_02_Monstre() {
        //jeu de donnees
        Jeu j = new Jeu();
        Personnage pH = new Monstre(7, 2, j);
        //assertions
        assertNotNull("Les coordonnees de l'objet ne devraient pas etre nulles", pH);

    }

    /**
     * Verifie que le monstre est place aux bonnes coordonnees, qu'il a bien ses points de vie initiaux et qu'il est bien rattache a la bonne arene
     */
    @Test
    public void test_03_Monstre() {
        //jeu de donnees
        Jeu j = new Jeu();
        Monstre pM = new Monstre(1, 1, j);
        //assertions
        assertNotNull("Les coordonnees de l'objet ne devraient pas etre nulles", pM);
        assertEquals("Les points de vie du monstre devraient etre egaux a leur valeur par defaut", 2, pM.getPv());
        assertEquals("L'axe x du monstre devrait etre egal a 1", 1, pM.getX());
        assertEquals("L'axe y du monstre devrait etre egal a 1", 1, pM.getY());
        assertEquals("Le monstre devrait etre attache au bon labyrinthe", j.getLabyrinthe(), pM.getJeu().getLabyrinthe());
    }

    /**
     * Verifie les deplacements du monstre suivant l'axe voulu et si la case souhaitee est libre
     */
    @Test
    public void test_04_Monstre() {
        //jeu de donnees
        Jeu j = new Jeu();
        j.getListeMonstres().clear();
        Personnage pM = new Monstre(1, 1, j);
        //instruction de test
        pM.seDeplacer(8);
        //assertions
        assertEquals("Les coordonnees X ne devraient pas changer car on ne bouge pas dans cet axe", 1, pM.getX());
        assertEquals("Les coordonnees Y ne devraient pas changer car il doit y avoir une collision avec le mur du dessus", 1, pM.getY());
        //instruction de test
        pM.seDeplacer(6);
        //assertions
        assertEquals("Les coordonnees X devrait etre incremente de 1", 2, pM.getX());
        assertEquals("Les coordonnees Y ne devrait pas changer car on ne bouge pas dans cet axe", 1, pM.getY());
    }

    /**
     * Verifie que le monstre attaque bien le heros s'il se trouve a proximite de ce dernier
     * Permet egalement de tester les methodes attaquer() et subirDegats() de la classe Personnage
     */
    @Test
    public void test_05_lancerTour(){
        //jeu de donnees
        Jeu j = new Jeu();
        Monstre m = new Monstre(5,4,j);
        //instruction de test
        m.lancerTour();
        //assertion
        assertEquals("Le heros devrait avoir perdu 1 pv",4, j.getHeros().getPv());
    }

}