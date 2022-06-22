import jeu.*;
import org.junit.Assert;

public class LabyrintheTest {

    /**
     * Le premier test permet de tester si l'initialisation par defaut se deroule bien
     * On verifie donc que le labyrinthe contient bien le mur d'enceinte
     */
    @org.junit.Test
    public void test_01_getAccessible_Default(){
        //jeu de donnees
        Labyrinthe l = new Labyrinthe();
        boolean aUneOuverture = false;

        //instructions de test
        for (int x = 0; x < 10; x++) {
            if(l.getAccessible(x, 0)) aUneOuverture = true;
            if(l.getAccessible(x, 9)) aUneOuverture = true;
        }

        for (int y = 0; y < 10; y++) {
            if(l.getAccessible(0, y)) aUneOuverture = true;
            if(l.getAccessible(9, y)) aUneOuverture = true;
        }

        //assertion
        Assert.assertFalse("Le labyrinthe devrait etre entierement entoure",aUneOuverture);

    }

    /**
     * Le second test permet de tester si l'initialisation par defaut se deroule bien
     * On verifie donc que le labyrinthe contien bien une zone libre en son sein
     */
    public void test_02_getAccessible_Default(){
        //jeu de donnees
        Labyrinthe l = new Labyrinthe();
        //assertion
        Assert.assertTrue("la case de spawn du joueur devrait etre libre",l.getAccessible(5, 5));
    }

    /**
     * Le troisième test permet de tester si l'initialisation par defaut se deroule bien
     * On verifie donc que le labyrinthe contien bien l'amulette en son sein
     */
    public void test_03_placeItem(){
        //jeu de donnees
        Jeu j = new Jeu();
        Labyrinthe l = j.getLabyrinthe();
        //assertion
        Assert.assertEquals("La liste dois contenir au minimum un élément qui est l'amulette",l.getItem().get(0));
    }

    public void test_04_placeItem_NotInWall(){
        //jeu de donnees
        Jeu j = new Jeu();
        Labyrinthe l = j.getLabyrinthe();
        jeu.Item i = l.getItem().get(0);
        //assertion
        Assert.assertEquals("On vérifie que l'amulette est bien accessible",true,l.getAccessible(i.getX(),i.getY()));
    }

}