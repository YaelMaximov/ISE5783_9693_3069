package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Plane class
 * @author Moria Yaeli
 */
class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        //TC01: first point = second point
        assertThrows(IllegalAccessException.class, //
                () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)),
               "ERROR:Two points are the same point");
        //TC02:The points are on the same line
        assertThrows(IllegalAccessException.class, //
                () -> new Plane(new Point(1, 2, 3), new Point(2,4,6), new Point(4, 8, 12)),
                "ERROR: The points are on same line");

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     * */
    @Test
    void testTestGetNormal() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane plane= new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,0));

        assertEquals(new Vector(0,0,1), plane.getNormal(new Point(1, 0,0)), "Bad normal to plane");
    }
    /**
     * Test method for {@link Intersectable#findGeoIntersections(Ray)} .
     */

    @Test
    void testFindIntersectionPoints() throws IllegalAccessException {
        // Initialize the plane and ray objects
        Point p0 = new Point(2, 2, 1);
        Vector normal = new Vector(0, 1, 0);
        Plane plane = new Plane(p0, normal);
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray begins out of the plane, doesn't parallel to the plane,
        // doesn't orthogonal to the plane and crosses the plane (1 point)
        ray = new Ray(new Point(0, 10, 0), new Vector(3,-4,0));
        List<Intersectable.GeoPoint> expected = List.of(new Intersectable.GeoPoint(plane,new Point(6,2,0)));
        assertEquals(expected, plane.findGeoIntersections(ray),"wrong point for regular ray that crosses the plane");

        // TC02: Ray begins out of the plane, doesn't parallel to the plane,
        // doesn't orthogonal to the plane and doesn't cross the plane (0 points)
        ray = new Ray(new Point(3,5,4), new Vector(1, 2, 1));
        assertNull(plane.findGeoIntersections(ray),"regular ray that doesn't cross the plane shouldn't returned value");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line parallel to the plane
        // TC11: Ray in the plane (0 points)
        ray = new Ray(new Point(3,2,1), new Vector(0, 0, 1));
        assertNull(plane.findGeoIntersections(ray),"ray in the plane shouldn't returned value");

        // TC12: Ray out of the plane (0 points)
        ray = new Ray(new Point(2, 1, 2), new Vector(1, 0, 0));
        assertNull(plane.findGeoIntersections(ray),"ray parallel to a plane but not in it shouldn't return value");

        // **** Group: Ray's line orthogonal to the plane
        // TC13: Ray starts before the plane (1 point)
        ray = new Ray(new Point(2, 0, 1), new Vector(0, 1, 0));
        expected = List.of(new Intersectable.GeoPoint(plane,new Point(2, 2, 1)));
        assertEquals(expected, plane.findGeoIntersections(ray),"wrong value for the cross point of an orthogonal ray begins before the plane");

        // TC14: Ray starts in the plane (0 points)
        ray = new Ray(new Point(3, 2, 1), new Vector(0, 1, 0));
        assertNull(plane.findGeoIntersections(ray),"orthogonal ray begins in the plane shouldn't return value");

        // TC15: Ray starts after the plane (0 points)
        ray = new Ray(new Point(2, 3, 2), new Vector(0, 1, 0));
        assertNull(plane.findGeoIntersections(ray),"orthogonal ray after in the plane shouldn't return value");

        // **** Group: Ray line that starts in the plane but doesn't orthogonal or parallel to it
        // TC16: Ray starts in the plane ( points)
        ray = new Ray(new Point(3, 2, 1), new Vector(3,-4,0));
        assertNull(plane.findGeoIntersections(ray),"regular ray starts in the plane shouldn't return value");


        // **** Group: Ray line that starts in the point defining the plane but doesn't orthogonal or parallel to it
        // TC17: Ray starts in the point defining the plane ( points)
        ray = new Ray(new Point(2, 2, 1), new Vector(3,-4,0));
        assertNull(plane.findGeoIntersections(ray),"regular ray starts in the point defining the  plane shouldn't return value");
    }



}