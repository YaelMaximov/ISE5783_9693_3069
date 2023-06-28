package pictures;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a cube in 3D space.
 */
public class Cube {
    Polygon base;
    Polygon top;
    Polygon front;
    Polygon back;
    Polygon rightSide;
    Polygon leftSide;
    Point p2;
    Point p3;
    Point p4;
    Point p5;
    Point p6;
    Point p7;
    Point p8;

    /**
     * Constructs a cube given one corner point and dimensions along the x and y axes.
     *
     * @param p1 The corner point of the cube.
     * @param zx The dimension along the x-axis.
     * @param y  The dimension along the y-axis.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Cube(Point p1, double zx, double y) throws IllegalAccessException {
        p2 = p1.add(new Vector(zx, 0, 0));
        p3 = p2.add(new Vector(0, 0, zx));
        p4 = p1.add(new Vector(0, 0, zx));
        p5 = p1.add(new Vector(0, y, 0));
        p6 = p2.add(new Vector(0, y, 0));
        p7 = p3.add(new Vector(0, y, 0));
        p8 = p4.add(new Vector(0, y, 0));

        top = new Polygon(p5, p6, p7, p8);
        base = new Polygon(p1, p2, p3, p4);
        front = new Polygon(p1, p2, p6, p5);
        back = new Polygon(p4, p3, p7, p8);
        rightSide = new Polygon(p2, p3, p7, p6);
        leftSide = new Polygon(p1, p4, p8, p5);
    }

    /**
     * Returns the geometries representing the cube.
     *
     * @return The geometries representing the cube.
     */
    public Geometries getGeometries() {
        return new Geometries(top, base, front, back, rightSide, leftSide);
    }

    /**
     * Sets the emission color of the cube.
     *
     * @param color The emission color to set.
     * @return The updated Cube object.
     */
    public Cube setCubeEmission(Color color) {
        top = (Polygon) top.setEmission(color);
        base = (Polygon) base.setEmission(color);
        front = (Polygon) front.setEmission(color);
        back = (Polygon) back.setEmission(color);
        rightSide = (Polygon) rightSide.setEmission(color);
        leftSide = (Polygon) leftSide.setEmission(color);
        return this;
    }

    /**
     * Sets the material of the cube.
     *
     * @param mt The material to set.
     * @return The updated Cube object.
     */
    public Cube setCubeMaterial(Material mt) {
        top = (Polygon) top.setMaterial(mt);
        base = (Polygon) base.setMaterial(mt);
        front = (Polygon) front.setMaterial(mt);
        back = (Polygon) back.setMaterial(mt);
        rightSide = (Polygon) rightSide.setMaterial(mt);
        leftSide = (Polygon) leftSide.setMaterial(mt);
        return this;
    }

    /**
     * Calculates and returns the normal vector of the top face of the cube at the given point.
     *
     * @param p The point on the top face.
     * @return The normal vector of the top face.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Vector getCubeTopNormal(Point p) throws IllegalAccessException {
        Vector normal = top.getNormal(p);
        if (normal.getY() < 0)
            return new Vector(normal.getX(), -1 * normal.getY(), normal.getZ());
        return normal;
    }
}
