package GameObjects;

import Collision.*;
import Enums.CollisionType;

import java.awt.Color;
import java.awt.Graphics;

public class Platform extends StaticObject{

    public Platform(int x, int y, int w, int h) {
        super(x, y, w, h, CollisionType.BOX);
    }

    public void drawPlatform(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(box.tLeft.x, box.tLeft.y, box.width, box.height);
    }
}
