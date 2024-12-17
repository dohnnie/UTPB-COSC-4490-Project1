package GameObjects;

import Collision.BoxCollider;
import javax.imageio.ImageIO;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import src.*;

public class Sprite {
    BufferedImage playerImage;
    Game game; //Might not need this
    public BoxCollider box;
    public float xVel, yVel;

    Sprite(String imageFile, Game game, Point origin, int w, int h) throws IOException {
        this.game = game;
        box = new BoxCollider(origin.x, origin.y, w, h);

        playerImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics x = playerImage.getGraphics();
        BufferedImage rawImage = ImageIO.read(new File(imageFile));
        Image tempImage = rawImage.getScaledInstance(box.width, box.height, Image.SCALE_SMOOTH);
        x.drawImage(tempImage, 0, 0, null);
        x.dispose();
        xVel = 0;
        yVel = 0;
    }

    public Sprite(String imageFile, Game game, Point origin) throws IOException{
        this(imageFile, game, origin, 100, 100);
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, (int)box.centerX - (box.width / 2), (int)box.centerY - (box.height / 2), null);
    }

    public void moveHorizontal() {
        box.centerX += xVel;
    }

    public void moveVertical() {
        box.centerY += yVel;
    }

    public void reset() {
        xVel = 0;
        yVel = 0;
    }

    public void update() {

    }
}
