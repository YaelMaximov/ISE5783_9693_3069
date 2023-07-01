package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    protected List<Intersectable> intersectables;

    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometry) {
        if (bvhIsOn)
            createBoundingBox();
        //intersectables = List.of(geometry);
        intersectables = new LinkedList<>();
        Collections.addAll(intersectables, geometry);
    }

    public void add(Intersectable... geometries) {
        if (bvhIsOn)
            createBoundingBox();
        Collections.addAll(intersectables, geometries);
        //intersectables.addAll(Arrays.asList(geometries));//check
    }


    /**
     * find intersection between ray and all geometries in the geometry collection
     * @param ray ray towards the composite of geometries
     * @return  immutable list of intersection points as  {@link GeoPoint} objects
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) throws IllegalArgumentException {
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
    //public void add(List<Triangle> triangles) {
    //intersectables.addAll((triangles));
    //}

    @Override
    public void createBoundingBox() {
        if (intersectables == null)
            return;
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : intersectables) {
            if (geo.box != null) {
                minX = Math.min(minX, geo.box.minimums.getX());
                minY = Math.min(minY, geo.box.minimums.getY());
                minZ = Math.min(minZ, geo.box.minimums.getZ());
                maxX = Math.max(maxX, geo.box.maximums.getX());
                maxY = Math.max(maxY, geo.box.maximums.getY());
                maxZ = Math.max(maxZ, geo.box.maximums.getZ());
            }
        }
        box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }
}