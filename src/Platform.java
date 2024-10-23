package src;

import java.awt.Graphics;
import java.awt.Color;

public class Platform {

    int xPos, yPos;
    int width, height;
    int[] tLeft = new int[2];
    int[] tRight = new int[2];
    int[] bLeft = new int[2];
    int[] bRight = new int[2];

    Platform(int x, int y, int w, int h) {

        xPos = x;
        yPos = y;
        width = w;
        height = h;
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
