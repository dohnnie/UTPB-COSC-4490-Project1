package Collision;

import java.awt.Point;

import Enums.Directions;
import GameObjects.Sprite;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

public class BoxCollider {
    public Point center;
    public int width, height;

    public BoxCollider(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;

        center = new Point(x + (width / 2), y + (height / 2));
    }

    public void drawBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(center.x - (width / 2), center.y - (height / 2), width, height);
    }

    public float getLeft() {
        return center.x - (width / 2);
    }

    public void setLeft(float newLeft) {
        center.x = (int) newLeft + (width / 2);
    }

    public float getRight() {
        return center.x + (width / 2);
    }

    public void setRight(float newRight) {
        center.x = (int) newRight - (width / 2);
    }

    public float getTop() {
        return center.y - (height / 2);
    }

    public void setTop(float newTop) {
        center.y = (int) newTop + (height / 2);
    }

    public float getBottom() {
        return center.y + (height / 2);
    }

    public void setBottom(float newBottom) {
        center.y = (int) newBottom - (height / 2);
    }

    public static boolean checkCollision(Sprite s1, Sprite s2) {
        boolean noXOverlap = s1.box.getRight() <= s2.box.getLeft() || s1.box.getRight() >= s2.box.getRight();
        boolean noYOverlap = s1.box.getBottom() <= s2.box.getTop() || s1.box.getTop() >= s2.box.getBottom();

        if(noXOverlap || noYOverlap) {
            return false;
        }
        else {
            return true;
        }
    }

    public static ArrayList<Sprite> checkCollisionList(Sprite sprite, ArrayList<Sprite> list) {
        ArrayList<Sprite> collisionList = new ArrayList<>();
        for(Sprite toCollide : list) {
            if(checkCollision(sprite, toCollide)) {
                collisionList.add(toCollide);
            }
        }

        return collisionList;
    }

    // General use case for collisions between a box, and a physics box
    /*public Directions collide(BoxCollider obj) {
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
    }*/
}