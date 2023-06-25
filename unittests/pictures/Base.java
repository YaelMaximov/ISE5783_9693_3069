package pictures;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

import java.util.List;

public class Base {
    Cube c1;
    Cube c2;
    Cube c3;
    final public double Height=1.5d;
    public Base(Point p1, double zx, double y,double k) throws IllegalAccessException {
        c1 = new Cube(p1, zx, Height);
        Point start = p1.add(new Vector((zx-k*zx)/2, Height, (zx-k*zx)/2));
        c2 = new Cube(start, zx * k, y - 2 * Height);
        start = p1.add(new Vector(0, y - Height, 0));
        c3 = new Cube(start, zx, Height);
    }

    public Vector getC1Normal(Point p) throws IllegalAccessException {
        Vector normal=c1.getCubeTopNormal(p);
        return normal;
    }
    public List<Point> points() {
        return  List.of(c1.p2,c1.p3,c1.p4);

    }

    public Geometries getGeometries() {
        return new Geometries(c1.getGeometries(),c2.getGeometries(),c3.getGeometries());
    }
    public Base setBaseEmission(Color color1,Color color2) {
        c1 = c1.setCubeEmission(color1);
        c2 = c2.setCubeEmission(color2);
        c3 = c3.setCubeEmission(color1);

        return this;
    }
    public Base setBaseMaterial(Material mt) {
        c1 = c1.setCubeMaterial(mt);
        c2 = c2.setCubeMaterial(mt);
        c3 = c3.setCubeMaterial(mt);

        return this;
    }
}
