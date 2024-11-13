package GameObjects;

import java.awt.Toolkit;
import java.io.IOException;

import Collision.*;
import src.Game;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import javax.imageio.ImageIO;

public class Player extends PhysicsObject{
    Game game;
    Toolkit tk;
    //int zLayer;


    double xVel = 0.0;

    double yAcc = 0.1;
    double yVel = 0.0;

    int[] tLeft = new int[2];
    int[] tRight = new int[2];
    int[] bLeft = new int[2];
    int[] bRight = new int[2];

    BufferedImage playerImage;

    public Player(Game g, Toolkit tk, int width, int height) throws IOException {
        super((int)(tk.getScreenSize().getWidth() / 2) - (width / 2),
                (int)(tk.getScreenSize().getHeight() / 2) - (height / 2),
                width,
                height,
                CollisionType.BOX);

        this.game = g;
        this.tk = tk;

        playerImage = ImageIO.read(new File("Figure.png"));
        Graphics x = playerImage.getGraphics();
        x.drawImage(playerImage, 0, 0, null);
    }

    public void drawPlayer(Graphics g) {
        g.drawImage(playerImage, xPos, yPos, null);

        g.setColor(Color.red);
        g.drawRect(xPos, yPos, this.width, this.height);
    }

    public void move(double acceleration) {
        xVel = acceleration;
        xPos += xVel;
    }

    public void jump() {
        yPos -= 15;
    }

   /*public boolean collide(Platform platform) {
        //Case 1: player.bRight is past platform.tLeft, and Case 2: player.bLeft is past platform.tRight
        if((bRight[0] >= platform.tLeft[0] && bRight[1] >= platform.tLeft[1]) && (bLeft[0] <= platform.tRight[0] && bLeft[1] >= platform.tRight[1])) {
                return true;
        }
        
        return false;
    }*/

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
        tRight[0] = xPos + width;
        tRight[1] = yPos;
        bLeft[0] = xPos;
        bLeft[1] = height + yPos;
        bRight[0] = width + xPos;
        bRight[1] = height + yPos;
    }

    public void update(boolean isColliding) {
        getCorners(this.xPos, this.yPos);
        if (isColliding) {
            yVel = 0;
        } else {
            yVel += yAcc;
        }
        yPos += yVel;
    }

}
