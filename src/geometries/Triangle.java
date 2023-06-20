package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Triangle class represents a triangle in 3D space.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle object with the given points.
     *
     * @param p1 the first Point of the triangle
     * @param p2 the second Point of the triangle
     * @param p3 the third Point of the triangle
     * @throws IllegalAccessException if any of the given points are null
     */
    public Triangle(Point p1, Point p2, Point p3) throws IllegalAccessException {
        super(p1, p2, p3);
    }

    /**
     * find intersection between ray and  2D triangle
     *
     * @param ray ray towards object
     * @return immutable list of one intersection point as  {@link GeoPoint} object
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalAccessException {
        // check if ray intersects plane containing the triangle
        List<GeoPoint> result = plane.findGeoIntersections(ray);
        // no intersections
        if (result == null)
            return null;

        //check that intersection point is closer to ray origin than
        // max distance parameter
        double distance = result.get(0).point.distance(ray.getP0());
        if (alignZero(distance - maxDistance) > 0)
            return null;

        // check if intersection points are in Triangle
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // create three vectors between ray origin and
        //each of triangle vertices
        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector v3 = p3.subtract(p0);

        // n1,n2 ,n3 = value of dot product between ray vector
        // and the result vector of cross product between pairs
        // of vectors from ray origin and triangle vertices
        // if n1 or n2 pr m3 == 0 - intersection on border -> no intersection

        double n1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(n1))
            return null;

        double n2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(n2))
            return null;

        double n3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(n3))
            return null;

        // if sign of all three values ,n1 ,n2 ,n3 is not equal
        // intersection point is not on triangle
        if (!((n1 < 0 && n2 < 0 && n3 < 0) || (n1 > 0 && n2 > 0 && n3 > 0)))
            return null;

        // ray intersects triangle
        return List.of(new GeoPoint(this, result.get(0).point));
    }

    @Override
    public String toString() {
        return "Triangle: " +
                "vertices = " + vertices +
                ", plane = " + plane;
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


