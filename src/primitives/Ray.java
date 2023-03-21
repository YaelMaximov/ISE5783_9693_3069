package primitives;

public class Ray {
    final Point p0;
    final Vector dir;

    public Ray(Point p0, Vector dir) throws IllegalAccessException {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}
