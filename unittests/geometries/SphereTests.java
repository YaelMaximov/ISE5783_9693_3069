package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Unit tests for Sphere class
 * @author Moria Yaeli
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:There is a simple single test here
        Sphere sphere = new Sphere(1.0, new Point(1,1,1));
        Vector vector = new Vector(1, 0, 0);
        assertEquals(vector, sphere.getNormal(new Point(2, 1, 1)), "Bad normal to sphere");
    }
}