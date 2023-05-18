package geometries;

import primitives.Point;
import primitives.Ray;


import java.util.ArrayList;
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
    public List<Point> findIntsersections(Ray ray) throws IllegalAccessException {
        List<Point> Intersections = null;
        boolean created = false;
        for (Intersectable geometry : intersectables) {
            if (geometry.findIntsersections(ray) != null) {
                if (!created) {
                    Intersections = geometry.findIntsersections(ray);
                    created = true;
                } else {
                    Intersections.addAll(geometry.findIntsersections(ray));
                }
            }
        }
        if (created) {
            return Intersections;
        }
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
        List<GeoPoint> arrayList=new ArrayList<GeoPoint>();
        for (Intersectable geo: intersectables) {
            int i=0;
            if (geo.findGeoIntersections(ray) != null) {
                arrayList.add((GeoPoint) geo.findGeoIntersections(ray/*maxDistance*/).get(i));
                i++;
            }
        }
        if(arrayList.isEmpty())
        {
            return null;
        }
        return arrayList;
    }
}