package Collision;

import java.awt.Point;

import Enums.Directions;

import java.awt.Graphics;
import java.awt.Color;

public class BoxCollider {
    public Point tLeft, tRight, bLeft, bRight, center;
    public int width, height;
    public BoxCollider(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;

        //Since swing starts drawing from the top left of the image it'll be easier to make that the root point
        //and doing math to find stuff like the middle of the collider
        tLeft = new Point(x, y);
        tRight = new Point(x + width, y);
        bLeft = new Point(x, y + height);
        bRight = new Point(x + width, y + height);
        center = new Point(x + (width / 2), y + (height / 2));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void drawBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(tLeft.x, tLeft.y, width, height);
    }

    public void updatePoints(int xPos, int yPos) {
        tLeft.x = xPos;
        tLeft.y = yPos;
        tRight.x = xPos + width; 
        tRight.y = yPos;
        bLeft.x = xPos; 
        bLeft.y = yPos + height;
        bRight.x = xPos + width; 
        bRight.y = yPos + height;
        center.x = xPos + (width / 2);
        center.y = yPos + (height / 2);
    }

    // General use case for collisions between a box, and a physics box
    public Directions collide(BoxCollider obj) {
        if(this.tRight.x < obj.tLeft.x || obj.tRight.x < this.tLeft.x)
            return Directions.NONE;
        
        if(this.bRight.y < obj.tLeft.y || obj.bRight.y < this.tLeft.y) {
            return Directions.NONE;
        }

        double xDist = this.center.x - obj.center.x;
        double yDist = obj.center.y - this.center.y;
        double dist = Math.sqrt((xDist * xDist) + (yDist * yDist));
        double theta = Math.acos((xDist / yDist) / dist);
        int angle = (int)((theta * 8)/(Math.PI * 2));
        
        switch (angle) {
            case 0, 7 -> { 
                return Directions.LEFT;
            }
            case 1, 2 -> {
                return Directions.TOP;
            }
            case 3, 4 -> {
                return Directions.RIGHT;
            }
            case 5, 6 -> {
                return Directions.BOTTOM;
            }
            default -> {
                return Directions.NONE;
            }
        }
    }
}