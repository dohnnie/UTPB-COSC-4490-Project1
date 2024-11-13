package Collision;

import GameObjects.StaticObject;

public class CircleCollider extends Collider {
    int radius;
    int center;

    public CircleCollider(int x, int y) {
        super(x, y);
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
