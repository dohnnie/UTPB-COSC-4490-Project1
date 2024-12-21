package Collision;

import GameObjects.Sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BoxCollider {
    public float centerX, centerY;
    public int width, height;

    public BoxCollider(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;

        centerX = x + (width / 2);
        centerY = y + (height / 2);
    }

    public void drawBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int)centerX - (width / 2), (int)centerY - (height / 2), width, height);
    }

    public float getLeft() {
        return centerX - (width / 2);
    }

    public void setLeft(float newLeft) {
        centerX = newLeft + (width / 2);
    }

    public float getRight() {
        return centerX + (width / 2);
    }

    public void setRight(float newRight) {
        centerX = newRight - (width / 2);
    }

    public float getTop() {
        return centerY - (height / 2);
    }

    public void setTop(float newTop) {
        centerY = newTop + (height / 2);
    }

    public float getBottom() {
        return centerY + (height / 2);
    }

    public void setBottom(float newBottom) {
        centerY = newBottom - (height / 2);
    }

    public static boolean checkCollision(Sprite s1, Sprite s2) {
        boolean noXOverlap = s1.box.getRight() <= s2.box.getLeft() || s1.box.getRight() >= s2.box.getRight();
        boolean noYOverlap = s1.box.getBottom() <= s2.box.getTop() || s1.box.getTop() >= s2.box.getBottom();

        if(noXOverlap && noYOverlap) {
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

    public static void resolvePlatformCollisions(Sprite s, ArrayList<Sprite> walls) {
        s.yVel += s.GRAVITY;

        s.box.centerY += s.yVel;
        ArrayList<Sprite> colList = BoxCollider.checkCollisionList(s, walls);
        //System.out.println("Vertical Collision List: " + colList.size());
        if(colList.size() > 0) {
            Sprite collided = colList.get(0);
            if(s.yVel > 0) {
                s.box.setBottom(collided.box.getTop());
            } else if(s.yVel < 0){
                s.box.setTop(collided.box.getBottom());
            }
            s.yVel = 0;
        }

        s.box.centerX += s.xVel;
        colList = BoxCollider.checkCollisionList(s, walls);
        //System.out.println("Horizontal Collision List: " + colList.size());
        if(colList.size() > 0) {
            Sprite collided = colList.get(0);
            if(s.xVel > 0) {
                s.box.setRight(collided.box.getLeft());
            } else if(s.xVel < 0){
                s.box.setLeft(collided.box.getRight());
            }
            s.xVel = 0;
        }
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