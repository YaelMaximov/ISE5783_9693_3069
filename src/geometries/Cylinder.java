package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * A class representing a cylinder in 3D space, defined by a radius, an axis ray and a height.
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    private final double height;

    /**
     * Constructs a new Cylinder object with the specified radius, axis ray and height.
     *
     * @param radius the radius of the cylinder
     * @param axisRay the axis ray of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * Returns the normal vector to the cylinder at the specified point.
     *
     * @param p the point to get the normal vector at
     * @return null
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }
}
