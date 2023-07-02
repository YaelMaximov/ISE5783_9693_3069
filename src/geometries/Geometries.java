package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * The Geometries class represents a collection of intersectable geometries.
 * It provides methods for adding geometries to the collection and finding intersections with a ray.
 */
public class Geometries extends Intersectable {
    protected List<Intersectable> intersectables;

    /**
     * Constructs an empty Geometries object with an empty list of intersectables.
     */
    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    /**
     * Constructs a Geometries object with the specified intersectable geometries.
     *
     * @param geometry The intersectable geometries to add to the collection.
     */
    public Geometries(Intersectable... geometry) {
        if (bvhIsOn)
            createBoundingBox();
        intersectables = new LinkedList<>();
        Collections.addAll(intersectables, geometry);
    }

    /**
     * Adds the specified intersectable geometries to the collection.
     *
     * @param geometries The intersectable geometries to add to the collection.
     */
    public void add(Intersectable... geometries) {
        if (bvhIsOn)
            createBoundingBox();
        Collections.addAll(intersectables, geometries);
    }

    /**
     * Finds the intersections between a ray and all geometries in the collection.
     *
     * @param ray         The ray to find intersections with.
     * @param maxDistance The maximum distance to consider for intersections.
     * @return An immutable list of intersection points as GeoPoint objects.
     * @throws IllegalArgumentException If the ray is null.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalArgumentException {
        List<GeoPoint> result = null;   // intersection points

        // For each geometry in the intersectable collection, check intersection points
        for (var item : intersectables) {
            // Get intersection points for each specific item (item can be either geometry or a nested composite of geometries)
            List<GeoPoint> itemList = item.findGeoIntersections(ray, maxDistance);

            // Points were found, add them to the composite's total intersection points list
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        // Return the list of points, or null if no intersection points were found
        return result;
    }

    /**
     * Creates a bounding box for the collection of geometries.
     * The bounding box is an axis-aligned bounding box (AABB) that encloses all geometries in the collection.
     */
    @Override
    public void createBoundingBox() {
        if (intersectables == null)
            return;
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : intersectables) {
            if (geo.box != null) {
                minX = Math.min(minX, geo.box.minimums.getX());
                minY = Math.min(minY, geo.box.minimums.getY());
                minZ = Math.min(minZ, geo.box.minimums.getZ());
                maxX = Math.max(maxX, geo.box.maximums.getX());
                maxY = Math.max(maxY, geo.box.maximums.getY());
                maxZ = Math.max(maxZ, geo.box.maximums.getZ());
            }
        }
        box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }
}
