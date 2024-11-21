package GameObjects;
import Collision.*;

import java.awt.Color;
import java.awt.Graphics;

public class BasicEnemy extends PhysicsObject{
    
    public BasicEnemy(int x, int y, int width, int height) {
        super(x, y, width, height, CollisionType.BOX);
    }

    public void drawEnemy(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(this.tLeft.x, this.tLeft.y, this.width, this.height);
    }
}
