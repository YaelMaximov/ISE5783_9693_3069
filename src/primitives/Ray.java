package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

/**
 * A class representing a ray in 3D space, defined by a starting point and a direction.
 */
public class Ray {
    private static final double DELTA = 0.1;
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
     * @throws IllegalArgumentException if the direction vector is the zero vector
     */
    public Ray(Point p0, Vector dir) throws IllegalArgumentException {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    public Ray(Point point, Vector lightDirection,Vector n) throws IllegalArgumentException {
        this.dir = lightDirection;
        // Calculate an epsilon vector to slightly move the point in the direction of the normal
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

        // Move the point slightly in the direction of the normal
        this.p0 = point.add(epsVector);


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

    public Point getPoint(double t) throws IllegalArgumentException {
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

    /**
     * Looking for the GeoPoint closest to the beginning of the fund of all the points received
     *
     * @param pointList List of Intersections of GeoPoint
     * @return The closest GeoPoint to the beginning of the ray
     */


    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointList) {
        if (pointList.isEmpty()) {
            return null;
        }
        GeoPoint minPoint = new GeoPoint(pointList.get(0).geometry, pointList.get(0).point);
        double min = p0.distance(pointList.get(0).point);
        double d;
        for (GeoPoint pl : pointList) {
            d = p0.distance(pl.point);
            if (d < min) {
                minPoint = pl;
                min = d;
            }
        }
        return minPoint;
    }


    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


}


