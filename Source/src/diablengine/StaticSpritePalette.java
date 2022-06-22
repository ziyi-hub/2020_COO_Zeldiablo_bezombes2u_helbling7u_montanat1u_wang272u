package diablengine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe de gestion des sprites du jeu
 */
public class StaticSpritePalette {

    /**
     * Stock les sprites acutellement charges
     */
    private static Map<String, BufferedImage> palette = new HashMap<String, BufferedImage>();

    /**
     * Permet de charger des sprites dans la palette
     * @param pathToSource chemin vers la sprite sheet
     * @param label label du sprite dans la palette
     * @param x position x du sprite dans la sprite sheet
     * @param y position y du sprite dans la sprite sheet
     * @param hauteur hauteur du sprite dans la spritesheet
     * @param largeur largeur du sprite dans la spritesheet
     */
    public static void addToPalette(String pathToSource, String label, int x, int y, int hauteur, int largeur) {

        BufferedImage source;

        try {
            source = ImageIO.read(new File(pathToSource));
        } catch (Exception e) {
            throw new Error();
        }

        palette.put(label, source.getSubimage(x, y, largeur, hauteur));

    }

    /**
     * Permet de recuperer un sprite de la palette
     * @param label label du sprite dans la palette (defini lors de l'appel à addToPalette) - ATTENTION RETOURNE NULL SI LABEL NON AFFECTE
     * @return le sprite voulu
     */
    public static BufferedImage pickFromPalette(String label){
        if(label != null) return palette.get(label);
        return null;
    }

}
