package jeu;

import java.util.ArrayList;

/**
 * Classe jeu.Labyrinthe
 */
public class Labyrinthe {

    /**
     * Correspond au tableau stockant les murs de l'arene avec :<br>- true = mur,<br>- false = case vide)
     */
    private boolean murs[][];

    /**
     * Correspond a la taille du tableau en hauteur et en largeur puisque l'arene est carree
     */
    private int taille;

    private ArrayList<Item> listeItems;

    /**
     * Constructeur d'une arene par defaut
     */
    public Labyrinthe() {
        this.taille = 10;
        this.murs = new boolean[][]{
                {true, true, true, true, true, true, true, true, true, true,},
                {true, false, false, false, true, false, true, false, false, true},
                {true, false, true, false, false, false, false, false, true, true},
                {true, false, true, true, true, false, true, false, false, true},
                {true, true, false, false, false, false, true, true, false, true},
                {true, false, false, true, false, false, true, false, false, true},
                {true, false, true, true, true, true, true, false, true, true},
                {true, false, false, false, false, true, false, false, false, true},
                {true, false, false, true, false, true, false, false, false, true},
                {true, true, true, true, true, true, true, true, true, true,}
        };
        this.listeItems = new ArrayList<Item>();
    }

    /**
     * Place les items dans le jeu
     */
    public void placeItem() {
        Personnage m = Jeu.JeuEnCours.getRandomMonstre();
        listeItems.add(new Amulette((m.getX()), (m.getY())));
    }

    /**
     * Methode indiquant si la case de coordonnees x et y est accessible
     *
     * @param x abscisse du mouvement du joueur
     * @param y ordonnee du mouvement du joueur
     * @return true si la case est accessible (= vide)
     */
    public boolean getAccessible(int x, int y) {
        return !this.murs[y][x];
    }

    /**
     * Retourne le nombre de cases vides
     *
     * @return nombre de cases vides
     */
    public int getNbCasesLibres() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!this.murs[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Accesseur retournant la taille du terrain
     *
     * @return taille du terrain
     */
    public int getTaille() {
        return taille;
    }

    public ArrayList<Item> getItem() {
        return this.listeItems;
    }

    public void changeItemList(ArrayList<Item> nList) {
        this.listeItems = nList;
    }
}
