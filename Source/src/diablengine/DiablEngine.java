package diablengine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe principal du moteur
 */
public class DiablEngine {

    /**
     * Zone de rendu
     */
    protected static JPanel renderZone;
    /**
     * Fenetre de rendu
     */
    private JFrame mainFrame;
    /**
     * Classe attache au moteur
     */
    private DEPluggable pluggedObject;
    /**
     * Menu principal du jeu attache au moteur
     */
    private IMenu menu;
    /**
     * Numero de la derniere image rendu
     */
    private int latestFrameNumber = 1;
    /**
     * Tache asynchrone
     */
    private Timer worker;

    /**
     * Initialize le moteur
     * @param size taille de la fenetre
     */
    public void initRender(Dimension size) {

        renderZone = pluggedObject.render(size.width, size.height);

        mainFrame = new JFrame("DiableEngine");

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setContentPane(renderZone);
        mainFrame.setResizable(false);

        mainFrame.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been pressed.
             *
             * @param e
             */
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                pluggedObject.keyPressed(e);
            }
        });

        mainFrame.pack();
        mainFrame.validate();
        mainFrame.setVisible(true);
    }

    /**
     * Lance le moteur
     * @param refreshRate fps vise
     */
    public void run(int refreshRate) {
        pluggedObject.init(this);

        Timer timedTasker = new Timer();
        timedTasker.schedule(new TimerTask() {
            @Override
            public void run() {
                pluggedObject.fixedUpdate(latestFrameNumber);
                System.out.println(this.toString() + " : Frame number " + latestFrameNumber++);
                renderZone.repaint();
            }
        }, 0, 1000 / refreshRate);

        worker = timedTasker;
    }

    /**
     * Stop le moteur
     */
    public void stop() {
        if (worker != null) {
            worker.cancel();
            worker.purge();
        }

        //TODO implemente la mort
        if(menu == null) System.exit(0);

        menu.displayMainMenu();
        mainFrame.dispose();
    }

    /**
     * Initialize et lance le moteur
     * @param object
     * @param size
     * @param refreshRate
     * @return
     */
    public static DiablEngine plugAndPlay(DEPluggable object, Dimension size, int refreshRate) {
        return plugAndPlay(object, size, refreshRate, null);

    }

    /**
     * Initialize et lance le moteur
     * @param object
     * @param size
     * @param refreshRate
     * @return
     */
    public static DiablEngine plugAndPlay(DEPluggable object, Dimension size, int refreshRate,IMenu menu) {
        if (object != null) {
            DiablEngine moteur = new DiablEngine(object);
            moteur.menu = menu;
            moteur.initRender(size);
            moteur.run(refreshRate);
            return moteur;
        }
        throw new Error("Objet passe null");

    }

    /**
     * Constructeur
     * @param object objet a attache
     */
    public DiablEngine(DEPluggable object) {
        pluggedObject = object;
    }

}
