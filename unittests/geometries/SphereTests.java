package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:There is a simple single test here
        Sphere sphere = new Sphere(1.0, new Point(1, 0, 0));
        Vector vector = new Vector(0, 1, 1);
        assertEquals(vector, sphere.getNormal(new Point(1, 1, 1)), "Bad normal to sphere");
    }
}