package pictures;

import geometries.Cylinder;
import geometries.Geometries;
import primitives.*;

import java.util.List;

public class CylinderBase extends Base {
    Cylinder c1;
    Cylinder c2;
    Cylinder c3;
    Cylinder c4;

    Cylinder c121;
    Cylinder c231;
    Cylinder c341;
    Cylinder c411;

    Cylinder c122;
    Cylinder c232;
    Cylinder c342;
    Cylinder c412;

    /**
     * Constructs a CylinderBase object with the given parameters.
     *
     * @param p1   The center point of the base.
     * @param zx   The X-axis size of the base.
     * @param y    The height of the base.
     * @param k    The height scale factor.
     * @throws IllegalAccessException if an illegal access occurs.
     */
    public CylinderBase(Point p1, double zx, double y, double k) throws IllegalAccessException {
        super(p1, zx, y, k);
        double move = (zx - k * zx) / 4;
        Point center1 = p1.add(new Vector(move, Height, move));
        Vector normal = getC1Normal(center1);
        Ray ray1 = new Ray(center1, normal);
        double radius = move * 0.7;
        double height = y - 2 * Height;
        c1 = new Cylinder(ray1, radius, height);

        List<Point> points = points();
        Point center2 = points.get(0).add(new Vector(-move, Height, move));
        Ray ray2 = new Ray(center2, normal);
        c2 = new Cylinder(ray2, radius, height);

        Point center3 = points.get(1).add(new Vector(-move, Height, -move));
        Ray ray3 = new Ray(center3, normal);
        c3 = new Cylinder(ray3, radius, height);

        Point center4 = points.get(2).add(new Vector(move, Height, -move));
        Ray ray4 = new Ray(center4, normal);
        c4 = new Cylinder(ray4, radius, height);

        double d = (center1.distance(center2)) / 3;
        c121 = new Cylinder(new Ray(center1.add(new Vector(d * 1, 0, 0)), normal), radius, height);
        c122 = new Cylinder(new Ray(center1.add(new Vector(2 * d, 0, 0)), normal), radius, height);

        c231 = new Cylinder(new Ray(center2.add(new Vector(0, 0, 1 * d)), normal), radius, height);
        c232 = new Cylinder(new Ray(center2.add(new Vector(0, 0, 2 * d)), normal), radius, height);

        c341 = new Cylinder(new Ray(center3.add(new Vector(-1 * d, 0, 0)), normal), radius, height);
        c342 = new Cylinder(new Ray(center3.add(new Vector(-2 * d, 0, 0)), normal), radius, height);

        c411 = new Cylinder(new Ray(center1.add(new Vector(0, 0, d * 1)), normal), radius, height);
        c412 = new Cylinder(new Ray(center1.add(new Vector(0, 0, 2 * d)), normal), radius, height);
    }

    /**
     * Returns the Geometries object that contains all the cylinders of the CylinderBase.
     *
     * @return The Geometries object containing all the cylinders.
     */
    public Geometries getGeometries() {
        return new Geometries(super.getGeometries(), c1, c2, c3, c4,
                c121, c122, c231, c232,
                c341, c342, c411, c412);
    }

    /**
     * Sets the emission colors of the base and all the cylinders in the CylinderBase.
     *
     * @param color1 The emission color of the base.
     * @param color2 The emission color of the cylinders.
     * @return The CylinderBase object with the updated emission colors.
     */
    public CylinderBase setCylinderBaseEmission(Color color1, Color color2) {
        super.setBaseEmission(color1, color2);
        c1 = (Cylinder) c1.setEmission(color1);
        c2 = (Cylinder) c2.setEmission(color1);
        c3 = (Cylinder) c3.setEmission(color1);
        c4 = (Cylinder) c4.setEmission(color1);
        c121 = (Cylinder) c121.setEmission(color1);
        c231 = (Cylinder) c231.setEmission(color1);
        c341 = (Cylinder) c341.setEmission(color1);
        c411 = (Cylinder) c411.setEmission(color1);
        c122 = (Cylinder) c122.setEmission(color1);
        c232 = (Cylinder) c232.setEmission(color1);
        c342 = (Cylinder) c342.setEmission(color1);
        c412 = (Cylinder) c412.setEmission(color1);
        return this;
    }

    /**
     * Sets the material of the base and all the cylinders in the CylinderBase.
     *
     * @param mt The material of the base and cylinders.
     * @return The CylinderBase object with the updated material.
     */
    public CylinderBase setCylinderBaseMaterial(Material mt) {
        super.setBaseMaterial(mt);
        c1 = (Cylinder) c1.setMaterial(mt);
        c2 = (Cylinder) c2.setMaterial(mt);
        c3 = (Cylinder) c3.setMaterial(mt);
        c4 = (Cylinder) c4.setMaterial(mt);
        c121 = (Cylinder) c121.setMaterial(mt);
        c231 = (Cylinder) c231.setMaterial(mt);
        c341 = (Cylinder) c341.setMaterial(mt);
        c411 = (Cylinder) c411.setMaterial(mt);
        c122 = (Cylinder) c122.setMaterial(mt);
        c232 = (Cylinder) c232.setMaterial(mt);
        c342 = (Cylinder) c342.setMaterial(mt);
        c412 = (Cylinder) c412.setMaterial(mt);

        return this;
    }
}
