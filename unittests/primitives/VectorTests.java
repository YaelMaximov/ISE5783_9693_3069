package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;

class VectorTests {


    VectorTests() throws IllegalAccessException {
    }

    @Test
    void testAdd() throws IllegalAccessException {
        Vector v1 =new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(new Vector(2, 3, 2),v1.add(new Vector(1, 1, -1)), "ERROR:wrong result of add function");
        // =============== Boundary Values Tests ==================
        // TC11: test if  Vector + -itself throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception");
    }
    @Test
    void testSubtract() throws IllegalAccessException{
        Vector v1 =new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(new Vector(0, 1, 4),v1.subtract(new Vector(1, 1, -1)), "ERROR:wrong result of add function");
        // =============== Boundary Values Tests ==================
        // TC11: test if Vector -itself throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: Vector - itself does not throw an exception");

    }

    @Test
    void testScaling() throws IllegalAccessException {
        Vector v1 =new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(new Vector(2,4,6),v1.scale(2), "ERROR:wrong result of add function");
        // =============== Boundary Values Tests ==================
        // TC11: test if zero scale throws an exception
        assertThrows(IllegalArgumentException.class, () ->v1.scale(0),
                "ERROR: Vector - itself does not throw an exception");


    }

    @Test
    void testDotProduct() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(-28,new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)),"ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        // TC11: test if dotProduct() for orthogonal vectors works right and not giving zero result
        assertTrue(isZero(new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2))), "crossProduct() result is not orthogonal to 1st operand");



    }
    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() throws IllegalAccessException {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(0,new Vector(1, 2, 3).lengthSquared() - 14,"ERROR: lengthSquared() wrong value");
    }
    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() throws IllegalAccessException {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(0,new Vector(0, 3, 4).length() - 5,"ERROR: length() wrong value");
    }
    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() throws IllegalAccessException {
        Vector v=new Vector(0,3,4);
        Vector n=v.normalize();
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(1d,n.lengthSquared(),0.00001,"wrong normalized vector length");
        assertThrows(IllegalArgumentException.class,()->v.crossProduct(n),"normalized vector is not iun the same direction");
        assertEquals(new Vector(0,0.6,0.8),n,"wrong normalized vector");
    }
}