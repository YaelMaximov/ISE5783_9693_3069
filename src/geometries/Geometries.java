package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    private List<Intersectable> intersectables;

    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometry) {
        intersectables = List.of(geometry);
    }

    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));//check
    }


    //    @Override
//    public List<Point> findIntsersections(Ray ray) throws IllegalAccessException {
//        boolean found_inter = false;
//        if (intersectables ==null)
//        {return null;}
//        for (Intersectable geometry : intersectables) {
//            if (geometry.findIntsersections(ray) != null) {
//                found_inter = true;
//                break;
//            }
//        }
//        if (found_inter) {
//            List<Point> Intersections=new LinkedList<>();
//            for (Intersectable geometry : intersectables) {
//                if (geometry.findIntsersections(ray) != null) {
//                    Intersections.addAll(geometry.findIntsersections(ray));
//                }
//            }
//            return Intersections;
//        } else {
//            return null;
//        }
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
        List<GeoPoint> Intersections =null;
        for (Intersectable geometry : intersectables) {
            var temp = geometry.findGeoIntersections(ray);
            if (temp != null){
                if (Intersections == null) Intersections = new LinkedList<>();
                Intersections.addAll(temp);
            }
        }
        return Intersections;
    }

    public void add(List<Triangle> triangles) {
        intersectables.addAll((triangles));
    }
}