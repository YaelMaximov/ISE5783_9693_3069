package pictures;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * Represents a base composed of multiple cubes in 3D space.
 */
public class Base {
    Cube c1;
    Cube c2;
    Cube c3;
    final public double Height = 1.5d;

    /**
     * Constructs a base using three cubes based on the provided parameters.
     *
     * @param p1 The corner point of the first cube.
     * @param zx The dimension along the x-axis of the first cube.
     * @param y  The dimension along the y-axis of the base.
     * @param k  The scaling factor for the second cube along the x-axis.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Base(Point p1, double zx, double y, double k) throws IllegalAccessException {
        c1 = new Cube(p1, zx, Height);
        Point start = p1.add(new Vector((zx - k * zx) / 2, Height, (zx - k * zx) / 2));
        c2 = new Cube(start, zx * k, y - 2 * Height);
        start = p1.add(new Vector(0, y - Height, 0));
        c3 = new Cube(start, zx, Height);
    }

    /**
     * Calculates and returns the normal vector of the top face of the first cube at the given point.
     *
     * @param p The point on the top face of the first cube.
     * @return The normal vector of the top face.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Vector getC1Normal(Point p) throws IllegalAccessException {
        return c1.getCubeTopNormal(p);
    }

    /**
     * Returns a list of points representing the corners of the first cube.
     *
     * @return The list of points representing the corners of the first cube.
     */
    public List<Point> points() {
        return List.of(c1.p2, c1.p3, c1.p4);
    }

    /**
     * Returns the geometries representing the base.
     *
     * @return The geometries representing the base.
     */
    public Geometries getGeometries() {
        return new Geometries(c1.getGeometries(), c2.getGeometries(), c3.getGeometries());
    }

    /**
     * Sets the emission color of the cubes in the base.
     *
     * @param color1 The emission color for the first and third cubes.
     * @param color2 The emission color for the second cube.
     * @return The updated Base object.
     */
    public Base setBaseEmission(Color color1, Color color2) {
        c1 = c1.setCubeEmission(color1);
        c2 = c2.setCubeEmission(color2);
        c3 = c3.setCubeEmission(color1);
        return this;
    }

    /**
     * Sets the material of the cubes in the base.
     *
     * @param mt The material to set.
     * @return The updated Base object.
     */
    public Base setBaseMaterial(Material mt) {
        c1 = c1.setCubeMaterial(mt);
        c2 = c2.setCubeMaterial(mt);
        c3 = c3.setCubeMaterial(mt);
        return this;
    }
}


