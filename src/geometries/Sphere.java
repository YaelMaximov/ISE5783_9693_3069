package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Sphere class represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a sphere with the given radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the normal vector to the sphere at a given point.
     *
     * @param p a point on the sphere (unused)
     * @return the normal vector to the sphere
     */
    @Override
    public Vector getNormal(Point p) throws IllegalAccessException {
        return p.subtract(this.center).normalize();
    }


    //    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
//        Point p0 = ray.getP0();
//        Vector v = ray.getDir();
//        Vector u;
//        try {
//            u = center.subtract(p0);
//        } catch (IllegalAccessException e) {
//            return null;// לעבור ולתקן לא נכון!!
//        }
//        double tm = alignZero(v.dotProduct(u));
//        double dSquares = tm == 0 ? u.lengthSquared() : u.lengthSquared() - tm * tm;
//        double thSquared = alignZero(radius * radius - dSquares);
//        if (thSquared <= 0) {
//            return null;
//        }
//        double th = alignZero(Math.sqrt(thSquared));
//        if (th == 0) {
//            return null;
//        }
//        double t1 = alignZero(tm - th);
//        double t2 = alignZero(tm + th);
//        if (t1 <= 0 && t2 <= 0) {
//            return null;
//        }
//        if (t1 > 0 && t2 > 0) {
//            return List.of(ray.getP0().add(ray.getDir().scale(t1)), ray.getP0().add(ray.getDir().scale(t2)));//לא נכון חייבת לחזור!!
//        }
//        if (t1 > 0) {
//            return List.of(ray.getP0().add(ray.getDir().scale(t1)));//לא נכון לבדוק שובב
//        } else
//            return List.of(ray.getP0().add(ray.getDir().scale(t2)));// לא נכון לבדוק שוב
//    }
    /**
     * find intersection points between ray and sphere
     * @param ray ray towards the object
     * @return immutable list containing 0/1/2 intersection points as {@link GeoPoint}s objects
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) throws IllegalAccessException {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        // ray starts at center point of sphere
        // return point on surface in direction of ray
        if (P0.equals(center)) {
            return List.of( new GeoPoint(this,ray.getPoint(radius)));
        }

        // vector from ray origin to center point
        Vector U = center.subtract(P0);

        // tm = U's projection on ray's vector
        double tm = alignZero(v.dotProduct(U));
        // d between u and ray (at center point)
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        //distance from center to ray is larger than the radius
        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        // th = distance from ray intersection point to point
        // reached by scaling ray vector by tm
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        // t1 = size of distance from first intersection point to
        //  point reached by ray scaled by tm
        double t1 = alignZero(tm - th);
        // t2 = size of distance from point reached by ray scaled by tm
        // to second intersection point
        double t2 = alignZero(tm + th);

        // check that distance from ray origin to intersection points
        // is smaller than max distance parameter set by user
        boolean distanceT1 = alignZero(t1-maxDistance) <= 0;
        boolean distanceT2 = alignZero(t2-maxDistance) <= 0;

        // ray constructed outside sphere
        // two intersection points
        if (t1 > 0 && t2 > 0 && distanceT1 && distanceT2) {
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this,P1), new GeoPoint (this,P2));
        }
        // ray constructed inside sphere and intersect in back direction
        if (t1 > 0 && distanceT1) {
            Point P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(this,P1));
        }
        // ray constructed inside sphere and intersect in forward direction
        if (t2 > 0 && distanceT2) {
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint (this,P2));
        }
        // no intersection points found - assurance return
        // code should not be reaching this point
        return null;

    }
}