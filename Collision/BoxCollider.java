package Collision;

import java.awt.Point;

public class BoxCollider extends Collider {
    Point tLeft, tRight, bLeft, bRight;

    public BoxCollider(int x, int y) {
        super(x, y);
    }

    // General use case for collisions between a box, and a physics box
    public boolean collide(BoxCollider obj) {
        // Case 1: When right side of object is colliding on top of the left side of this
        if( (obj.bRight.x >= this.tLeft.x && obj.bRight.y >=this.tLeft.y))
            return true;
        // Case 2: When left side of object is colliding on top of the right side of this 
        else if(obj.bLeft.x <=this.tRight.x && obj.bLeft.y >= this.tRight.y)
            return true;
        // Case 3: When right side of object is colliding under the left side of this
        else if(obj.tRight.x >= this.bLeft.x && obj.tRight.y <= this.bLeft.y)
            return true;
        // Case 4: When left side of object is colliding under the right side of this
        else if(obj.tLeft.x <= this.bRight.x && obj.tLeft.y <= this.bLeft.y)
            return true;

        return false;
    }
}
