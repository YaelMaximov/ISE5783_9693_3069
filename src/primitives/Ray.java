package primitives;

import geometries.Intersectable;

import java.util.List;
import java.util.Objects;

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
     * @param p0  the starting point of the ray
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
        Vector scl_p = dir.scale(t);
        Point P = p0.add(scl_p);
        return P;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    public Point findClosestPoint(List<Point> pointList) {
        if (pointList.isEmpty()) {
            return null;
        }
        Point minPoint = new Point(pointList.get(0).xyz);
        double min = p0.distance(pointList.get(0));
        double d;
        for (Point pl : pointList) {
            d = p0.distance(pl);
            if (d < min) {
                minPoint = pl;
                min = d;
            }
        }
        return minPoint;
    }

    /**
     * Looking for the GeoPoint closest to the beginning of the fund of all the points received
     *
     * @param pointList List of Intersections of GeoPoint
     * @return The closest GeoPoint to the beginning of the ray
     */
    public Intersectable.GeoPoint findClosestGeoPoint(List<Intersectable.GeoPoint> pointList) {
        if (pointList.isEmpty()) {
            return null;
        }
        Intersectable.GeoPoint minPoint = new Intersectable.GeoPoint(pointList.get(0).geometry, pointList.get(0).point);
        double min = p0.distance(pointList.get(0).point);
        double d;
        for (Intersectable.GeoPoint pl : pointList) {
            d = p0.distance(pl.point);
            if (d < min) {
                minPoint = pl;
                min = d;
            }
        }
        return minPoint;


    }
}