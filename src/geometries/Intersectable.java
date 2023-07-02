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
    public AABB box;

    /**
     * Sets whether or not the bounding volume hierarchy (BVH) optimization is enabled for this intersectable object.
     *
     * @param bvhIsOn true to enable BVH, false otherwise
     */
    public void setBvhIsOn(boolean bvhIsOn) {
        this.bvhIsOn = bvhIsOn;
    }

    /**
     * The AABB class represents an axis-aligned bounding box.
     */
    public class AABB {
        public Point minimums;
        public Point maximums;

        /**
         * Constructs an AABB with the specified minimum and maximum points.
         *
         * @param min The minimum point of the AABB.
         * @param max The maximum point of the AABB.
         */
        public AABB(Point min, Point max) {
            minimums = min;
            maximums = max;
        }
    }

    /**
     * Checks if the ray intersects the bounding box of this object.
     *
     * @param ray The ray to check for intersection with the bounding box.
     * @return true if the ray intersects the bounding box, false otherwise.
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
     * The GeoPoint class represents a point of intersection between a ray and a geometry object.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and intersection point.
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
     * Finds the intersections between a ray and this object.
     *
     * @param ray The ray to intersect with this object.
     * @return A list of intersection points, or null if there are no intersections.
     * @throws IllegalArgumentException If the ray is null.
     */
    public List<Point> findIntersections(Ray ray) throws IllegalArgumentException {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points between a ray and this object.
     *
     * @param ray The ray to intersect with this object.
     * @return A list of GeoPoint objects representing the intersection points, or null if there are no intersections.
     * @throws IllegalArgumentException If the ray is null.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException {
        if (bvhIsOn && !isIntersectingBoundingBox(ray))
            return null;
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the intersection points between a ray and this object, up to a specified maximum distance.
     *
     * @param ray         The ray to intersect with this object.
     * @param maxDistance The maximum distance to consider for intersections.
     * @return A list of GeoPoint objects representing the intersection points, or null if there are no intersections.
     * @throws IllegalArgumentException If the ray is null.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) throws IllegalArgumentException {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper method to be implemented by subclasses.
     * Finds the intersection points between a ray and this object, up to a specified maximum distance.
     *
     * @param ray         The ray to intersect with this object.
     * @param maxDistance The maximum distance to consider for intersections.
     * @return A list of GeoPoint objects representing the intersection points, or null if there are no intersections.
     * @throws IllegalArgumentException If the ray is null.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalArgumentException;

    /**
     * Creates a bounding box for this object.
     * The bounding box is an axis-aligned bounding box (AABB) that encloses the object.
     *
     * @throws IllegalArgumentException If the object cannot have a bounding box.
     */
    protected abstract void createBoundingBox() throws IllegalArgumentException;
}
