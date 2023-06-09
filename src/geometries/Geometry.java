package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface defines methods that all geometric shapes should implement.
 */
public abstract class Geometry extends Intersectable {
    private Material material=new Material();

    protected Color emission = Color.BLACK;

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the normal vector of a point on the surface of the shape.
     *
     * @param p the point on the surface of the shape
     * @return the normal vector of the point on the surface of the shape
     */
    public abstract Vector getNormal(Point p) throws IllegalArgumentException;

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}