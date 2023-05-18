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

    @Override
    public List<Point> findIntsersections(Ray ray) throws IllegalAccessException {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalAccessException e) {
            return null;// לעבור ולתקן לא נכון!!
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquares = tm == 0 ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquares);
        if (thSquared <= 0) {
            return null;
        }
        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) {
            return null;
        }
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) {
            return null;
        }
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getP0().add(ray.getDir().scale(t1)), ray.getP0().add(ray.getDir().scale(t2)));//לא נכון חייבת לחזור!!
        }
        if (t1 > 0) {
            return List.of(ray.getP0().add(ray.getDir().scale(t1)));//לא נכון לבדוק שובב
        } else
            return List.of(ray.getP0().add(ray.getDir().scale(t2)));// לא נכון לבדוק שוב
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(radius))));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquared);
        if (thSquared <= 0) return null;
        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) {
            return List.of(
                    new GeoPoint(this,(ray.getP0().add(ray.getDir().scale(t1))))
                    ,new GeoPoint(this,(ray.getP0().add(ray.getDir().scale(t2))))); //P1 , P2
        }
        if (t1 > 0)
            return List.of(new GeoPoint(this,(ray.getP0().add(ray.getDir().scale(t1)))));
        else
            return List.of(new GeoPoint(this,(ray.getP0().add(ray.getDir().scale(t2)))));
    }
}