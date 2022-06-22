import jeu.*;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.awt.*;


import static org.junit.Assert.*;

public class HerosTest {

    /**
     * Verifie que le heros ne puisse pas etre place en dehors de l'arene
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_01_Heros() {
        Jeu j = new Jeu();
        Personnage pH = new Heros(11, 11, 10, j, "toto");
    }

    /**
     * Verifie que le heros est bien place aux bonnes coordonnees
     */
    @Test
    public void test_02_Heros() {
        //jeu de donnees
        Jeu j = new Jeu();
        Personnage pH = new Heros(1, 1, 10, j, "toto");
        //assertion
        assertNotNull("Les coordonnees de l'objet ne devraient pas etre nulles", pH);

    }

    /**
     * Verifie que le heros est place aux bonnes coordonnees, qu'il possede ses points de vie initiaux et qu'il est bien rattache au bon labyrinthe
     */
    @Test
    public void test_03_Heros() {
        //jeu de donnees
        Jeu j = new Jeu();
        Heros pH = new Heros(1, 1, 10, j, "toto");
        //assertions
        assertNotNull("Les coordonnees de l'objet ne devraient pas etre nulles", pH);
        assertEquals("Les points de vie du heros devraient etre egaux a leur valeur par defaut", 10, pH.getPv());
        assertEquals("L'axe x du heros devrait etre egal a 1", 1, pH.getX());
        assertEquals("L'axe y du heros devrait etre egal a 1", 1, pH.getY());
        assertEquals("Le heros devrait être attaché au bon labyrinthe", j.getLabyrinthe(), pH.getJeu().getLabyrinthe());
        assertEquals("Le nom devrait être toto", "toto", pH.getNom());
    }

    /**
     * Verifie les deplacements du heros suivant l'axe de deplacement et si la case voulue est libre
     */
    @Test
    public void test_04_Heros() {
        //jeu de donnees
        Jeu j = new Jeu();
        j.getListeMonstres().clear();
        Personnage pH = new Heros(1, 1, 10, j, "toto");
        //instruction de test
        pH.seDeplacer(8);
        //assertions
        assertEquals("Les coordonnees X ne devraient pas changer car on ne bouge pas dans cet axe", 1, pH.getX());
        assertEquals("Les coordonnees Y ne devraient pas changer car il doit y avoir une collision avec le mur du dessus", 1, pH.getY());
        //instruction de test
        pH.seDeplacer(6);
        //assertions
        assertEquals("Les coordonnees X devraient etre incrementees de 1", 2, pH.getX());
        assertEquals("Les coordonnees Y ne devraient  pas changer car on ne bouge pas dans cet axe", 1, pH.getY());
    }

    /**
     * Verifie que le heros attaque bien les monstres autour de lui
     */
    @Test
    public void test_05_attaquerAlentours(){
        //jeu de donnees
        Jeu j = new Jeu();
        Heros pH = (Heros) j.getHeros();
        Monstre pM = new Monstre(5,4,j);
        Monstre pM2 = new Monstre(4,5,j);
        //instructions de test
        j.getListeMonstres().add(pM);
        j.getListeMonstres().add(pM2);
        pH.attaquerAlentours();
        //assertions
        assertEquals("Le premier monstre devrait avoir perdu 1 pv", 1, pM.getPv());
        assertEquals("Le second monstre devrait avoir perdu 1 pv", 1, pM2.getPv());
        //instruction de test
        pH.attaquerAlentours();
        //assertion ayant pour but de tester la mort et la suppression d'un monstre dans l'arene
        assertEquals("Le monstre devrait etre mort", false, pM.isTherePersonnage(new Point(5,4)));
    }

    /** TODO
    @Test
    public void test_06_attaquerAlentours(){
        //jeu de donnees
        Jeu j = new Jeu();
        Heros pH = (Heros) j.getHeros();
        Monstre pM = new Monstre(5,3,2,j);
        //instructions de test
        j.getListeMonstres().add(pM);
        pH.attaquerAlentours();
        pH.seDeplacer(8);
        //assertions
        assertEquals("Le monstre ne devrait pas perdre de pv", 2, pM.getPv());
        assertEquals("Le heros ne devrait pas avoir effectue de deplacement",5, pH.getY());
    }
    **/

    /**
     * Vérifie si l'ajout de la fonctionnalité 6.2 fonctionne
     * 6.2 : L'amulette doit être ramassable par le joueur
     */
    @Test
    public void test_07_Prendre_Amulette(){
        Jeu j = new Jeu();
        Labyrinthe la = j.getLabyrinthe();
        Personnage heros = j.getHeros();
        ArrayList<Item> lItem = la.getItem();
        ArrayList<Item> lItem_Hero = ((Heros)j.getHeros()).getListeItems();
        Assert.assertEquals("La taille de la liste item du heros devrais être 0",0, lItem_Hero.size());
        int pos = 0;
        boolean trouver = false;
        while(!trouver && pos < lItem.size()){
            Item i = lItem.get(pos);
            if(i instanceof Amulette){
                trouver = true;


            int dX = i.getX();
            int dY = i.getY();
            int mov = 0;
            if(la.getAccessible(dX-1,dY)){
                dX--;
                mov=6;
            }else if (la.getAccessible(dX+1,dY)){
                dX++;
                mov = 4;
            }else if(la.getAccessible(dX,dY-1)){
                dY--;
                mov=2;
            }else if(la.getAccessible(dX,dY+1)){
                dY++;
                mov = 8;
            }
                heros.setX(dX);
                heros.setY(dY);
            j.getListeMonstres().clear();
                heros.seDeplacer(mov);
                lItem_Hero = ((Heros) heros).getListeItems();
            Assert.assertEquals("La taille de la liste devrais être de 1",1,lItem_Hero.size());
            }
        }
    }

    /**
     * Le test 08 vérifie le bon fonctionnement de la fonctionnalité 6.3
     * 6.3 : Le joueur doit gagner quand il passe sur la case départ avec l'amulette
     */

    @Test
    public void test_08_Gagner_Amulette(){
        Jeu j = new Jeu();
        Labyrinthe la = j.getLabyrinthe();
        Personnage heros = j.getHeros();
        ArrayList<Item> lItem = la.getItem();
        ArrayList<Item> lItem_Hero = ((Heros)heros).getListeItems();

        Assert.assertEquals("La taille de la liste item du heros devrais être 0",0, lItem_Hero.size());

        int index = 0;
        boolean trouver = false;

        while(!trouver && index < lItem.size()){

            Item i = lItem.get(index);

            if(i instanceof Amulette){

                trouver = true;


                int dX = i.getX();
                int dY = i.getY();

                int mov = 0;

                if(la.getAccessible(dX-1,dY)){
                    dX--;
                    mov=6;
                }else if (la.getAccessible(dX+1,dY)){
                    dX++;
                    mov = 4;
                }else if(la.getAccessible(dX,dY-1)){
                    dY--;
                    mov=2;
                }else if(la.getAccessible(dX,dY+1)){
                    dY++;
                    mov = 8;
                }

                j.getListeMonstres().clear();
                heros.setX(dX);
                heros.setY(dY);
                heros.seDeplacer(mov);

                lItem_Hero = ((Heros)heros).getListeItems();

                Assert.assertEquals("La taille de la liste devrais être de 1",1,lItem_Hero.size());
                Assert.assertEquals("L'amulette devrais être en possession du joueur",true,((Heros) heros).getAmulette());
                Assert.assertEquals("Le joueur ne dois pas avoir encore gagner",false,((Heros) heros).getGagner());

                int drX = (int)(((Heros)heros).getPos().getX());
                int drY = (int)(((Heros)heros).getPos().getY());

                if(la.getAccessible(drX-1,drY)){
                    drX--;
                    mov=6;
                }else if (la.getAccessible(drX+1,drY)){
                    drX++;
                    mov = 4;
                }else if(la.getAccessible(drX,drY-1)){
                    drY--;
                    mov=2;
                }else if(la.getAccessible(drX,drY+1)){
                    drY++;
                    mov = 8;
                }

                heros.setY(drY);
                heros.setX(drX);
                heros.seDeplacer(mov);

                Assert.assertEquals("Le joueur devrais avoir gagner",true,((Heros) heros).getGagner());
            }
        }
    }


}