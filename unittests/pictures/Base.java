package pictures;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class Base {
    Cube c1;
    Cube c2;
    Cube c3;
    final private double Height=3d;
    public Base(Point p1, double z, double x, double y) throws IllegalAccessException {
        c1 = new Cube(p1, z, x, Height);
        Point start = p1.add(new Vector((x-0.8*x)/2, Height, (z-0.8*z)/2));
        c2 = new Cube(start, z * 0.8, x * 0.8, y - 2 * Height);
        start = p1.add(new Vector(0, y - Height, 0));
        c3 = new Cube(start, z, x, Height);
    }
    public Geometries getGeometries() {
        return new Geometries(c1.getGeometries(),c2.getGeometries(),c3.getGeometries());
    }

    public Base setBaseEmission(Color color) {
        c1 = c1.setCubeEmission(color);
        c2 = c2.setCubeEmission(color);
        c3 = c3.setCubeEmission(color);

        return this;
    }
    public Base setBaseMaterial(Material mt) {
        c1 = c1.setCubeMaterial(mt);
        c2 = c2.setCubeMaterial(mt);
        c3 = c3.setCubeMaterial(mt);

        return this;
    }
}
