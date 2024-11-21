package GameObjects;

import Collision.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import src.Game;

public class Player extends PhysicsObject{
    Game game;
    Toolkit tk;

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
        g.drawImage(playerImage, this.tLeft.x, this.tLeft.y, null);

        g.setColor(Color.red);
        g.drawRect(this.tLeft.x, this.tLeft.y, this.width, this.height);
    }

    public void move(double acceleration) {
        xVel = acceleration;
        this.tLeft.x += xVel;
    }

    public void jump() {
        this.yVel -= 10;
        this.tLeft.y += this.yVel;
    }

    public boolean worldBounds(double screenWidth, double screenHeight) {
        if(this.tLeft.x >= screenWidth || this.tLeft.y >= screenHeight) {
            System.out.println("Out of bounds");
            return true;
        }

        return false;
    }
}
