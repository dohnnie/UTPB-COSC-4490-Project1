package src;

import java.awt.Toolkit;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;

public class Player {
    Game game;
    Toolkit tk;
    //int zLayer;

    //Initial Position of player character
    int initX;
    int initY;

    //Global Position of player character
    int xPos;
    int yPos;

    //Size of player character
    int pWidth;
    int pHeight;

    double xVel = 0.0;

    double yAcc = 0.1;
    double yVel = 0.0;

    int[] tLeft = new int[2];
    int[] tRight = new int[2];
    int[] bLeft = new int[2];
    int[] bRight = new int[2];

    Player(Game g, Toolkit tk) throws IOException {
        this.game = g;
        this.tk = tk;

        this.pWidth = 70;
        this.pHeight = 150;

        initX = (int) (tk.getScreenSize().getWidth() / 2) - (pWidth / 2);
        initY = (int) (tk.getScreenSize().getHeight() / 2) - (pHeight / 2);

        xPos = initX;
        yPos = initY;
    }

    public void drawPlayer(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(xPos, yPos, pWidth, pHeight);
    }

    public void move(double acceleration) {
        xVel = acceleration;
        xPos += xVel;
    }

    public void jump() {
        yPos -= 15;
    }

   public boolean collide(Platform platform) {
        //Case 1: player.bRight is past platform.tLeft, and Case 2: player.bLeft is past platform.tRight
        if((bRight[0] >= platform.tLeft[0] && bRight[1] >= platform.tLeft[1]) && (bLeft[0] <= platform.tRight[0] && bLeft[1] >= platform.tRight[1])) {
                return true;
        }
        
        return false;
    }

    public boolean worldBounds(double screenWidth, double screenHeight) {
        if(tLeft[0] >= screenWidth || tLeft[1] >= screenHeight) {
            System.out.println("Out of bounds");
            return true;
        }

        return false;
    }

   public void getCorners(int xPos, int yPos) {
        tLeft[0] = xPos;
        tLeft[1] = yPos;
        tRight[0] = xPos + pWidth;
        tRight[1] = yPos;
        bLeft[0] = xPos;
        bLeft[1] = pHeight + yPos;
        bRight[0] = pWidth + xPos;
        bRight[1] = pHeight + yPos;
    }

    public void update(boolean collide) {
        getCorners(this.xPos, this.yPos);
        if (collide) {
            yVel = 0;
        } else {
            yVel += yAcc;
        }
        yPos += yVel;
    }

}
