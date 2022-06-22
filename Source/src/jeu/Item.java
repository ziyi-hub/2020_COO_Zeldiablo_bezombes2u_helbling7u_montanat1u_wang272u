package jeu;

/**
 * Classe abstraite representant un item
 */
public abstract class Item {
    /**
     * Position en x de l'item dans le labyrinthe
     */
    private int x;
    /**
     * Position en y de l'item dans le labyrinthe
     */
    private int y;

    /**
     * Constructeur par defaut de item
     *
     * @param x definit position en x de l'item dans le labyrinthe
     * @param y definit position en y de l'item dans le labyrinthe
     */
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter de X
     *
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter de Y
     *
     * @return y
     */
    public int getY() {
        return this.y;
    }
}
