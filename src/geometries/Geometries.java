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



//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException {
//        List<GeoPoint> Intersections =null;
//        for (Intersectable geometry : intersectables) {
//            var temp = geometry.findGeoIntersections(ray);
//            if (temp != null){
//                if (Intersections == null) Intersections = new LinkedList<>();
//                Intersections.addAll(temp);
//            }
//        }
//        return Intersections;
//    }
    /**
     * find intersection between ray and all geometries in the geometry collection
     * @param ray ray towards the composite of geometries
     * @return  immutable list of intersection points as  {@link GeoPoint} objects
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) throws IllegalAccessException {
        List<GeoPoint> result = null;   // intersection points

        //for each geometry in intersect-able collection check intersection points
        for (var item: intersectables) {

            // get intersection point for each specific item, (item can be either geometry/nested composite of geometries)
            List<GeoPoint> itemList = item.findGeoIntersections(ray,maxDistance);

            // points were found , add to composite's total intersection points list
            if(itemList != null) {
                if(result==null){
                    result= new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        // return list of points - null if no intersection points were found
        return result;

    }
    public void add(List<Triangle> triangles) {
        intersectables.addAll((triangles));
    }

    protected void createBoundingBox() throws IllegalAccessException {

    }
}