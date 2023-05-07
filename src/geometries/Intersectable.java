package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents a geometric object that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Returns a list of points where the specified ray intersects this object.
     *
     * @param ray the ray to intersect with this object
     * @return a list of points where the ray intersects this object, or an empty list if there are no intersections
     */
    public abstract List<Point> findIntsersections(Ray ray) throws IllegalAccessException;

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
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalAccessException;
    public List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalAccessException {
        return findGeoIntersectionsHelper(ray);
    }

}
