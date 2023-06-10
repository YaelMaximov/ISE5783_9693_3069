package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Tube class represents an infinite tube in 3D space.
 */
public class Tube extends RadialGeometry {

    protected Ray axisRay;

    /**
     * Constructs a tube with the given radius and axis ray.
     *
     * @param radius  the radius of the tube
     * @param axisRay the axis ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axis ray of the tube.
     *
     * @return the axis ray of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns the normal vector to the tube at a given point.
     *
     * @param p a point on the tube (unused)
     * @return the normal vector to the tube
     */
    @Override
    public Vector getNormal(Point p) throws IllegalAccessException {
        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        Vector P0_P = p.subtract(P0);
        double t = alignZero(v.dotProduct(P0_P));
        if (isZero(t)) {
            return P0_P.normalize();
        }
        Point o = P0.add(v.scale(t));
        if (p.equals(o)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }
        Vector n = p.subtract(o).normalize();
        return n;

    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}
