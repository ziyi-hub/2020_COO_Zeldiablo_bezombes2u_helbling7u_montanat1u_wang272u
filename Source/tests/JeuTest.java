import jeu.*;
import org.junit.Assert;

public class JeuTest {

    /**
     * Le premier test permet de tester si l'initialisation par defaut se deroule bien
     * On verifie donc le labyrinthe et le heros
     */
    @org.junit.Test
    public void test_01_Jeu_Default(){
        //jeu de donnees
        Jeu j = new Jeu();
        //assertion
        Assert.assertNotNull("Le labyrinthe ne devrait pas etre null", j.getLabyrinthe());
        Assert.assertNotNull("Le heros ne devrait pas etre null", j.getHeros());
    }

    /**
     * Le second test permet de tester si l'initialisation par defaut se deroule bien
     * Maintenant on verifie que la liste jeu.Monstre est bien initialisee.
     */
    @org.junit.Test
    public void test_02_Jeu_Default(){
        //jeu de donnees
        Jeu j = new Jeu();
        //assertion
        Assert.assertNotNull("La liste de Monstres ne dois pas etre null",j.getListeMonstres());
    }

    /**
     * Le troisième test detaille le second test et verifie si l'initialisation par defaut se deroule bien
     * Par consequent verifie que la liste jeu.Monstre est bien initialisee, mais contient tous les monstres du labyrinthe par defaut.
     */
    @org.junit.Test
    public void test_03_Jeu_Default(){
        //jeu de donnes
        Jeu j = new Jeu();
        //assertion
        Assert.assertEquals("La liste de Monstres devrais contenir 3 Monstres",3,j.getListeMonstres().size());
    }

    /**
     * on verifie que on gette la Personnage de la liste de jeu.Monstre random
     */
    @org.junit.Test
    public void test_04_getRandomMonstre(){
        //jeu de donnees
        Jeu j = new Jeu();
        //instruction de test
        Monstre m = (Monstre)j.getRandomMonstre();
        //assertion
        Assert.assertTrue("on gette la personnage de la liste Monstres", j.getListeMonstres().contains(m));
    }

    /* TODO: Corriger ca
    /**
     * on verifie que la mort de personnage
     * Par consequent on verifie que si le heros meurt, le jeu s'arrête
     * Par consequent on verifie que si le Monstre meurt, on le supprime dans la liste de Monstres

    @org.junit.Test
    public void test_05_personnageMort(){
        //jeu de donnees
        Jeu j = new Jeu();
        //personnahe de donnees
        Personnage pM = new Monstre(1, 1, 10, j);
        Personnage pH = new Heros(1, 1, 10, j, "toto");
        //instruction de test
        j.personnageMort(pM, 1);
        j.personnageMort(pH, 1);
        //assertion
        Assert.assertEquals("on gette la personnage de la liste Monstres", j.getListeMonstres());
    }
    */

}