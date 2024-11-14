package GameObjects;

import java.awt.Toolkit;
import java.io.IOException;

import Collision.*;
import src.Game;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.*;
import javax.imageio.ImageIO;

public class Player extends PhysicsObject{
    Game game;
    Toolkit tk;
    //int zLayer;


    double xVel = 0.0;
    double yAcc = 0.1;
    double yVel = 0.0;
    Point rootPoint;

    BufferedImage playerImage;

    public Player(Game g, Toolkit tk, int width, int height) throws IOException {
        super((int)(tk.getScreenSize().getWidth() / 2) - (width / 2),
                (int)(tk.getScreenSize().getHeight() / 2) - (height / 2),
                width,
                height,
                CollisionType.BOX);

        this.game = g;
        this.tk = tk;

        rootPoint = new Point(this.tLeft.x, this.tLeft.y);
        playerImage = ImageIO.read(new File("Figure.png"));
        Graphics x = playerImage.getGraphics();
        x.drawImage(playerImage, 0, 0, null);
    }

    public void drawPlayer(Graphics g) {
        g.drawImage(playerImage, rootPoint.x, rootPoint.y, null);

        g.setColor(Color.red);
        g.drawRect(rootPoint.x, rootPoint.y, this.width, this.height);
    }

    public void move(double acceleration) {
        xVel = acceleration;
        rootPoint.x += xVel;
    }

    public void jump() {
        rootPoint.y -= 15;
    }

   /*public boolean collide(Platform platform) {
        //Case 1: player.bRight is past platform.tLeft, and Case 2: player.bLeft is past platform.tRight
        if((bRight[0] >= platform.tLeft[0] && bRight[1] >= platform.tLeft[1]) && (bLeft[0] <= platform.tRight[0] && bLeft[1] >= platform.tRight[1])) {
                return true;
        }
        
        return false;
    }*/

    public boolean worldBounds(double screenWidth, double screenHeight) {
        if(rootPoint.x >= screenWidth || rootPoint.y >= screenHeight) {
            System.out.println("Out of bounds");
            return true;
        }

        return false;
    }

    /*public void update(boolean isColliding) {
    
    }*/

}
