import diablengine.DiablEngine;
import diablengine.IMenu;
import diablengine.StaticSpritePalette;
import jeu.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Launcher du jeu
 */
public class Zeldiablo implements IMenu {

    /**
     * instance du moteur qui fait tourner le jeu
     */
    public DiablEngine de;

    public static void main(String[] args) {

        new Zeldiablo();

    }

    /**
     * Constructeur par defaut
     */
    public Zeldiablo() {
        loadRessources();
        displayMainMenu();
    }

    /**
     * Permet d'afficher le menu principal du jeu (Implementation de IMenu pour connection avec le moteur)
     */
    public void displayMainMenu() {
        JFrame menu = new JFrame("Zeldiablo : Menu");
        JPanel jp = new JPanel(new BorderLayout());

        JPanel panel_bouttons = new JPanel(new BorderLayout());
        JButton lancerPartie = new JButton("Lancer partie");
        JButton quitterJeu = new JButton("Quitter le jeu");

        lancerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jeu j = new Jeu();

                de = DiablEngine.plugAndPlay(j, new Dimension(500, 500), 20, Zeldiablo.this);
                menu.dispose();
            }
        });

        quitterJeu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel_bouttons.add(lancerPartie, BorderLayout.NORTH);
        panel_bouttons.add(quitterJeu, BorderLayout.SOUTH);

        jp.add(art_panel());
        jp.add(panel_bouttons, BorderLayout.SOUTH);

        menu.setSize(300, 200);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setResizable(false);
        menu.setContentPane(jp);
        menu.setVisible(true);
    }

    /**
     * @return Un jpanel generer pour creer le fond dans le menu principale
     */
    private JPanel art_panel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int j = 0; j <= getHeight(); j += 64) {
                    for (int i = 0; i <= getWidth(); i += 64) {
                        g.drawImage(StaticSpritePalette.pickFromPalette("wall"), i, j, 64, 64, null);
                    }
                }

                g.drawImage(StaticSpritePalette.pickFromPalette("heros_attaque"), (getWidth()/2)-128, (getHeight()/2)-64, 128, 128, null);
                g.drawImage(StaticSpritePalette.pickFromPalette("monstre_attaque"), (getWidth()/2)+22, (getHeight()/2)-40, 80, 80, null);

            }
        };
    }

    private void loadRessources() {
        StaticSpritePalette.addToPalette("tileset.png", "ground", 0, 0, 16, 16);
        StaticSpritePalette.addToPalette("tileset.png", "sortie", 0, 16, 16, 16);
        StaticSpritePalette.addToPalette("tileset.png", "wall", 16, 0, 16, 16);
        StaticSpritePalette.addToPalette("tileset.png", "heros", 32, 0, 32, 32);
        StaticSpritePalette.addToPalette("tileset.png", "heros_attaque", 32, 32, 32, 32);
        StaticSpritePalette.addToPalette("tileset.png", "monstre", 0, 32, 16, 16);
        StaticSpritePalette.addToPalette("tileset.png", "monstre_attaque", 16, 32, 16, 16);
        StaticSpritePalette.addToPalette("tileset.png", "amulette", 0, 48, 8, 8);

    }

}
