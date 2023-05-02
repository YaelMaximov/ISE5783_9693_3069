package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 * @author Moria Yaeli
 */
class PointTests {
    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        Vector vector=new Vector(1,2,3);
        Vector vector1= new Vector(-2, -4, -6);
        Vector vector2 = new Vector(-1, -2, -3);
        assertEquals(vector.add(vector1),vector2,"ERROR: Point - Point does not work correctly");
        // =============== Boundary Values Tests ==================
        //there are no boundary tests



    }
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(new Vector(3,3,3).subtract(new Point(1,1,1)),new Vector(2,2,2),"ERROR: Point - Point does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11:test subtracting same point
        assertThrows(IllegalArgumentException.class,()->new Point(1,2,3).subtract(new Point(1,2,3)),"Subtract between the same point must throw an exception");

    }
    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        Point point= new Point(2,0,3);
        Point point1= new Point(0,2,2);
        assertEquals(point.distance(point1),3,"ERROR");
    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(14d,new Point(1,1,1).distanceSquared(new Point(2,3,4)),0.0001,//
                 "ERROR:wrong squared distance result");
        // =============== Boundary Values Tests ==================
        //TC11:test subtracting same point
        assertEquals(0d,new Point(1,2,3).distanceSquared(new Point(1,2,3)),0.0001,//
                "ERROR:wrong squared distance between the point and itself");
    }
}