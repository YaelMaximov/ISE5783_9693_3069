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
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalAccessException {
        if(this.plane.findGeoIntersectionsHelper(ray, maxDistance)==null){
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
            Point point = plane.findGeoIntersectionsHelper(ray, maxDistance).get(0).point;
            return  List.of(new GeoPoint(this, point));
        } else if ((n1v<0)&&(n2v<0)&&(n3v<0)) {
            Point point = plane.findGeoIntersectionsHelper(ray, maxDistance).get(0).point;
            return  List.of(new GeoPoint(this, point));
        }
        else{
            return null;
        }


    }
//    @Override
//    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
//        /*List<Point> list = new ArrayList<Point>(findIntsersections(ray));
//        List<GeoPoint> geoPointList = new ArrayList<GeoPoint>();
//        int i=0;
//
//        for (Point geoPoint : list) {
//            Geometry geometry = null;
//            GeoPoint geoPoint1=new GeoPoint(geometry,geoPoint);
//            geoPointList.add(i,geoPoint1);
//            i++;
//        }
//        return geoPointList;*/
//        if (plane.findIntsersections(ray) == null)
//            return null;
//        Point P0 = ray.getP0();
//        Point p1 = vertices.get(0);
//        Point p2 = vertices.get(1);
//        Point p3 = vertices.get(2);
//        Vector V1 = p1.subtract(P0);
//        Vector V2 = p2.subtract(P0);
//        Vector V3 = p3.subtract(P0);
//        Vector N1 = V1.crossProduct(V2).normalize();
//        Vector N2 = V2.crossProduct(V3).normalize();
//        Vector N3 = V3.crossProduct(V1).normalize();
//        double vn1 = ray.getDir().dotProduct(N1);
//        double vn2 = ray.getDir().dotProduct(N2);
//        double vn3 = ray.getDir().dotProduct(N3);
//        if (vn1 == 0 && vn2 == 0 || vn2 == 0 && vn3 == 0 || vn1 == 0 && vn3 == 0)
//            return null;
//        if (vn1 >= 0 && vn2 >= 0 && vn3 >= 0 || vn1 <= 0 && vn2 <= 0 && vn3 <= 0) {
//            Point point = plane.findIntsersections(ray).get(0);
//            GeoPoint geoPoint = new GeoPoint(this, point);
//            ArrayList<GeoPoint> listGeo = new ArrayList<GeoPoint>();
//
//            return listGeo;
//
//        }
//        return null;
//    }


    }
