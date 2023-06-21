package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a plane in 3D space.
 */
public class Plane extends Geometry {

    private final Point p0;
    private final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     *
     * @param p1 a point on the plane
     * @param p2 a point on the plane
     * @param p3 a point on the plane
     */

    public Plane(Point p1, Point p2, Point p3) throws IllegalAccessException {
        // check that all three points are different

        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("points must be different");

        p0 = p1;

        // generate two vectors from the three points
        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector N;

        // attempt to get cross product vector of the above vectors
        // if exception is thrown all three points are
        // on same line and cannot represent a plane
        try {
            N = U.crossProduct(V);
        } catch (Exception e) {
            throw new IllegalArgumentException("The three points are on same line, can not represent a Plane");
        }
        // set plane's normal vector to normalized result of cross product vector
        normal = N.normalize();
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

    /**
     * find intersection between ray and plane
     * @param ray ray towards the plane
     * @return  immutable list of one intersection point as  {@link GeoPoint} object
     */

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) throws IllegalAccessException {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // ray cannot start at plane's origin point
        if(p0.equals(P0))
            return null;

        // ray points -> P = p0 + t*v_ (v_ = direction vector)
        // points on plane  if normal vector dot product with vector from
        // origin point to proposed point == 0
        // glossary:  (n,v) = dot product between vectors n,v
        // isolating t ,( scaling factor for ray's direction vector ) ->
        // t = (normal vector, vector from origin to point)/ (normal vector, ray vector)
        // if t is positive ray intersects plane
        double nv = n.dotProduct(v);

        // ray direction cannot be parallel to plane orientation
        if (isZero(nv)){
            return null;
        }

        // vector from origin to point
        Vector Q_P0 = p0.subtract(P0);

        double nQMinusP0 = alignZero(n.dotProduct(Q_P0));

        //t should not be equal to 0
        if( isZero(nQMinusP0)){
            return null;
        }
        // scaling factor for ray , if value is positive
        // ray intersects plane
        double t = alignZero(nQMinusP0 / nv);
        if (t > 0 && alignZero(t-maxDistance) <= 0){
            //return immutable List
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        // no intersection point  - ray and plane in opposite  direction
        return null;
    }
//@Override
//protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
//    Point p0 = ray.getP0();
//    ray.getDir().normalize();
//    double nv = this.normal.dotProduct(ray.getDir());
//    if (isZero(nv))//there is not any intersection
//        return null;
//    double t = normal.scale(-1).dotProduct(p0.subtract(p0).scale(1 / nv));
//    if (isZero(t)) {//The  first point of the ray in the plan(0 point)
//        return null;
//    }
//    if (t < 0) {//The first point of the ray in the plain(o point)
//        return null;
//    } else {
//        ArrayList<GeoPoint> arrayList = new ArrayList<GeoPoint>();
//        Point point = p0.add(ray.getDir().scale(t));
//        arrayList.add(new GeoPoint(this, point));
//        return arrayList;
//    }
}
