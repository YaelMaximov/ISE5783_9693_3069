package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Triangle class represents a triangle in 3D space.
 */
public class Triangle extends Polygon {

    /**
     Constructs a Triangle object with the given points.
     @param p1 the first Point of the triangle
     @param p2 the second Point of the triangle
     @param p3 the third Point of the triangle
     @throws IllegalAccessException if any of the given points are null
     */
    public Triangle(Point p1,Point p2,Point p3) throws IllegalAccessException {
        super(p1,p2,p3);
    }
    public List<Point> findIntsersections(Ray ray) throws IllegalAccessException {
        if(this.plane.findIntsersections(ray)==null){
            return null;
        };

        Point P0=ray.getP0();
        Vector v=ray.getDir();

        Point P1=this.vertices.get(0);
        Point P2=this.vertices.get(1);
        Point P3=this.vertices.get(2);

        Vector v1=P1.subtract(P0);
        Vector v2=P2.subtract(P0);
        Vector v3=P3.subtract(P0);

        double n1v=v1.crossProduct(v2).normalize().dotProduct(v);
        double n2v=v2.crossProduct(v3).normalize().dotProduct(v);
        double n3v=v3.crossProduct(v1).normalize().dotProduct(v);
        if((n1v==0)||(n2v==0)||(n3v==0)){
            return null;
        } else if ((n1v>0)&&(n2v>0)&&(n3v>0)) {
            return this.plane.findIntsersections(ray);
        } else if ((n1v<0)&&(n2v<0)&&(n3v<0)) {
            return this.plane.findIntsersections(ray);
        }
        else{
            return null;
        }


    }


    }
