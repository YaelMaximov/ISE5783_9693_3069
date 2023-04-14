package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        //TC01: first point = second point
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)),
                "ERROR:Two points are the same point");
        //TC02:The points are on the same line
        assertThrows(IllegalArgumentException.class, //
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
}