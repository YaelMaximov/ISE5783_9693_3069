package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface defines methods that all geometric shapes should implement.
 */
public interface Geometry {

    /**
     * Returns the normal vector of a point on the surface of the shape.
     *
     * @param p the point on the surface of the shape
     * @return the normal vector of the point on the surface of the shape
     */
    public Vector getNormal(Point p) throws IllegalAccessException;

}