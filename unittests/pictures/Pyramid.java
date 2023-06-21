package pictures;

import geometries.Geometries;
import geometries.Polygon;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class Pyramid {
    Triangle t1;
    Triangle t2;
    Triangle t3;
    Triangle t4;
    Polygon base;
    public Pyramid(Point p1, double z, double x, double y) throws IllegalAccessException {
        Point p2 = p1.add(new Vector(x, 0, 0));
        Point p3 = p2.add(new Vector(0, 0, z));
        Point p4 = p1.add(new Vector(0, 0, z));
        Point p5 = p1.add(new Vector(x/2, y, z/2));
        base = new Polygon(p1,p2,p3,p4);
        t1=new Triangle(p1,p2,p5);
        t2=new Triangle(p2,p3,p5);
        t3=new Triangle(p3,p4,p5);
        t4=new Triangle(p4,p1,p5);
    }
    public Geometries getGeometries() {
        return new Geometries(base,t1,t2,t3,t4);
    }

    public Pyramid setPyramidEmission(Color color) {
        base = (Polygon) base.setEmission(color);
        t1=(Triangle) t1.setEmission(color);
        t2=(Triangle) t2.setEmission(color);
        t3=(Triangle) t3.setEmission(color);
        t4=(Triangle) t4.setEmission(color);
        return this;
    }
    public Pyramid setPyramidMaterial(Material mt) {
        base = (Polygon) base.setMaterial(mt);
        t1=(Triangle) t1.setMaterial(mt);
        t2=(Triangle) t2.setMaterial(mt);
        t3=(Triangle) t3.setMaterial(mt);
        t4=(Triangle) t4.setMaterial(mt);
        return this;
    }
}
