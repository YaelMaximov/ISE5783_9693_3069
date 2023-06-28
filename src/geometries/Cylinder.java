package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Finite tube delimited by two planes
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder
     */
    final private double height;

    /**
     * Constructs a cylinder with the specified axis ray, radius, and height.
     *
     * @param axisRay The ray originating from the base of the cylinder.
     * @param radius  The radius of the cylinder.
     * @param height  The height of the cylinder.
     * @throws IllegalArgumentException If the height is not a positive value.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(radius, axisRay);
        if (height <= 0)
            throw new IllegalArgumentException("Height must be a positive value");
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder: " +
                "height = " + height +
                ", axisRay = " + axisRay +
                ", radius = " + radius;
    }

    /**
     * Calculates and returns the normal vector to the cylinder at the specified point.
     * Overrides the {@link Geometry#getNormal(Point)} method.
     *
     * @param point The point to calculate the normal at.
     * @return The normal vector to the cylinder at the specified point.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    @Override
    public Vector getNormal(Point point) throws IllegalAccessException {
        Vector direction = axisRay.getDir();
        Point P0 = axisRay.getP0();

        // Check if the given point is on the base of the cylinder
        if (point.equals(P0) || isZero(point.subtract(P0).dotProduct(direction)))
            return direction.normalize();

        // Check if the given point is on the top base of the cylinder
        if (point.equals(P0.add(direction.scale(height))) || isZero(point.subtract(P0.add(direction.scale(height))).dotProduct(direction)))
            return direction.normalize();

        // Check if the given point is on the circumference of the cylinder
        return super.getNormal(point);
    }

    /**
     * Finds the intersection points between a ray and the 3D cylinder.
     * Overrides the {@link Tube#findGeoIntersectionsHelper(Ray, double)} method.
     *
     * @param ray         The ray towards the cylinder.
     * @param maxDistance The maximum distance to check for intersections.
     * @return An immutable list containing 0, 1, or 2 intersection points as {@link GeoPoint} objects.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) throws IllegalAccessException {
        // Origin point of the cylinder (on the bottom base)
        Point basePoint = axisRay.getP0();
        // Point across the base point on the top base
        Point topPoint = axisRay.getPoint(height);
        // Direction vector of the cylinder (orthogonal to the base point)
        Vector vC = axisRay.getDir();

        // Find intersection points of the ray with the bottom base of the cylinder
        List<GeoPoint> result = new LinkedList<>();
        // Create a plane that contains the base point
        Plane basePlane = new Plane(basePoint, vC);
        // Find intersections between the ray and the plane
        List<GeoPoint> intersectionsBase = basePlane.findGeoIntersections(ray, maxDistance);

        // If intersections were found, check that the points are actually on the base of the cylinder
        // by checking if the distance from the base point to the intersection point satisfies the equation: distance² < radius²
        if (intersectionsBase != null) {
            for (GeoPoint p : intersectionsBase) {
                Point pt = p.point;
                // Intersection point is the base point itself
                if (pt.equals(basePoint))
                    result.add(new GeoPoint(this, basePoint));
                    // Intersection point is different from the base point but is on the bottom base
                else if (pt.subtract(basePoint).dotProduct(pt.subtract(basePoint)) < radius * radius)
                    result.add(new GeoPoint(this, pt));
            }
        }

        // Find intersection points of the ray with the top base of the cylinder
        // Create a plane that contains the top point
        Plane topPlane = new Plane(topPoint, vC);
        // Find intersections between the ray and the plane
        List<GeoPoint> intersectionsTop = topPlane.findGeoIntersections(ray, maxDistance);
        // If intersections were found, check that the points are actually on the base of the cylinder
        // by checking if the distance from the top point to the intersection point satisfies the equation: distance² < radius²
        if (intersectionsTop != null) {
            for (var p : intersectionsTop) {
                Point pt = p.point;
                // Intersection point is the top point itself
                if (pt.equals(topPoint))
                    result.add(new GeoPoint(this, topPoint));
                    // Intersection point is different from the base point but is on the top base
                else if (pt.subtract(topPoint).dotProduct(pt.subtract(topPoint)) < radius * radius)
                    result.add(new GeoPoint(this, pt));
            }
        }

        // If the ray intersects both bases, no other intersections are possible - return the result list
        if (result.size() == 2)
            return result;

        // Use the parent class function to find intersections with the cylinder represented as an infinite tube
        List<GeoPoint> intersectionsTube = super.findGeoIntersectionsHelper(ray, maxDistance);

        // If intersection points were found, check that they are within the finite cylinder's boundary
        // by checking if the scalar product of the direction vector with a vector from the intersection point
        // to the bottom base point is positive, and the scalar product of the direction vector with a
        // vector from the intersection point to the top base point is negative
        if (intersectionsTube != null) {
            for (var p : intersectionsTube) {
                Point pt = p.point;
                if (vC.dotProduct(pt.subtract(basePoint)) > 0 && vC.dotProduct(pt.subtract(topPoint)) < 0)
                    result.add(new GeoPoint(this, pt));
            }
        }

        // Return an immutable list
        int len = result.size();
        if (len > 0)
            if (len == 1)
                return List.of(result.get(0));
            else
                return List.of(result.get(0), result.get(1));

        // No intersections
        return null;
    }
}
