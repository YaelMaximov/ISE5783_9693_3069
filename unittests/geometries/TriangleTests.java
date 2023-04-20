package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for Triangle class
 * @author Moria Yaeli
 */
class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(1, 1, 1), new Point(0, 0, 0));
        Vector vector = new Vector(0.0D, -0.7071067811865475D, 0.7071067811865475D);
        assertEquals(vector, triangle.getNormal(new Point(0, 0, 0)), "Bad normal to triangle");
    }
    @Test
    void testFindIntersectionPoints() {
    }

}