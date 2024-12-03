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

    public void update(Platform platform) {
        box.updatePoints(this.tLeft.x, this.tLeft.y);
        boolean isColliding = this.box.collide(platform);
        if(isColliding) {
            box.bLeft.y -= platform.box.tLeft.y;
            box.bRight.y -= platform.box.tLeft.y;
            yVel = 0.0f;
        }
        else {
            yVel += gravity;
        }
        root.y += yVel;
    }
}
