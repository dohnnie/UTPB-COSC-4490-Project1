package GameObjects;

import Collision.*;
import Enums.CollisionType;

public class StaticObject { 
    public BoxCollider box;
    
    public StaticObject(int xPos, int yPos, int width, int height, CollisionType cType) {
        box = new BoxCollider(xPos, yPos, width, height);
    }
}
