package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Tube class represents an infinite tube in 3D space.
 */
public class Tube extends RadialGeometry {

    protected Ray axisRay;

    /**
     * Constructs a tube with the given radius and axis ray.
     *
     * @param radius   the radius of the tube
     * @param axisRay  the axis ray of the tube
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
    public Vector getNormal(Point p) {
        return null;
    }

}
