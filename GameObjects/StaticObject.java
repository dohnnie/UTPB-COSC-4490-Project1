package GameObjects;

import Collision.*;

public class StaticObject { 
    
    int xPos, yPos;
    int width, height;
    public Collider collider; 
    
    public StaticObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        switch (cType) {
            case BOX:
                collider = new BoxCollider(this.xPos, this.yPos, width, height);
                break;
            case CIRCLE:
                collider = new CircleCollider(this.xPos, this.yPos);
                break;
        }
    }
}
