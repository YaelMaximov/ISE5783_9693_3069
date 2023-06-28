package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents a geometric object that can be intersected by a ray.
 */
public abstract class Intersectable {
    protected boolean bvhIsOn = true;
    public BoundingBox box;

    /**
     * Represents a bounding box for the intersectable object.
     */
    public class BoundingBox {
        public Point minimums;
        public Point maximums;

        /**
         * Constructs a bounding box with the specified minimum and maximum points.
         *
         * @param min The minimum point of the bounding box.
         * @param max The maximum point of the bounding box.
         */
        public BoundingBox(Point min, Point max) {
            minimums = min;
            maximums = max;
        }
    }

    /**
     * Checks if a ray intersects with the bounding box of the object.
     *
     * @param ray The ray to check for intersection.
     * @return true if the ray intersects with the bounding box, false otherwise.
     */
    public boolean isIntersectingBoundingBox(Ray ray) {
        if (!bvhIsOn || box == null)
            return true;

        Vector dir = ray.getDir();
        Point p0 = ray.getP0();
        double tMin = (box.minimums.getX() - p0.getX()) / dir.getX();
        double tMax = (box.maximums.getX() - p0.getX()) / dir.getX();

        if (tMin > tMax) {
            double temp = tMin;
            tMin = tMax;
            tMax = temp;
        }

        double tyMin = (box.minimums.getY() - p0.getY()) / dir.getY();
        double tyMax = (box.maximums.getY() - p0.getY()) / dir.getY();

        if (tyMin > tyMax) {
            double temp = tyMin;
            tyMin = tyMax;
            tyMax = temp;
        }

        if ((tMin > tyMax) || (tyMin > tMax))
            return false;

        if (tyMin > tMin)
            tMin = tyMin;

        if (tyMax < tMax)
            tMax = tyMax;

        double tzMin = (box.minimums.getZ() - p0.getZ()) / dir.getZ();
        double tzMax = (box.maximums.getZ() - p0.getZ()) / dir.getZ();

        if (tzMin > tzMax) {
            double temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
        }

        if ((tMin > tzMax) || (tzMin > tMax))
            return false;

        if (tzMin > tMin)
            tMin = tzMin;

        if (tzMax < tMax)
            tMax = tzMax;

        return true;
    }

    /**
     * Represents a point of intersection between a ray and a geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a GeoPoint object with the specified geometry and intersection point.
         *
         * @param geometry The geometry object.
         * @param point    The intersection point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersection points between a ray and the object.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points as Point objects.
     *         Returns null if no intersection points were found.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public List<Point> findIntersections(Ray ray) throws IllegalAccessException {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points between a ray and the object.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points as GeoPoint objects.
     *         Returns null if no intersection points were found.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalAccessException {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the intersection points between a ray and the object up to a maximum distance.
     *
     * @param ray         The ray to intersect with the object.
     * @param maxDistance The maximum distance to check for intersections.
     * @return A list of intersection points as GeoPoint objects.
     *         Returns null if no intersection points were found.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) throws IllegalAccessException {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper method for finding the intersection points between a ray and the object.
     * Must be implemented in the subclasses.
     *
     * @param ray         The ray to intersect with the object.
     * @param maxDistance The maximum distance to check for intersections.
     * @return A list of intersection points as GeoPoint objects.
     *         Returns null if no intersection points were found.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalAccessException;
}
