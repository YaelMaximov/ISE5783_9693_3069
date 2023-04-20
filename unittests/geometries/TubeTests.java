package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for Tube class
 * @author Moria Yaeli
 */
class TubeTests {

    @Test
    void testGetAxisRay() {
    }
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() throws IllegalAccessException {

        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        Ray ray=new Ray(new Point(1, 1, 1),new Vector(1,0,0));
        Tube tube =new Tube(5, ray);
        Vector vector=new Vector(0,1,0);
        assertEquals(vector, tube.getNormal(new Point(2,6,1)), "found wrong normal to the tube");

        // =============== Boundary Values Tests ==================
        //TC11:test normal of a point that parallel to the origin point of the tube
        assertEquals(vector, tube.getNormal(new Point(1,6,1)), "found wrong normal to the tube");
        //TC12:test if the get normal function throws an exception for point on the ray
        assertThrows(IllegalArgumentException.class,()-> tube.getNormal(new Point(6,1,1)),
                "point cannot be on the tube axis");



    }

    @Test
    void testFindIntersectionPoints() {
    }
}