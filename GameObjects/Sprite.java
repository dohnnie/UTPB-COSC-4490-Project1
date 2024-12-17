package GameObjects;

import Collision.BoxCollider;
import javax.imageio.ImageIO;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import src.*;

public class Sprite {
    BufferedImage playerImage;
    Game game;
    BoxCollider box;
    Point origin; // Top left corner, where the image starts being drawn
    int width, height;
    float xVel, yVel;

    Sprite(String imageFile, Game game, Point origin, int w, int h) throws IOException {
        this.game = game;
        this.origin = origin;
        width = w;
        height = h;
        box = new BoxCollider(origin.x, origin.y, width, height);

        playerImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics x = playerImage.getGraphics();
        BufferedImage rawImage = ImageIO.read(new File(imageFile));
        Image tempImage = rawImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        x.drawImage(tempImage, 0, 0, null);
        x.dispose();
        xVel = 0;
        yVel = 0;
    }

    public Sprite(String imageFile, Game game, Point origin) throws IOException{
        this(imageFile, game, origin, 100, 100);
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, origin.x, origin.y, null);
    }

    public void update() {

    }
}
