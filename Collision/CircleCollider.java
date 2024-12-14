/*
 * UNUSED CLASS THERE IS NO FUNCTIONALITY IMPLEMENTED FOR THIS CLASS
 */

package Collision;

import GameObjects.StaticObject;

public class CircleCollider {
    int radius;
    int center;

    public CircleCollider(int x, int y) {
        center = x;
        radius = y;
    }

    public boolean collide(BoxCollider obj) {
        return true;
    }

    public boolean collide(StaticObject obj) {
        return true;
    }

    private double length() {
        return 0;
    }
}
