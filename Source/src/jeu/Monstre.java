package jeu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe representant un monstre classique
 */
public class Monstre extends Personnage {

    /**
     * Constructeur d'un monstre
     *
     * @param x   position en x
     * @param y   position en y
     * @param jeu labyrinthe auquel l'attache
     */
    public Monstre(int x, int y, Jeu jeu) {
        super(x, y, 2, jeu);
    }


    @Override
    public void lancerTour() {
        Random rng = new Random();

        ArrayList<Point> alentourDuMonstre = new ArrayList<Point>();
        alentourDuMonstre.add(new Point(getX(), getY() - 1));
        alentourDuMonstre.add(new Point(getX(), getY() + 1));
        alentourDuMonstre.add(new Point(getX() + 1, getY()));
        alentourDuMonstre.add(new Point(getX() - 1, getY()));
        Point heros = new Point(Jeu.JeuEnCours.getHeros().getX(), Jeu.JeuEnCours.getHeros().getY());

        if (alentourDuMonstre.contains(heros)) {
            this.attaquer(Jeu.JeuEnCours.getHeros());
        } else {
            this.seDeplacer((rng.nextInt(4) + 1) * 2);
        }

    }
}
