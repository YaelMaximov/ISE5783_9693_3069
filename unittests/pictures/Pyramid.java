package pictures;

import geometries.Geometries;
import geometries.Polygon;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a pyramid composed of triangles and a polygon base in 3D space.
 */
public class Pyramid {
    Triangle t1;
    Triangle t2;
    Triangle t3;
    Triangle t4;
    Polygon base;

    /**
     * Constructs a pyramid based on the provided parameters.
     *
     * @param p1 The base corner point of the pyramid.
     * @param zx The size of the pyramid in the x and z directions.
     * @param y  The height of the pyramid in the y direction.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Pyramid(Point p1, double zx, double y) throws IllegalAccessException {
        Point p2 = p1.add(new Vector(zx, 0, 0));
        Point p3 = p2.add(new Vector(0, 0, zx));
        Point p4 = p1.add(new Vector(0, 0, zx));
        Point p5 = p1.add(new Vector(zx / 2, y, zx / 2));
        base = new Polygon(p1, p2, p3, p4);
        t1 = new Triangle(p1, p2, p5);
        t2 = new Triangle(p2, p3, p5);
        t3 = new Triangle(p3, p4, p5);
        t4 = new Triangle(p4, p1, p5);
    }

    /**
     * Returns the geometries representing the pyramid.
     *
     * @return The geometries representing the pyramid.
     */
    public Geometries getGeometries() {
        return new Geometries(base, t1, t2, t3, t4);
    }

    /**
     * Sets the emission color of the pyramid components.
     *
     * @param color The emission color to set.
     * @return The Pyramid object with updated emission colors.
     */
    public Pyramid setPyramidEmission(Color color) {
        base = (Polygon) base.setEmission(color);
        t1 = (Triangle) t1.setEmission(color);
        t2 = (Triangle) t2.setEmission(color);
        t3 = (Triangle) t3.setEmission(color);
        t4 = (Triangle) t4.setEmission(color);
        return this;
    }

    /**
     * Sets the material of the pyramid components.
     *
     * @param mt The material to set.
     * @return The Pyramid object with updated material.
     */
    public Pyramid setPyramidMaterial(Material mt) {
        base = (Polygon) base.setMaterial(mt);
        t1 = (Triangle) t1.setMaterial(mt);
        t2 = (Triangle) t2.setMaterial(mt);
        t3 = (Triangle) t3.setMaterial(mt);
        t4 = (Triangle) t4.setMaterial(mt);
        return this;
    }
}
