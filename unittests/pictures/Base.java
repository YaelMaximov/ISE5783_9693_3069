package pictures;

import geometries.Geometries;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * The Base class represents a base made up of three cubes.
 */
public class Base {
    private Cube c1;
    private Cube c2;
    private Cube c3;
    final public double Height = 1.5d;

    /**
     * Constructs a Base object with the given parameters.
     *
     * @param p1 The starting point of the base.
     * @param zx The length of the base along the x-axis.
     * @param y  The height of the base along the y-axis.
     * @param k  The scale factor of the middle cube in the base.
     * @throws IllegalArgumentException if any of the input parameters are invalid.
     */
    public Base(Point p1, double zx, double y, double k) throws IllegalArgumentException {
        c1 = new Cube(p1, zx, Height);
        Point start = p1.add(new Vector((zx - k * zx) / 2, Height, (zx - k * zx) / 2));
        c2 = new Cube(start, zx * k, y - 2 * Height);
        start = p1.add(new Vector(0, y - Height, 0));
        c3 = new Cube(start, zx, Height);
    }

    /**
     * Returns the geometries of the base.
     *
     * @return The geometries of the base.
     */
    public Geometries getGeometries() {
        return new Geometries(c1.getGeometries(), c2.getGeometries(), c3.getGeometries());
    }

    /**
     * Sets the emission color of the base cubes.
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
     * Sets the material of the base cubes.
     *
     * @param mt The material to be set for the cubes.
     * @return The updated Base object.
     */
    public Base setBaseMaterial(Material mt) {
        c1 = c1.setCubeMaterial(mt);
        c2 = c2.setCubeMaterial(mt);
        c3 = c3.setCubeMaterial(mt);

        return this;
    }

    /**
     * Returns the normal vector of the top face of the first cube in the base at the given point.
     *
     * @param p The point on the top face of the first cube.
     * @return The normal vector of the top face at the given point.
     * @throws IllegalArgumentException if the point is not on the top face of the first cube.
     */
    public Vector getC1Normal(Point p) throws IllegalArgumentException {
        Vector normal = c1.getCubeTopNormal(p);
        return normal;
    }

    /**
     * Returns a list of points representing the corners of the top face of the first cube in the base.
     *
     * @return A list of points representing the corners of the top face.
     */
    public List<Point> points() {
        return List.of(c1.p2, c1.p3, c1.p4);
    }
}

