public abstract class CollisionBody {
    public abstract boolean collideLine(CollideLine l);
    public abstract boolean collideCircle(CollideCircle c);
    public abstract boolean collideRect(CollideRect r);
    public abstract boolean collideConvShape(CollideConvShape s);
    public abstract boolean collideConcShape(CollideConcShape s);
}