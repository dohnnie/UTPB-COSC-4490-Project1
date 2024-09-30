public interface Transformable {

    private ArrayList<Point> verts = new ArrayList<>();

    public Point getCenter();

    public void Rotate(double radians);

    public void Move(int xPixels, int yPixels);

    public void Scale(double factor);
}