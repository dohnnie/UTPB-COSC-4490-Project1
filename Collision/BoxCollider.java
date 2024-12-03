package Collision;

import GameObjects.*;
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

    public void updatePoints(int xPos, int yPos) {
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
    @Override
    public boolean collide(Platform platform) {
        //Currently all if statements are broken, I have to perform a check for each corner rathaer than just 2 in order to get the full range
        // Case 1: When right side of object is colliding on top of the left side of this
        if((this.bRight.x >= platform.box.tLeft.x && this.bRight.y >= platform.box.tLeft.y) && (this.bLeft.x <=platform.box.tRight.x && this.bLeft.y >= platform.box.tRight.y))    
            return true;
        // Case 2: When left side of object is colliding on top of the right side of this 
        //else if(this.bLeft.x <=platform.box.tRight.x && this.bLeft.y >= platform.box.tRight.y)
        //    return true;
        // Case 3: When right side of object is colliding under the left side of this
        //else if(this.tRight.x <= platform.box.bLeft.x && this.tRight.y >= platform.box.bLeft.y)
        //    return true;
        // Case 4: When left side of object is colliding under the right side of this
        //else if(this.tLeft.x >= platform.box.bRight.x && this.tLeft.y >= platform.box.bLeft.y)
        //    return true;

        return false;
    }

    public boolean collideTop(Player player) {
        if(player.box.bLeft.x >= this.tLeft.x && player.box.bLeft.y >= this.tLeft.y && 
            player.box.bRight.x <= this.tRight.x && player.box.bRight.y >= this.tRight.y)
            return true;

        return false;
    }
}
