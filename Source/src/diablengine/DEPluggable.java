package diablengine;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Interface entre le moteur et une classe principale d'un jeu l'utilisant
 */
public interface DEPluggable {

    /**
     * Fonction qui sera appele au demarrage de diablEngine
     */
    void init(DiablEngine de);

    /**
     * Fonction qui sera appele a interval regulier (interval qui dependra du taux de rafraichissement visee)
     */
    void fixedUpdate(int latestFrameNumber);

    /**
     * Permet de rendre l'objet graphiquement
     *
     * @return JPanel du rendu
     */
    JPanel render(int width, int height);

    /**
     * Est appele quand le joueur appuis sur une touche du clavier
     *
     * @param e KeyEvent correspondant à l'appuis
     */
    void keyPressed(KeyEvent e);
}
