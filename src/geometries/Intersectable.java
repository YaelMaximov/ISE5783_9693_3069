package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents a geometric object that can be intersected by a ray.
 */
public abstract class Intersectable {
    protected boolean bvhIsOn=true;
    public AABB box;

    public void setBvhIsOn(boolean bvhIsOn) {
        this.bvhIsOn = bvhIsOn;
    }

//    /**
//     * Returns a list of points where the specified ray intersects this object.
//     *
//     * @param ray the ray to intersect with this object
//     * @return a list of points where the ray intersects this object, or an empty list if there are no intersections
//     */
//    public abstract List<Point> findIntsersections(Ray ray) throws IllegalArgumentException;

    public class AABB{
        public Point minimums;
        public Point maximums;
        public AABB(Point min,Point max){
            minimums=min;
            maximums=max;
        }

    }
    public boolean isIntersectingBoundingBox(Ray ray){
        if (!bvhIsOn || box ==null)
            return true;
        Vector dir= ray.getDir();
        Point p0=ray.getP0();
        double tMin = (box.minimums.getX()-p0.getX()) / dir.getX();
        double tMax = (box.maximums.getX()-p0.getX()) / dir.getX();
        if (tMin>tMax){
            double temp=tMin;
            tMin=tMax;
            tMax=temp;
        }
        double tyMin = (box.minimums.getY()-p0.getY()) / dir.getY();
        double tyMax = (box.maximums.getY()-p0.getY()) / dir.getY();
        if (tyMin > tyMax) {
            double temp=tyMin;
            tyMin=tyMax;
            tyMax=temp;
        }
        if((tMin>tyMax)||(tyMin > tMax))
            return false;
        if (tyMin > tMin)
            tMin=tyMin;
        if (tyMax< tMax)
            tMax=tyMax;
        double tzMin = (box.minimums.getZ()-p0.getZ()) / dir.getZ();
        double tzMax = (box.maximums.getZ()-p0.getZ()) / dir.getZ();
        if (tzMin > tzMax){
            double temp = tzMin;
            tzMin =tzMax;
            tzMax = temp;
        }
        if ((tMin > tzMax)||(tzMin > tMax))
            return false;
        if(tzMin>tMin)
            tMin = tzMin;
        if (tzMax<tMax)
            tMax = tzMax;
        return true;
    }

    public static class GeoPoint{
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry,Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }


    public List<Point> findIntersections(Ray ray) throws IllegalArgumentException {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException {
        if(bvhIsOn && !isIntersectingBoundingBox(ray))
            return null;
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) throws IllegalArgumentException {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }


    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalArgumentException;
    protected abstract   void createBoundingBox() throws IllegalArgumentException;

}
