import java.util.Arraylist;

public class Collideable implements {

    private ArrayList<CollisionBody> body = new ArrayList<>();
    private ArrayList<CollisionBody> colliders = new ArrayList<>();

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

    public
}