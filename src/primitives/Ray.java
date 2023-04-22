package primitives;

/**
 * A class representing a ray in 3D space, defined by a starting point and a direction.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    final Point p0;

    /**
     * The normalized direction vector of the ray.
     */
    final Vector dir;

    /**
     * Constructs a new Ray object with the specified starting point and direction vector.
     *
     * @param p0 the starting point of the ray
     * @param dir the direction vector of the ray
     * @throws IllegalAccessException if the direction vector is the zero vector
     */
    public Ray(Point p0, Vector dir) throws IllegalAccessException {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return the direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }
    public Point getPoint(double t) throws IllegalAccessException {
        Vector scl_p=dir.scale(t);
        Point P=p0.add(scl_p);
        return P;
    }
}
