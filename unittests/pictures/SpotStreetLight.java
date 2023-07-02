package pictures;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;

/**
 * The `SpotStreetLight` class represents a streetlight with a spotlight source.
 * It consists of a base, a lamp, and a spotlight on top.
 */
public class SpotStreetLight {
    private static final int SHININESS = 301;
    private static final Double3 KD3 = new Double3(0.3, 0.8, 0.4);
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);

    Cylinder Base;
    Cylinder lamp;
    PointLight light;
    Cylinder top;

    /**
     * Constructs a new `SpotStreetLight` object with the specified base point and normal vector.
     *
     * @param p      The base point of the streetlight.
     * @param normal The normal vector of the streetlight.
     * @throws IllegalArgumentException If the provided arguments are invalid.
     */
    public SpotStreetLight(Point p, Vector normal) throws IllegalArgumentException {
        double baseH = 30d;
        double lampH = 10d;

        // Create the base cylinder
        Ray ray = new Ray(p, normal);
        Base = new Cylinder(ray, 1d, baseH);
        Base = (Cylinder) Base.setEmission(new Color(0, 0, 0));
        Base = (Cylinder) Base.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        Point nextP = p.add(new Vector(0, baseH, 0));

        // Create the lamp cylinder
        ray = new Ray(nextP, normal);
        lamp = new Cylinder(ray, 5d, lampH);
        lamp = (Cylinder) lamp.setEmission(new Color(255, 207, 73));
        lamp = (Cylinder) lamp.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.5));

        nextP = nextP.add(new Vector(0, lampH / 2, 0));

        // Create the spotlight
        light = new SpotLight(new Color(800, 255, 255), nextP, new Vector(0, -1, 0))
                .setkL(0.0001).setkQ(0.0002);

        // Create the top cylinder
        ray = new Ray(nextP.add(new Vector(0, lampH / 2, 0)), normal);
        top = new Cylinder(ray, 1d, 2d);
        top = (Cylinder) top.setEmission(new Color(0, 0, 0));
        top = (Cylinder) top.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
    }

    /**
     * Returns the geometries of the `SpotStreetLight` object.
     *
     * @return The geometries representing the streetlight.
     */
    public Geometries getGeometries() {
        return new Geometries(Base, top, lamp);
    }

    /**
     * Returns the light source associated with the `SpotStreetLight` object.
     *
     * @return The point light source of the streetlight.
     */
    public PointLight getLight() {
        return light;
    }
}

