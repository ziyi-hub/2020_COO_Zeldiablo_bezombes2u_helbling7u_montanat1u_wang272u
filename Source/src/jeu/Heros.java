package jeu;

import java.awt.*;
import java.util.*;
/**
 * Classe representant le personnage que le joueur incarne
 */
public class Heros extends Personnage {

    /**
     * Nom du personnage
     */
    private final String nom;


    private boolean droitdeJeu = true;

    /**
     * Indique si le joueur à gagner
     */
    private boolean gagner = false;

    /**
     * Indique si le joueur est en possession de l'amulette
     */
    private boolean amulette = false;


    /**
     *  Liste des items possédé par le joueurs
     */
    private ArrayList<Item> listeItems;

    /**
     * Position de la sortie basé sur la position du joueur.
     */
    private final Point pos;
    /**
     * Constructeur d'un heros
     *
     * @param x   position en x
     * @param y   position en y
     * @param pv  nombre de pv
     * @param jeu jeu.Jeu auquel l'attache
     * @param nom nom du personnage
     */
    public Heros(int x, int y, int pv, Jeu jeu, String nom) {
        super(x, y, pv, jeu);
        this.nom = nom;
        this.listeItems = new ArrayList<Item>();
        this.pos = new Point(x,y);
    }

    /**
     * Getter du nom
     *
     * @return nom du heros
     */
    public String getNom() {
        return nom;
    }

    /**
     * Methode permettant au joueur d'attaquer les monstres qui se trouvent a proximite de lui ou de lancer une attaque dans le vide si aucun monstre ne se trouve a proximite
     *
     * @return true quand l'attaque est faite
     */
    public boolean attaquerAlentours() {
        attaqueAnim = true;
        if (getPersonnageAlentours().isEmpty()) {
            this.droitdeJeu = false;
            return false;
        }

        for (Personnage p : getPersonnageAlentours()) {
            if(!(p instanceof Heros)) p.subirDegats();
        }
        return true;
    }

    /**
     * Point d'entree d'action du personnage
     */
    @Override
    public void lancerTour() {

    }

    /**
     *
     * @param direction (Direction sur le pave numerique ->
     *                  <p>2 = bas</p>
     *                  <p>6 = droite</p>
     *                  <p>8 = haut</p>
     *                  <p>4 = gauche</p>
     */
    @Override
    public void seDeplacer(int direction) {
        super.seDeplacer(direction);
        if(this.pos.getX() == getX() && this.pos.getY() == getY() && amulette){
            gagner = true;
        }else {
            Labyrinthe laby = super.getJeu().getLabyrinthe();

            ArrayList<Item> listItem = laby.getItem();
            int taille = listItem.size();
            boolean trouver = false;
            while (!trouver && taille > 0) {
                Item i = listItem.get(taille - 1);
                if (i.getX() == getX() && i.getY() == getY()) {
                    this.listeItems.add(i);
                    listItem.remove(taille - 1);
                    laby.changeItemList(listItem);
                    if(i instanceof Amulette){
                        amulette = true;
                    }
                    trouver = true;
                } else {
                    taille--;
                }
            }
        }
    }

    /**
     * Getter du droit de jeu
     *
     * @return true si le joueur peut se deplacer apres une attaque
     */
    public boolean isDroitdeJeu() {
        return droitdeJeu;
    }

    /**
     * Setter du droit de jeu
     *
     * @param droitdeJeu indique si le joueur peut jouer ou non
     */
    public void setDroitdeJeu(boolean droitdeJeu) {
        this.droitdeJeu = droitdeJeu;
    }

    /**
     * Getter la liste des items
     * @return la liste des items en possession du joueur
     */
    public ArrayList<Item> getListeItems(){
        return this.listeItems;
    }

    /**
     * Getter Gagner
     * @return true si le joueur à l'amulette en sa possession et qu'il a atteins la sortie
     */
    public boolean getGagner(){
        return this.gagner;
    }

    public boolean getAmulette(){
        return this.amulette;
    }

    public Point getPos(){
        return this.pos;
    }

}
