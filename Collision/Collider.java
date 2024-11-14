// Collision interface that should force every collision class to implement a collide method
package Collision;

public class Collider {
    int xPos, yPos;

    public Collider() {
        this.xPos = 0;
        this.yPos = 0;
    }

    public Collider(int x, int y) {
        this.xPos = x;
        this.yPos = y;
   }

   public boolean collide(BoxCollider obj) {
    return true;
   }

   public boolean collide(CircleCollider obj) {
    return true;
   }
}