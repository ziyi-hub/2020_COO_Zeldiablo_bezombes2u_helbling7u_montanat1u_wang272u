package jeu;

import java.awt.*;
import java.util.ArrayList;

public abstract class Personnage {

    /**
     * Position sur l'axe x
     */
    private int x;
    /**
     * Position sur l'axe y
     */
    private int y;
    /**
     * Nombre de points de vie
     */
    private int pv;
    /**
     * Maximum de points de vie du personnage
     */
    private int pvMax;
    /**
     * jeu.Labyrinthe attache
     */
    private Jeu jeu;

    /**
     * Defini si une animation d'attaque doit etre jouer
     */
    public boolean attaqueAnim = false;

    public int getPv() {
        return pv;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Constructeur d'un personnage
     *
     * @param x   position en x
     * @param y   position en y
     * @param pv  nombre de pv
     * @param jeu jeu.Jeu auquel l'attache
     */
    public Personnage(int x, int y, int pv, Jeu jeu) {

        try {
            if (!jeu.getLabyrinthe().getAccessible(x, y)) {
                throw new IllegalArgumentException("Impossible de créer un personnage dans un mur");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Impossible de créer un personnage dans un mur ou hors du terrain");
        }


        this.x = x;
        this.y = y;
        this.pv = pv;
        this.pvMax = pv;
        this.jeu = jeu;
    }


    /**
     * Permet de tenter de deplacer le personnage, l'action ne s'effectura pas si une collision avec un mur est detecte
     *
     * @param direction (Direction sur le pave numerique ->
     *                  <p>2 = bas</p>
     *                  <p>6 = droite</p>
     *                  <p>8 = haut</p>
     *                  <p>4 = gauche</p>
     */
    public void seDeplacer(int direction) {
        int tempX = x;
        int tempY = y;

        switch (direction) {
            case 2:
                tempY++;
                break;
            case 6:
                tempX++;
                break;
            case 8:
                tempY--;
                break;
            case 4:
                tempX--;

        }
        if (jeu.getLabyrinthe().getAccessible(tempX, tempY) && !isTherePersonnage(new Point(tempX, tempY))) {
            x = tempX;
            y = tempY;
        }
    }

    /**
     * Permet de verifier si un personnage se trouve sur cette case
     *
     * @param p coordonees de la case a verifier
     * @return true si il y a effectivement un personnage sur la case p
     */
    public boolean isTherePersonnage(Point p) {
        for (Personnage m : jeu.getListeMonstres()) {
            if (p.equals(new Point(m.getX(), m.getY()))) {
                return true;
            }
        }

        if (p.equals(new Point(jeu.getHeros().getX(), jeu.getHeros().getY()))) {
            return true;
        }

        return false;
    }

    /**
     * Permet de savoir quels sont les personnages qui se trouvent aux alentours du personnage appelant
     *
     * @return liste des personnages aux alentours
     */
    public ArrayList<Personnage> getPersonnageAlentours() {
        ArrayList<Point> nearbyPoints = new ArrayList<Point>();
        ArrayList<Personnage> result = new ArrayList<Personnage>();

        nearbyPoints.add(new Point(x, y - 1));
        nearbyPoints.add(new Point(x, y + 1));
        nearbyPoints.add(new Point(x - 1, y));
        nearbyPoints.add(new Point(x + 1, y));

        for (Personnage m : jeu.getListeMonstres()) {
            if (nearbyPoints.contains(new Point(m.getX(), m.getY()))) {
                result.add(m);
            }
        }

        if (nearbyPoints.contains(new Point(jeu.getHeros().getX(), jeu.getHeros().getX()))) {
            result.add(jeu.getHeros());
        }

        return result;
    }

    /**
     * Accesseur jeu.Labyrinthe
     *
     * @return jeu.Labyrinthe attache au personnage
     */
    public Jeu getJeu() {
        return jeu;
    }

    /**
     * Nombre de points de vie max
     *
     * @return
     */
    public int getPvMax() {
        return pvMax;
    }

    /**
     * Point d'entree d'action du personnage
     */
    public abstract void lancerTour();

    public boolean attaquer(Personnage cible) {
        attaqueAnim = true;
        if (cible != null) {
            cible.subirDegats();
            return true;
        }
        return false;
    }

    /**
     * Methode permettant de faire subir des degats a un personnage ou de le tuer si son nombre de pv <= 1
     */
    public void subirDegats() {
        this.pv--;
        if (pv <= 0) {
            jeu.personnageMort(this);
        }
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

}
