import java.util.Arraylist;

public class Collideable implements Runnable{

    private ArrayList<CollisionBody> body = new ArrayList<>();
    private ArrayList<Collideable> colliders = new ArrayList<>();

    public Collideable() {

    }

    public synchronized ArrayList<CollisionBody> getList() {
        return body;
    }
    public synchronized void addBody(CollisionBody c) {
        colliders.add(c);
    }

    public synchronized void addBody(Collideable c) {
        for (CollisionBody b : c.getList()) {
            this.addBody(b);
        }
    }

    public synchronized void dropBody(CollisionBody c) {
        colliders.remove(c);
    }

    public boolean collide() {
        for (Collideable c : colliders) {
            for (CollisionBody b1 : body) {
                for (CollisionBody b2: c.body) {
                    if (b1.collide(b2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}