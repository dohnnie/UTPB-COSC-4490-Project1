package Collision;

import java.awt.Point;

public class BoxCollider extends Collider {
    public Point tLeft, tRight, bLeft, bRight, center;
    int width, height;
    public BoxCollider(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        //Since swing starts drawing from the top left of the image it'll be easier to make that the root point
        //and doing math to find stuff like the middle of the collider
        tLeft = new Point(this.xPos, this.yPos);
        tRight = new Point(this.xPos + width, this.yPos);
        bLeft = new Point(this.xPos, this.yPos + height);
        bRight = new Point(this.xPos + width, this.yPos + height);
        center = new Point(x + (width / 2), y + (height / 2));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateColliderPoints(int xPos, int yPos) {
        tLeft.x = xPos;
        tLeft.y = yPos;
        tRight.x = xPos + width; 
        tRight.y = yPos;
        bLeft.x = xPos; 
        bLeft.y = yPos + height;
        bRight.x = xPos + width; 
        bRight.y = yPos + height;
        center.x = xPos + (width / 2);
        center.y = yPos + (height / 2);
    }

    // General use case for collisions between a box, and a physics box
    public BoxSides collide(BoxCollider obj) {

        //System.out.println("This tLeft: " + tLeft.x + ", This tRight: " + tRight.x + ", This bLeft: " + bLeft.y + ", This bRight: " + bRight.y);
        //System.out.println("Obj tLeft: " + obj.tLeft.x + ", Obj tRight: " + obj.tRight.x + ", Obj bLeft: " + obj.bLeft.y + ", Obj bRight: " + obj.bRight.y);

        if(this.tRight.x < obj.tLeft.x || obj.tRight.x < this.tLeft.x)
            return BoxSides.NONE;
        
        if(this.bRight.y < obj.bLeft.y || obj.bRight.y < this.bLeft.y)
            return BoxSides.NONE;

        //Checks if this is to the left or right ob obj
        if(this.tRight.x > obj.tLeft.x || this.tLeft.x < obj.tRight.x) {
            //Checks if this is within the height of obj
            if(this.bRight.y > obj.bLeft.y && this.bLeft.y < obj.bRight.y) {
                //if this is to the right of obj
                if(this.tRight.x > obj.tLeft.x)
                    return BoxSides.RIGHT;

                return BoxSides.LEFT;
            }
        }

        //Checks if this is above or below obj
        if(this.bRight.y > obj.bLeft.y || this.bLeft.y < obj.bRight.y) {
            //Checks if this is within the width of obj
            if(this.tRight.x > obj.tLeft.x && this.tLeft.x < obj.tRight.x) {
                //if this is below obj
                if(this.bRight.y > obj.bLeft.y)
                    return BoxSides.BOTTOM;

                return BoxSides.TOP;
            }
        }

        return BoxSides.NONE;
    }
}