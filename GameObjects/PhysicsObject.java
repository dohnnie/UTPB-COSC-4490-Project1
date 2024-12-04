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
        BoxSides collisionSide = box.collide(platform.box);
        switch(collisionSide) {
            case TOP -> {
                int left_offset = box.bLeft.y - platform.box.tLeft.y;
                int right_offset = box.bRight.y - platform.box.tLeft.y;
                box.tLeft.y -= left_offset;
                box.tRight.y -= right_offset;
                box.bLeft.y -= left_offset;
                box.bRight.y -= right_offset;
                yVel = 0.0f;
            }
            case LEFT -> {

            }
            case RIGHT -> {

            }
            case BOTTOM -> {

            }
            case NONE -> {
                yVel += gravity;
            }
        }
        root.y += yVel;
    }
}
