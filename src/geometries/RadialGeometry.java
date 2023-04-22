package geometries;

/**
 * The RadialGeometry class represents a geometric object with a radius, such as a sphere or cylinder.
 */
public abstract class RadialGeometry implements Geometry {

    protected double radius;

    /**
     * Constructs a radial geometry object with the given radius.
     *
     * @param radius the radius of the object
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }


}