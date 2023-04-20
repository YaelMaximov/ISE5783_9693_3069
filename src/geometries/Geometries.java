package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    private List<Intersectable> geometries;

    public Geometries() {
        geometries = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries){
        //geometries=geometries; check
    }
    public void add(Intersectable... geometries){

    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
