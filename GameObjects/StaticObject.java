package GameObjects;

import Collision.*;

public class StaticObject { 
    
    int xPos, yPos;
    int width, height;
    public BoxCollider box;
    public CircleCollider circle;
    
    public StaticObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        switch (cType) {
            case BOX -> {
                box = new BoxCollider(this.xPos, this.yPos, width, height);
            }
           case CIRCLE -> {
                circle = new CircleCollider(this.xPos, this.yPos);
            }
        }
    }
}
