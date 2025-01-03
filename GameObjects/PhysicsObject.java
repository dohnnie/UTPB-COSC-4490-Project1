package GameObjects;

import Collision.*;
import Enums.CollisionType;
import Enums.Directions;
import java.util.ArrayList;

import java.awt.Point;
public class PhysicsObject {
    
    public int width, height;
    public double xVel, yVel;
    public double acceleration;
    public static double gravity = 0.2f;

    public Point root, initPoint;
    public BoxCollider box;
    public Directions cDirection;

    public PhysicsObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        initPoint = new Point(xPos, yPos);
        root = new Point(xPos, yPos);
        this.width = width;
        this.height = height;
        this.xVel = 0;
        this.yVel = 0;
        box = new BoxCollider(root.x, root.y, width, height); 
    }

    public void reset() {
        xVel = 0;
        yVel = 0;
        root.x = initPoint.x;
        root.y = initPoint.y;
    }

    //Modify to accept an array of platforms
    public void update(ArrayList<Platform> platforms) {
        //HashMap<Directions, BoxCollider> toCollide = new HashMap<>(); 
        box.updatePoints(root.x, root.y);
        for(Platform platform : platforms) {
            cDirection = box.collide(platform.box);
            //toCollide.put(cDirection, box);
            switch(cDirection) {
                case TOP -> {
                    int offset =  box.bLeft.y - platform.box.tLeft.y;
                    box.tLeft.y -= offset;
                    box.tRight.y -= offset;
                    box.bLeft.y -= offset;
                    box.bRight.y -= offset;
                    yVel = 0.0f;
                    break;
                }
                case LEFT -> {
                    int offset = box.tRight.x - platform.box.tLeft.x;
                    box.tLeft.x -= offset;
                    box.tRight.x -= offset;
                    box.bLeft.x -= offset;
                    box.bRight.x -= offset;
                    yVel += gravity;
                }
                case RIGHT -> {
                    int offset = platform.box.tRight.x - box.tLeft.x;
                    box.tLeft.x += offset;
                    box.tRight.x += offset;
                    box.bLeft.x += offset;
                    box.bRight.x += offset;
                    yVel += gravity;
                }
                case BOTTOM -> {
                    int offset = platform.box.bLeft.y - box.tLeft.y;
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
            //This line causes the loop to break after the first element so it's only checking the first element

            //break;
        }
        root.y += yVel;
    }
}
