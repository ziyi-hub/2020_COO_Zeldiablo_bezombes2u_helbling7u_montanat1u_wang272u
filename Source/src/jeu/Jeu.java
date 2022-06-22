package jeu;

import diablengine.DEPluggable;
import diablengine.DiablEngine;
import diablengine.Prebuilt;
import diablengine.StaticSpritePalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe permettant d'executer le jeu
 */
public class Jeu implements DEPluggable {

    /**
     * Reference public du jeu
     */
    public static Jeu JeuEnCours;

    /**
     * Liste des monstres presents dans le labyrinthe
     */
    private ArrayList<Personnage> listeMonstres;

    /**
     * jeu.Heros joue par le joueur
     */
    private final Personnage heros;

    /**
     * jeu.Labyrinthe de la partie
     */
    private final Labyrinthe laby;

    /**
     * Moteur en charge de cette classe pluggable
     */
    private DiablEngine moteur;

    /**
     * Constructeur par defaut, il permet de lancer une partie sur le labyrinthe par defaut avec 3 monstres
     */
    public Jeu() {
        JeuEnCours = this;
        this.laby = new Labyrinthe();
        this.heros = new Heros(5, 5, 5, this, "Joueur");
        genererMonstres(3);
        this.laby.placeItem();
    }


    /**
     * Permet de placer un nombre de monstres donne aleatoirement sur le terrain
     *
     * @param nbDeMonstres nombre de monstres a palcer
     */
    private void genererMonstres(int nbDeMonstres) {
        Random rng = new Random(LocalTime.now().getNano());

        listeMonstres = new ArrayList<Personnage>();
        ArrayList<Integer> previousX = new ArrayList<Integer>();
        ArrayList<Integer> previousY = new ArrayList<Integer>();

        if (this.laby.getNbCasesLibres() < nbDeMonstres)
            throw new Error("Il n'y a pas assez d'espace pour placer tout ces monstres");

        for (int i = 0; i < nbDeMonstres; i++) {
            boolean isPositionValid = false;

            while (!isPositionValid) {
                int x = rng.nextInt(this.laby.getTaille());
                int y = rng.nextInt(this.laby.getTaille());

                if (this.laby.getAccessible(x, y) && (!previousX.contains(x) || !previousY.contains(y)) && (x != heros.getX() || y != heros.getX())) {
                    previousX.add(x);
                    previousY.add(y);
                    listeMonstres.add(new Monstre(x, y, this));
                    isPositionValid = true;
                }
            }
        }
    }

    /**
     * Getter de listeMonstres
     *
     * @return liste des monstres attache au labyrinthe
     */
    public ArrayList<Personnage> getListeMonstres() {
        return listeMonstres;
    }

    /**
     * Getter de heros
     *
     * @return heros attache au labyrinthe
     */
    public Personnage getHeros() {
        return heros;
    }

    /**
     * Getter du labyrinthe
     *
     * @return labyrinthe de la partie actuelle
     */
    public Labyrinthe getLabyrinthe() {
        return laby;
    }

    /**
     * Getter random de la liste monstre
     *
     * @return personnage de la liste monstre
     */
    public Personnage getRandomMonstre() {
        int i = new Random(LocalTime.now().getNano()).nextInt(listeMonstres.size());
        return listeMonstres.get(i);
    }

    /**
     * Lorsqu'un personnage n'a plus de points de vie, il meurt
     * quand héro meurt et disparait du jeu
     * quand monstre meurt, on le supprime dans la liste de Monstres
     * @param perso personnage
     */
    public void personnageMort(Personnage perso) {
        if (perso instanceof Heros) {
            if (perso.getPv() <= 0) {
                System.out.println("Le heros meurt");
            }
        } else {
            listeMonstres.remove(perso);
        }
    }

    /**
     * Permet de rendre l'objet graphiquement
     *
     * @return JPanel du rendu
     */
    @Override
    public JPanel render(int x, int y) {
        JPanel result = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int tailleCase = x / 10;

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {

                        int originCaseX = i * tailleCase;
                        int originCaseY = j * tailleCase;

                        g.setColor(new Color(0, 0, 0));
                        if (getLabyrinthe().getAccessible(i, j)) {

                            if(i==5&&j==5){
                                g.drawImage(StaticSpritePalette.pickFromPalette("sortie"),originCaseX, originCaseY, tailleCase, tailleCase,null);
                            }else {
                                g.drawImage(StaticSpritePalette.pickFromPalette("ground"),originCaseX, originCaseY, tailleCase, tailleCase,null);
                            }

                        } else {
                            g.drawImage(StaticSpritePalette.pickFromPalette("wall"),originCaseX, originCaseY, tailleCase, tailleCase,null);
                        }

                        for(Item item : getLabyrinthe().getItem()) {
                            if (item.getX() == i && item.getY() == j && item instanceof Amulette) {
                                g.drawImage(StaticSpritePalette.pickFromPalette("amulette"), originCaseX, originCaseY, tailleCase, tailleCase, null);
                            }
                        }

                        for (Personnage m : listeMonstres) {
                            if (m.getX() == i && m.getY() == j) {
                                String state = "monstre";
                                if(m.attaqueAnim) {
                                    state = "monstre_attaque";
                                    m.attaqueAnim = false;
                                }
                                g.drawImage(StaticSpritePalette.pickFromPalette(state),originCaseX, originCaseY, tailleCase, tailleCase,null);
                                g = Prebuilt.drawLifeBar(g, originCaseX, originCaseY, tailleCase, tailleCase / 10, m.getPv(), m.getPvMax(), -10);
                            }
                        }

                        if (getHeros().getX() == i && getHeros().getY() == j) {
                            String state = "heros";
                            if(getHeros().attaqueAnim) {
                                state = "heros_attaque";
                                getHeros().attaqueAnim = false;
                            }

                            g.drawImage(StaticSpritePalette.pickFromPalette(state),originCaseX, originCaseY, tailleCase, tailleCase,null);

                            g = Prebuilt.drawLifeBar(g, originCaseX, originCaseY, tailleCase, tailleCase / 10, getHeros().getPv(), getHeros().getPvMax(), -10);
                        }
                    }
                }
            }
        };
        result.setPreferredSize(new Dimension(x, y));
        return result;
    }

    /**
     * Est appele quand le joueur appuis sur une touche du clavier
     *
     * @param e KeyEvent correspondant à l'appuis
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Heros lHeros = (Heros) getHeros();

        if (lHeros.isDroitdeJeu()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    lHeros.seDeplacer(8);
                    break;
                case KeyEvent.VK_DOWN:
                    lHeros.seDeplacer(2);
                    break;
                case KeyEvent.VK_LEFT:
                    lHeros.seDeplacer(4);
                    break;
                case KeyEvent.VK_RIGHT:
                    lHeros.seDeplacer(6);
                    break;
                case KeyEvent.VK_SPACE:
                    lHeros.attaquerAlentours();
                    break;
                case KeyEvent.VK_ESCAPE:
                    moteur.stop();
            }
        }
    }

    /**
     * Fonction qui sera appele au demarrage de diablEngine
     */
    @Override
    public void init(DiablEngine arg) {
        moteur = arg;
    }

    /**
     * Fonction qui sera appele a interval regulier (interval qui dependra du taux de rafraichissement visee)
     */
    @Override
    public void fixedUpdate(int latestFrameNumber) {


        if (getHeros().getPv() <= 0 || ((Heros)getHeros()).getGagner()) {
            moteur.stop();

        }

        if (latestFrameNumber % 10 == 0) {
            for (Personnage p : listeMonstres) {
                if (p instanceof Monstre) {
                    p.lancerTour();
                }
            }

            Heros h = (Heros)getHeros();
            h.setDroitdeJeu(true);
        }
    }
}
