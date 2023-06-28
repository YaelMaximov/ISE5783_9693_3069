package pictures;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a clock composed of polygons and cylinders in 3D space.
 */
public class Clock {
    final private double Height = 1d;
    Polygon frame;
    Cylinder circle1;
    Cylinder circle2;
    Cylinder circle3;
    Polygon longHand;
    Polygon shortHand;

    /**
     * Constructs a clock based on the provided parameters.
     *
     * @param p1 The corner point of the clock frame.
     * @param l  The length of the clock frame.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Clock(Point p1, double l) throws IllegalAccessException {
        Point p2 = p1.add(new Vector(l, 0, 0));
        Point p3 = p2.add(new Vector(0, l, 0));
        Point p4 = p1.add(new Vector(0, l, 0));
        frame = new Polygon(p1, p2, p3, p4);

        Point center1 = p1.add(new Vector(l / 2, l / 2, 0));
        double radius1 = (l / 2) - l * 0.08;
        Ray ray1 = new Ray(center1, frame.getNormal(center1));
        circle1 = new Cylinder(ray1, radius1, Height);

        double radius2 = radius1 - radius1 * 0.2;
        Point center2 = center1.add(new Vector(0, 0, 0.5));
        Ray ray2 = new Ray(center2, frame.getNormal(center2));
        circle2 = new Cylinder(ray2, radius2, Height);

        double radius3 = radius2 - radius2 * 0.08;
        Point center3 = center2.add(new Vector(0, 0, 0.5));
        Ray ray3 = new Ray(center3, frame.getNormal(center3));
        circle3 = new Cylinder(ray3, radius3, Height);

        Point h1 = center3.add(new Vector(-0.4, 0, 2));
        Point h2 = center3.add(new Vector(0.4, 0, 2));
        Point h3 = h2.add(new Vector(0, radius3 * 0.9, 2));
        Point h4 = h1.add(new Vector(0, radius3 * 0.9, 2));
        longHand = new Polygon(h1, h2, h3, h4);

        Point hs1 = center3.add(new Vector(0, -0.4, 2));
        Point hs2 = center3.add(new Vector(0, 0.4, 2));
        Point hs3 = hs2.add(new Vector(radius3 * 0.6, 0, 2));
        Point hs4 = hs1.add(new Vector(radius3 * 0.6, 0, 2));
        shortHand = new Polygon(hs1, hs2, hs3, hs4);
    }

    /**
     * Returns the geometries representing the clock.
     *
     * @return The geometries representing the clock.
     */
    public Geometries getGeometries() {
        return new Geometries(frame, circle1, circle2, circle3, longHand, shortHand);
    }

    /**
     * Sets the emission color of the clock components.
     *
     * @param clock  The emission color of the clock cylinders.
     * @param square The emission color of the clock frame.
     * @return The Clock object with updated emission colors.
     */
    public Clock setClockEmission(Color clock, Color square) {
        circle1 = (Cylinder) circle1.setEmission(clock);
        circle2 = (Cylinder) circle2.setEmission(new Color(128, 128, 128));
        circle3 = (Cylinder) circle3.setEmission(clock);
        frame = (Polygon) frame.setEmission(square);
        longHand = (Polygon) longHand.setEmission(new Color(128, 128, 128));
        shortHand = (Polygon) shortHand.setEmission(new Color(128, 128, 128));

        return this;
    }

    /**
     * Sets the material of the clock components.
     *
     * @param mt The material to set.
     * @return The Clock object with updated material.
     */
    public Clock setClockMaterial(Material mt) {
        circle1 = (Cylinder) circle1.setMaterial(mt);
        frame = (Polygon) frame.setMaterial(mt);
        return this;
    }
}
