package geometries;

import primitives.Point;
import primitives.Ray;


import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> intersectables;

    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometry) {
        intersectables = List.of(geometry);
    }

    public void add(Intersectable... geometries) {


    }

    @Override
    public List<Point> findIntsersections(Ray ray) throws IllegalAccessException {
        boolean found_inter = false;
        if (intersectables ==null)
        {return null;}
        for (Intersectable geometry : intersectables) {
            if (geometry.findIntsersections(ray) != null) {
                found_inter = true;
                break;
            }
        }
        if (found_inter) {
            List<Point> Intersections=new LinkedList<>();
            for (Intersectable geometry : intersectables) {
                if (geometry.findIntsersections(ray) != null) {
                    Intersections.addAll(geometry.findIntsersections(ray));
                }
            }
            return Intersections;
        } else {
            return null;
        }

    }
}