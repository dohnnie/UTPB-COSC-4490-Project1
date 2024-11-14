package GameObjects;

import Collision.*;
import java.awt.Point;
public class PhysicsObject {
    
    public int width, height;
    public double xVel, yVel;
    public double acceleration;
    public double gravity;
    public Collider collider;
    public Point tLeft, tRight, bLeft, bRight;

    public PhysicsObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        //Root point
        tLeft = new Point(xPos, yPos);
        tRight = new Point(xPos + width, yPos);
        bLeft = new Point(xPos, yPos + height);
        bRight = new Point(xPos + width, yPos + height);
        this.width = width;
        this.height = height;
        switch (cType) {
            case BOX:
                collider = new BoxCollider(tLeft.x, tLeft.y, width, height); 
                collider = (BoxCollider)collider;
                break;
            case CIRCLE:
                //Remember to change this to the center of the circle rather than top left of a box
                collider = new CircleCollider(tLeft.x, tLeft.y);
                collider = (CircleCollider)collider;
                break;
        }
    }

    public void calculate_velocity(double acceleration) {

    }

    public void calculate_position(double velocity) {

    }

    public void updatePoints(int xPos, int yPos) {
        tLeft.x = xPos; tLeft.y = yPos;
        tRight.x = xPos + this.width; tRight.y = yPos;
        bLeft.x = xPos; bLeft.y = yPos + height;
        bRight.x = xPos + width; bRight.y = yPos + height;
    }

    public void update(boolean isColliding) {
        yVel -= 0.1;
        tLeft.y -=yVel;        
    }
}
