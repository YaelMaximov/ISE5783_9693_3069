package pictures;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import lighting.PointLight;
import primitives.*;
/**
 * The `StreetLamp` class represents a streetlamp with a point light source.
 * It consists of a base, a lamp sphere, and a cylinder on top.
 */
public class StreetLamp {
    Cylinder Base;
    Sphere lamp;
    PointLight light;
    Cylinder top;

    /**
     * Constructs a new `StreetLamp` object with the specified base point and normal vector.
     *
     * @param p      The base point of the streetlamp.
     * @param normal The normal vector of the streetlamp.
     * @throws IllegalArgumentException If the provided arguments are invalid.
     */
    public StreetLamp(Point p, Vector normal) throws IllegalArgumentException {
        double sphereR = 6d;
        double cylinderH = 30d;

        // Create the base cylinder
        Ray ray = new Ray(p, normal);
        Base = new Cylinder(ray, 1d, cylinderH);
        Base = (Cylinder) Base.setEmission(new Color(0, 0, 0));
        Base = (Cylinder) Base.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        Point nextP = p.add(new Vector(0, sphereR + cylinderH, 0));

        // Create the lamp sphere
        lamp = new Sphere(sphereR, nextP);
        lamp = (Sphere) lamp.setEmission(new Color(255, 207, 73));
        lamp = (Sphere) lamp.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.5));

        // Create the point light
        light = new PointLight(new Color(800, 255, 255), nextP)
                .setkL(0.0001).setkQ(0.0002);

        // Create the top cylinder
        ray = new Ray(nextP.add(new Vector(0, sphereR, 0)), normal);
        top = new Cylinder(ray, 1d, 2d);
        top = (Cylinder) top.setEmission(new Color(0, 0, 0));
        top = (Cylinder) top.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
    }

    /**
     * Returns the geometries of the `StreetLamp` object.
     *
     * @return The geometries representing the streetlamp.
     */
    public Geometries getGeometries() {
        return new Geometries(Base, top, lamp);
    }

    /**
     * Returns the light source associated with the `StreetLamp` object.
     *
     * @return The point light source of the streetlamp.
     */
    public PointLight getLight() {
        return light;
    }
}
