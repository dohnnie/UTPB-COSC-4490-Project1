package GameObjects;

import Collision.*;
import java.awt.Point;
public class PhysicsObject {
    
    public int width, height;
    public double xVel, yVel;
    public double acceleration;
    public static double gravity = 0.2f;
    public Point root, initPoint;
    public BoxCollider box;
    public CircleCollider circle;
    public BoxSides cDirection;

    public PhysicsObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        initPoint = new Point(xPos, yPos);
        root = new Point(xPos, yPos);
        this.width = width;
        this.height = height;
        this.xVel = 0;
        this.yVel = 0;
        switch (cType) {
            case BOX -> {
                box = new BoxCollider(root.x, root.y, width, height); 
            }
            case CIRCLE -> {
                //Remember to change this to the center of the circle rather than top left of a box
                circle = new CircleCollider(root.x, root.y);
            }
        }
    }

    public void reset() {
        xVel = 0;
        yVel = 0;
        root.x = initPoint.x;
        root.y = initPoint.y;
    }

    public void calculate_velocity(double acceleration) {

    }

    public void calculate_position(double velocity) {

    }

    /*public void updateObjectPoints(BoxCollider box) {
        this.tLeft.x = box.tLeft.x;
        this.tLeft.y = box.tLeft.y;
        this.tRight.x = box.tRight.x;
        this.tRight.y = box.tRight.y;
        this.bLeft.x = box.bLeft.x;
        this.bLeft.y = box.bLeft.y;
        this.bRight.x = box.bRight.x;
        this.bRight.y = box.bRight.y;
    }*/

    //Modify to accept an array of platforms
    public void update(BoxCollider obj) {
        box.updateColliderPoints(root.x, root.y);
        cDirection = box.collide(obj);
        switch(cDirection) {
            case TOP -> {
                int offset =  box.bLeft.y - obj.tLeft.y;
                box.tLeft.y -= offset;
                box.tRight.y -= offset;
                box.bLeft.y -= offset;
                box.bRight.y -= offset;
                yVel = 0.0f;
            }
            case LEFT -> {
                int offset = box.tRight.x - obj.tLeft.x;
                box.tLeft.x -= offset;
                box.tRight.x -= offset;
                box.bLeft.x -= offset;
                box.bRight.x -= offset;
                yVel += gravity;
            }
            case RIGHT -> {
                int offset = obj.tRight.x - box.tLeft.x;
                box.tLeft.x += offset;
                box.tRight.x += offset;
                box.bLeft.x += offset;
                box.bRight.x += offset;
                yVel += gravity;
            }
            case BOTTOM -> {
                int offset = obj.bLeft.y - obj.tLeft.y;
                box.tLeft.y += offset;
                box.tRight.y += offset;
                box.bLeft.y += offset;
                box.bRight.y += offset;
                yVel += gravity;
            }
            case NONE -> {
                yVel += gravity;
            }
        }
        root.y += yVel;
    }
}
