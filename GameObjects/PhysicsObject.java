package GameObjects;

import Collision.*;
import java.awt.Point;
public class PhysicsObject {
    
    public int width, height;
    public double xVel, yVel;
    public double acceleration;
    public static double gravity = 0.1f;
    public Point tLeft, tRight, bLeft, bRight, root, initPoint;
    public BoxCollider box;
    public CircleCollider circle;

    public PhysicsObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        initPoint = new Point(xPos, yPos);
        tLeft = new Point(xPos, yPos);
        //Root point
        root = tLeft;
        tRight = new Point(xPos + width, yPos);
        bLeft = new Point(xPos, yPos + height);
        bRight = new Point(xPos + width, yPos + height);
        this.width = width;
        this.height = height;
        this.xVel = 0;
        this.yVel = 0;
        switch (cType) {
            case BOX -> {
                box = new BoxCollider(tLeft.x, tLeft.y, width, height); 
            }
            case CIRCLE -> {
                //Remember to change this to the center of the circle rather than top left of a box
                circle = new CircleCollider(tLeft.x, tLeft.y);
            }
        }
    }

    public void reset() {
        xVel = 0;
        yVel = 0;
        tLeft.x = initPoint.x;
        tLeft.y = initPoint.y;
    }

    public void calculate_velocity(double acceleration) {

    }

    public void calculate_position(double velocity) {

    }

    public void updateObjectPoints(BoxCollider box) {
        this.tLeft.x = box.tLeft.x;
        this.tLeft.y = box.tLeft.y;
        this.tRight.x = box.tRight.x;
        this.tRight.y = box.tRight.y;
        this.bLeft.x = box.bLeft.x;
        this.bLeft.y = box.bLeft.y;
        this.bRight.x = box.bRight.x;
        this.bRight.y = box.bRight.y;
    }

    public void update(Platform platform) {
        box.updateColliderPoints(tLeft.x, tLeft.y);
        /*if(this.getClass() == Player.class ) {
            System.out.println("Player tLeft: " + this.tLeft.x + ", Player tRight: " + this.tRight.x + ", Player bLeft: " + this.bLeft.y + ", Player bRight: " + this.bRight.y);
            System.out.println("Plat tLeft: " + platform.box.tLeft.x + ", Plat tRight: " + platform.box.tRight.x + ", Plat bLeft: " + platform.box.bLeft.y + ", Plat bRight: " + platform.box.bRight.y);
        }*/
        switch(box.collide(platform.box)) {
            case TOP -> {
                System.out.println("Collide Top");
                int left_offset = box.bLeft.y - platform.box.tLeft.y;
                int right_offset = box.bRight.y - platform.box.tLeft.y;
                box.tLeft.y -= left_offset;
                box.tRight.y -= right_offset;
                box.bLeft.y -= left_offset;
                box.bRight.y -= right_offset;
                yVel = 0.0f;
            }
            case LEFT -> {
                System.out.println("Collide Left!");
            }
            case RIGHT -> {
                System.out.println("Collide Right!");
            }
            case BOTTOM -> {
                System.out.println("Collide Bottom");
            }
            case NONE -> {
                yVel += gravity;
            }
        }
        /*if(box.collideTest2(platform.box)) {
            int left_offset = box.bLeft.y - platform.box.tLeft.y;
            int right_offset = box.bRight.y - platform.box.tLeft.y;
            box.tLeft.y -= left_offset;
            box.tRight.y -= right_offset;
            box.bLeft.y -= left_offset;
            box.bRight.y -= right_offset;
            yVel = 0.0f;
        } else {
            yVel += gravity;
        }*/
        root.y += yVel;
    }
}
