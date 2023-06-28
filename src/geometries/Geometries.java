package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of geometries.
 * Extends the Intersectable class and overrides the findGeoIntersectionsHelper method.
 * The Geometries class allows adding multiple geometries and finding intersections between a ray and all the geometries in the collection.
 */
public class Geometries extends Intersectable {
    private List<Intersectable> intersectables;

    /**
     * Constructs an empty Geometries collection.
     * Initializes the intersectables list as an empty LinkedList.
     */
    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    /**
     * Constructs a Geometries collection with the specified geometries.
     * Initializes the intersectables list with the given geometries.
     *
     * @param geometry The geometries to add to the collection.
     */
    public Geometries(Intersectable... geometry) {
        intersectables = List.of(geometry);
    }

    /**
     * Adds the specified geometries to the collection.
     *
     * @param geometries The geometries to add to the collection.
     */
    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));
    }

    /**
     * Finds the intersection points between a ray and all the geometries in the collection.
     * Overrides the findGeoIntersectionsHelper method from the Intersectable class.
     *
     * @param ray         The ray towards the composite of geometries.
     * @param maxDistance The maximum distance to check for intersections.
     * @return An immutable list of intersection points as GeoPoint objects.
     *         Returns null if no intersection points were found.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalAccessException {
        List<GeoPoint> result = null;   // Intersection points

        // For each geometry in the intersectables collection, check intersection points
        for (var item : intersectables) {
            // Get intersection points for each specific item (item can be either a geometry or a nested composite of geometries)
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
     * Adds the specified triangles to the collection.
     *
     * @param triangles The triangles to add to the collection.
     */
    public void add(List<Triangle> triangles) {
        intersectables.addAll(triangles);
    }
}
