package GameObjects;

import Collision.*;
import java.awt.*;
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

        playerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics x = playerImage.getGraphics();
        BufferedImage rawImage = ImageIO.read(new File("Figure.png"));
        Image tempImage = rawImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        x.drawImage(tempImage, 0, 0, null);
        x.dispose();
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

    public boolean collide(BasicEnemy enemy) {
        BoxCollider eHitbox = enemy.box;
        if((this.box.tLeft.x <= eHitbox.tRight.x && this.box.bLeft.x <= eHitbox.bRight.x) &&
            (this.box.bLeft.y <= eHitbox.bRight.y && this.box.bLeft.y >= eHitbox.tRight.y)) {
                System.out.println("Collide");
                return true;
            }

        return false;
    }
}
