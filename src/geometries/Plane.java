package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    Point p0;
    Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
        p0=p1;
        normal=null;
    }
    public Plane(Point p, Vector v) throws IllegalAccessException {
        this.p0=p;
        this.normal=v.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }
}
