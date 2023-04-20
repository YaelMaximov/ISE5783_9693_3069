package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * The Plane class represents a plane in 3D space.
 */
public class Plane implements Geometry {

    private final Point p0;
    private final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     *
     * @param p1 a point on the plane
     * @param p2 a point on the plane
     * @param p3 a point on the plane
     */
    /*public Plane(Point p1, Point p2, Point p3) throws IllegalAccessException {//maybe we dont need to throw ex
        p0 = p1;
        Vector U=p1.subtract(p2);
        Vector V=p1.subtract(p3);
        Vector N=U.crossProduct(V);
        normal = N.normalize();
    }*/
    public Plane(Point p1, Point p2, Point p3) throws IllegalAccessException {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalAccessException("Two the points are the same point");
        Vector vec1 = p2.subtract(p1);
        Vector vec2 = p3.subtract(p2);
        try {
            Vector vector = vec1.crossProduct(vec2);
            p0 = p2;
            normal = vector.normalize();
        } catch (Exception ex) {
            throw new IllegalAccessException("The points are on same line");
        }

    }


    /**
     * Constructs a plane from a point on the plane and the plane's normal vector.
     *
     * @param p a point on the plane
     * @param v the normal vector of the plane
     * @throws IllegalAccessException if the vector is the zero vector
     */
    public Plane(Point p, Vector v) throws IllegalAccessException {
        p0 = p;
        normal = v.normalize();
    }

    /**
     * Returns the normal vector of the plane at a given point.
     *
     * @param p a point on the plane (unused)
     * @return the normal vector of the plane
     */
    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Returns the base point of the plane.
     *
     * @return the base point of the plane
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}