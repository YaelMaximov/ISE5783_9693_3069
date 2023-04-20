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
     * Test method for {@link geometries.Plane#findIntsersections(Ray)} .
     */

    @Test
    void testFindIntersectionPoints() throws IllegalAccessException {
        Plane plane= new Plane(new Point(0,0,0),new Point(1,0,0),new Point(0,1,0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray begins out of the plane,doesn't parallel to the plane,
        // doesn't orthogonal to the plane and crosses the plane (1 point)
        Point p1 = new Point(0,-1,-1);
        List<Point> result = plane.findIntsersections(new Ray(new Point( 0, 1,1),
                new Vector(0,-1,-1)));
        assertEquals(List.of(p1), result, "Ray crosses plane");
        // TC02: Ray begins out of the plane,doesn't parallel to the plane,
        // doesn't orthogonal to the plane and doesn't cross the plane (0 points)
        assertNull(plane.findIntsersections(new Ray(new Point(2, 2, 2), new Vector(1, 1, -1))),
                "Ray doesn't crosses plane");




    }


}