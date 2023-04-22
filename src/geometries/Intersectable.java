package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable interface represents a geometric object that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Returns a list of points where the specified ray intersects this object.
     *
     * @param ray the ray to intersect with this object
     * @return a list of points where the ray intersects this object, or an empty list if there are no intersections
     */
    List<Point> findIntsersections(Ray ray) throws IllegalAccessException;

}
