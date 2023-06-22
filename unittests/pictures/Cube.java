package pictures;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class Cube {
    Polygon base;
    Polygon top;
    Polygon front;
    Polygon back;
    Polygon rightSide;
    Polygon leftSide;

    public Cube(Point p1, double z, double x, double y) throws IllegalAccessException {
        Point p2 = p1.add(new Vector(x, 0, 0));
        Point p3 = p2.add(new Vector(0, 0, z));
        Point p4 = p1.add(new Vector(0, 0, z));
        Point p5 = p1.add(new Vector(0, y, 0));
        Point p6 = p2.add(new Vector(0, y, 0));
        Point p7 = p3.add(new Vector(0, y, 0));
        Point p8 = p4.add(new Vector(0, y, 0));

        top = new Polygon(p5, p6, p7, p8);
        base = new Polygon(p1, p2, p3, p4);
        front = new Polygon(p1, p2, p6, p5);
        back = new Polygon(p4, p3, p7, p8);
        rightSide = new Polygon(p2, p3, p7, p6);
        leftSide = new Polygon(p1, p4, p8, p5);

    }
    public Geometries getGeometries() {
        return new Geometries(top,base,front,back,rightSide,leftSide);
    }

    public Cube setCubeEmission(Color color) {
        top = (Polygon) top.setEmission(color);
        base = (Polygon) base.setEmission(color);
        front = (Polygon) front.setEmission(color);
        back = (Polygon) back.setEmission(color);
        rightSide = (Polygon) rightSide.setEmission(color);
        leftSide = (Polygon) leftSide.setEmission(color);
        return this;
    }


}