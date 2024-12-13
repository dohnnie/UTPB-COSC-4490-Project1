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
        if(this.tRight.x < obj.tLeft.x || obj.tRight.x < this.tLeft.x)
            return BoxSides.NONE;
        
        if(this.bRight.y < obj.tLeft.y || obj.bRight.y < this.tLeft.y) {
            return BoxSides.NONE;
        }

        double xDist = this.center.x - obj.center.x;
        double yDist = obj.center.y - this.center.y;
        double dist = Math.sqrt((xDist * xDist) + (yDist * yDist));
        double theta = Math.acos((xDist / yDist) / dist);
        int angle = (int)((theta * 8)/(Math.PI * 2));
        
        switch (angle) {
            case 0, 7 -> { 
                return BoxSides.LEFT;
            }
            case 1, 2 -> {
                return BoxSides.TOP;
            }
            case 3, 4 -> {
                return BoxSides.RIGHT;
            }
            case 5, 6 -> {
                return BoxSides.BOTTOM;
            }
            default -> {
                return BoxSides.NONE;
            }
        }
    }
}