package src;

import java.awt.Toolkit;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;

public class Player {
    Game game;
    Toolkit tk;
    int zLayer;

    int initX;
    int initY;

    int xPos;
    int yPos;

    int pWidth;
    int pHeight;

    Player(Game g, Toolkit tk) throws IOException {
        this.game = g;
        this.tk = tk;

        this.pWidth = 50;
        this.pHeight = 50;

        initX = (int) (tk.getScreenSize().getWidth() / 2) - (pWidth / 2);
        initY = (int) (tk.getScreenSize().getHeight() / 2) - (pHeight / 2);

        xPos = initX;
        yPos = initY;
    }

    public void drawPlayer(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(xPos, yPos, pWidth, pHeight);
    }

    public void jump() {
        yPos -= 10;
    }

}
