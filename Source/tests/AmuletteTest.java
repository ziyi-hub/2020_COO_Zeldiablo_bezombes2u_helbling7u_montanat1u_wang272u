import jeu.*;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AmuletteTest {

    @Test
    /**
     * Permet de tester le placement de l'amulette
     */
    public void test_01_Amulette_Default(){
        //jeu.Jeu de donnees
        Item a = new Amulette(5,7);

        //Assertions
        Assert.assertEquals("x devrait etre 5",5,a.getX());
        Assert.assertEquals("y devrait etre 7",7,a.getY());
    }


}