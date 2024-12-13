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
    public BoxSides direction;
    int init_jump_count = 2; 
    public int jumps = init_jump_count;
    double pMaxYSpeed = -2;

    BufferedImage playerImage;

    public Player(Game g, Toolkit tk, int size, int xPos, int yPos) throws IOException {
        super(xPos, yPos,size, size, CollisionType.BOX);

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
        g.drawImage(playerImage, this.box.tLeft.x, this.box.tLeft.y, null);

        g.setColor(Color.red);
        g.drawRect(this.box.tLeft.x, this.box.tLeft.y, this.width, this.height);
    }

    public void move() {
        root.x += xVel;
    }

    public void jump() {
        if(jumps > 0) {
            yVel -= 10;
            root.y += yVel;
            jumps--;
        }
    }

    @Override
    public void update(BoxCollider obj) {
        super.update(obj);
        if(this.cDirection == BoxSides.TOP)
            jumps = init_jump_count;
    }

    public boolean worldBounds(double screenWidth, double screenHeight) {
        if(this.box.tLeft.x >= screenWidth || this.box.tLeft.y >= screenHeight) {
            System.out.println("Out of bounds");
            return true;
        }

        return false;
    }
}
