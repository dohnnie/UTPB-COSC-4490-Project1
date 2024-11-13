package GameObjects;

import java.awt.Graphics;
import Collision.*;
import java.awt.Color;

public class Platform extends StaticObject{

    int[] tLeft = new int[2];
    int[] tRight = new int[2];
    int[] bLeft = new int[2];
    int[] bRight = new int[2];

    public Platform(int x, int y, int w, int h) {
        super(x, y, w, h, CollisionType.BOX);
        getCorners(xPos, yPos);
    }

    public void drawPlatform(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(xPos, yPos, width, height);
    }

    public void getCorners(int xPos, int yPos) {
        tLeft[0] = xPos;
        tLeft[1] = yPos;
        tRight[0] = xPos + width;
        tRight[1] = yPos;
        bLeft[0] = xPos;
        bLeft[1] = height + yPos;
        bRight[0] = width + xPos;
        bRight[1] = height + yPos;
    }
}
