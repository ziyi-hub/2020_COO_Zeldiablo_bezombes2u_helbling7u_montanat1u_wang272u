package diablengine;

/**
 * Permet de connecter une classe afin qu'elle gere un menu quand le jeu se stop
 */
public interface IMenu {
    /**
     * Affiche un menu principal (est appele par DiableEngine lorsque qu'on l'on execute stop())
     */
    void displayMainMenu();
}
