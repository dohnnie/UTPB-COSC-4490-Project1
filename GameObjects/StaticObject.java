package GameObjects;

import Collision.*;
import Enums.CollisionType;

public class StaticObject { 
    public BoxCollider box;
    public CircleCollider circle;
    
    public StaticObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        switch (cType) {
            case BOX -> {
                box = new BoxCollider(xPos, yPos, width, height);
            }
           case CIRCLE -> {
                circle = new CircleCollider(xPos, yPos);
            }
        }
    }
}
