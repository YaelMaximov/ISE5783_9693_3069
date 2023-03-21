package primitives;

/**
 * Represents a ray in 3D space with a starting point and direction.
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
     * Constructs a new ray with the given starting point and direction vector.
     * @param p0 the starting point of the ray
     * @param dir the direction vector of the ray
     * @throws IllegalAccessException if the direction vector is a zero vector
     */
    public Ray(Point p0, Vector dir) throws IllegalAccessException {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Returns the starting point of the ray.
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction vector of the ray.
     * @return the direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }
}
