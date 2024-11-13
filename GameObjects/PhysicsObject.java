package GameObjects;

import Collision.*;

public class PhysicsObject {
    
    int xPos, yPos;
    int width, height;
    double xVel, yVel;
    double acceleration;
    double gravity;
    public Collider collider;

    public PhysicsObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        switch (cType) {
            case BOX:
                collider = new BoxCollider(this.xPos, this.yPos); 
                collider = (BoxCollider)collider;
                break;
            case CIRCLE:
                collider = new CircleCollider(this.xPos, this.yPos);
                collider = (CircleCollider)collider;
                break;
        }
    }

    public void calculate_velocity(double acceleration) {

    }

    public void calculate_position(double velocity) {

    }

    public void update() {
        
    }
}
