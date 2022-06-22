package diablengine;

import java.awt.*;

public class Prebuilt {

    public static Graphics drawLifeBar(Graphics g, int x, int y, int width, int height, int pv, int pvmax, int yOffset) {
        g.setColor(new Color(94, 94, 94));
        g.fillRect(x, y + yOffset, width, height);
        g.setColor(Color.green);
        g.fillRect(x, y + yOffset, width / pvmax * pv, height);

        return g;
    }

}
