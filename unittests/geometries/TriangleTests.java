//package geometries;
//
//import org.junit.jupiter.api.Test;
//import primitives.Point;
//import primitives.Ray;
//import primitives.Vector;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
///**
// * Unit tests for Triangle class
// * @author Moria Yaeli
// */
//class TriangleTests {
//    /**
//     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
//     */
//    @Test
//    void testGetNormal() throws IllegalAccessException {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: There is a simple single test here
//        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(1, 1, 1), new Point(0, 0, 0));
//        Vector vector = new Vector(0.0D, -0.7071067811865475D, 0.7071067811865475D);
//        assertEquals(vector, triangle.getNormal(new Point(0, 0, 0)), "Bad normal to triangle");
//    }
//    @Test
//    void testFindIntersectionPoints() throws IllegalAccessException {
//        Point p1=new Point(0,2,-1);
//        Point p2=new Point(4,2,0);
//        Point p3=new Point(2,2,2);
//        Triangle triangle=new Triangle(p1,p2,p3);
//        Ray ray;
//
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: intersection point in the triangle(1 point)
//        ray = new Ray(new Point(3,1,0), new Vector(-1,1,1));
//        List<Point> expected = List.of(new Point(2,2,1));
//        assertEquals(expected, triangle.findIntsersections(ray),"wrong point for regular ray that crosses the plane inside the triangle");
//        // TC02: intersection point out of the triangle in front of one of the edges(0 points)
//        ray = new Ray(new Point(3,1,0), new Vector(0,1,2));
//        assertNull(triangle.findIntsersections(ray),"intersection point out of the triangle in front of one of the edges shouldn't returned value");
//        // TC03: intersection point out of the triangle in front of one of the vertices(0 points)
//        ray = new Ray(new Point(3,1,0), new Vector(-1,1,3));
//        assertNull(triangle.findIntsersections(ray),"intersection point on one of the vertices shouldn't returned value");
//
//
//
//
//        // =============== Boundary Values Tests ==================
//        // TC11: intersection point on one of the edges(0 points)
//        ray = new Ray(new Point(3,1,0), new Vector(0,1,1));
//        assertNull(triangle.findIntsersections(ray),"intersection point on one of the edges shouldn't returned value");
//        // TC12: intersection point on one of the vertices(0 points)
//        ray = new Ray(new Point(3,1,0), new Vector(1,1,0));
//        assertNull(triangle.findIntsersections(ray),"intersection point on one of the vertices shouldn't returned value");
//        // TC13: intersection point on the continuation of one of the edges(0 points)
//        ray = new Ray(new Point(3,1,0), new Vector(-3,1,4));
//        assertNull(triangle.findIntsersections(ray),"intersection point on the continuation of one of the edges shouldn't returned value");
//    }
//
//}