package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for {@link Tube} class functionalities
 */
class TubeTest {

    Vector direction= new Vector(1,0,0);
    Ray ray1 =new Ray(new Point(0,0,0),direction);

    TubeTest() throws IllegalAccessException {
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}
     */
    @Test
    void testGetNormalEP() throws IllegalAccessException {
        Tube tube = new Tube(1, ray1);
        assertEquals(new Vector(0,0,1),tube.getNormal(new Point(5,0,1)),
                "normal vector returned is incorrect");
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}
     */
    @Test
    void testGetNormalBVE(){
        Tube tube=new Tube(1, ray1);
        assertThrows(IllegalArgumentException.class,
                ()->tube.getNormal(new Point(2,0,0)),
                "GetNormal() does not work for point on axis ray");

    }

    Vector vAxis = new Vector(0, 0, 1);
    Tube tube = new Tube(1d, new Ray(new Point(1, 1, 1), vAxis));
    Ray ray;


    //region ==================== Equivalence Partitions Tests =====================================================
    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionEP1() throws IllegalAccessException {
        // TC01: Ray's line is outside the tube (0 points)
        ray = new Ray(new Point(1, 8, 9), new Vector(1, 1, 0));
        assertNull(tube.findIntersections(ray), "ERROR - TC01: Must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionEP2() throws IllegalAccessException {
        // TC02: Ray's crosses the tube (2 points)
        ray = new Ray(new Point(0, 0, 0), new Vector(12, 6, 6));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "ERROR - TC02: must be intersections");
        assertEquals(2, result.size(), "ERROR - TC02: must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(
                List.of(new Point (0.4,0.2,0.2),
                        new Point (2,1,1)), result,
                "ERROR - TC02: Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionEP3() throws IllegalAccessException {
        // TC03: Ray's starts within tube and crosses the tube (1 point)
        ray = new Ray(new Point(1, 0.5, 0.5), new Vector(12, 6, 6));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull( result,"ERROR - TC03: must be intersections");
        assertEquals( 1, result.size(), "ERROR - TC03: must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "ERROR - TC03: Bad intersections");
    }
    //endregion

    //region =============== Boundary Values Tests =========================================================

    //region *** Group: Ray's line is parallel to the axis (0 points)
    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA11() throws IllegalAccessException {
        // TC11: Ray is inside the tube (0 points)
        ray = new Ray(new Point(0.5, 0.5, 0.5), vAxis);
        assertNull(tube.findIntersections(ray), "must not be intersections" );
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA12() throws IllegalAccessException {
        // TC12: Ray is outside the tube
        ray = new Ray(new Point(0.5, -5, 0.5), vAxis);
        assertNull( tube.findIntersections(ray),"must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA13() throws IllegalAccessException {
        // TC13: Ray is at the tube surface
        ray = new Ray(new Point(2, 1, 0), vAxis);
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA14() throws IllegalAccessException {
        // TC14: Ray is inside the tube and starts against axis head
        ray = new Ray(new Point(0.5, 0.5, 1), vAxis);
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }
    @Test
    void testFindIntersectionBVA15() throws IllegalAccessException {
        // TC15: Ray is outside the tube and starts against axis head
        ray = new Ray(new Point(0.5, -0.5, 1), vAxis);
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA16() throws IllegalAccessException {
        // TC16: Ray is at the tube surface and starts against axis head
        ray = new Ray(new Point (0.4,0.2,0.2), vAxis);
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA17() throws IllegalAccessException {
        // TC17: Ray is inside the tube and starts at axis head
        ray = new Ray(new Point(1, 1, 1), vAxis);
        assertNull( tube.findIntersections(ray), "must not be intersections");

    }
    //endregion

    //region *** Group: Ray is orthogonal but does not begin against the axis head
    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA21() throws IllegalAccessException {
        // TC21: Ray starts outside and the line is outside (0 points)
        ray = new Ray(new Point(-5, 4, 4), new Vector(1, 1, 0));
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA22() throws IllegalAccessException {
        // TC22: The line is tangent and the ray starts before the tube (0 points)
        ray = new Ray(new Point(-6, 2, 4), new Vector(1, 0, 0));
        assertNull( tube.findIntersections(ray), "must not be intersections");

    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA23() throws IllegalAccessException {
        // TC23: The line is tangent and the ray starts at the tube (0 points)
        ray = new Ray(new Point(1, 2, 6), new Vector(1, 0, 0));
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA24() throws IllegalAccessException {
        // TC24: The line is tangent and the ray starts after the tube (0 points)
        ray = new Ray(new Point(7, 2, 8), new Vector(1, 0, 0));
        assertNull( tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA25() throws IllegalAccessException {
        // TC25: Ray starts before (2 points)
        ray = new Ray(new Point(0, 0, 2), new Vector(2, 1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0.4, 0.2, 2), new Point(2, 1, 2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA26() throws IllegalAccessException {
        // TC26: Ray starts at the surface and goes inside (1 point)
        ray = new Ray(new Point(0.4, 0.2, 2), new Vector(2, 1, 0));
        List<Point> result  = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA27() throws IllegalAccessException {
        // TC27: Ray starts inside (1 point)
        ray = new Ray(new Point(1, 0.5, 2), new Vector(2, 1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA28() throws IllegalAccessException {
        // TC28: Ray starts at the surface and goes outside (0 points)
        ray = new Ray(new Point(2, 1, 12), new Vector(2, 1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA29() throws IllegalAccessException {
        // TC29: Ray starts after
        ray = new Ray(new Point(8, 14, 6), new Vector(2, 1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }


    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA30() throws IllegalAccessException {
        // TC30: Ray starts before and crosses the axis (2 points)
        ray = new Ray(new Point(1, -2, 2), new Vector(0,3 , 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 0, 2), new Point(1, 2, 2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA31() throws IllegalAccessException {
        // TC31: Ray starts at the surface and goes inside and crosses the axis
        ray = new Ray(new Point(1, 0, 4), new Vector(0, 6, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 4)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA32() throws IllegalAccessException {
        // TC32: Ray starts inside and the line crosses the axis (1 point)
        ray = new Ray(new Point(1, 0.3, 4), new Vector(0, 6, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 4)), result, "Bad intersections");

    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA33() throws IllegalAccessException {
        // TC33: Ray starts at the surface and goes outside and the line crosses the axis (0 points)
        ray = new Ray(new Point(0, 1, 3), new Vector(-1, 0, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result,"Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA34() throws IllegalAccessException {
        // TC34: Ray starts after and crosses the axis (0 points)
        ray = new Ray(new Point(4,1,4), new Vector(0, 3, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result,"Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA35() throws IllegalAccessException {
        // TC35: Ray start at the axis
        ray = new Ray(new Point(1, 1, 3), new Vector(0, 1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 3)), result, "Bad intersections");

    }
    //endregion

    //region  **** Group: Ray is orthogonal to axis and begins against the axis head
    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA41() throws IllegalAccessException {
        // TC41: Ray starts outside and the line is outside (
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 1, 0));
        List<Point> res= tube.findIntersections(ray);
        assertNull(res, "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA42() throws IllegalAccessException {
        // TC42: The line is tangent and the ray starts before the tube
        ray = new Ray(new Point(2, 0, 2), new Vector(0, 1, 0));
        assertNull(tube.findIntersections(ray), "must not be intersections");

    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA43() throws IllegalAccessException {
        // TC43: The line is tangent and the ray starts at the tube
        ray = new Ray(new Point(1, 0, 1), new Vector(3, 0, 0));
        assertNull(tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA44() throws IllegalAccessException {
        // TC44: The line is tangent and the ray starts after the tube
        ray = new Ray(new Point(2, 0, 2), new Vector(3, 0, 0));
        assertNull(tube.findIntersections(ray), "must not be intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA45() throws IllegalAccessException {
        // TC45: Ray starts before
        ray = new Ray(new Point(0, 0, 4), new Vector(1, 2, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0.2, 0.4, 4), new Point(1, 2, 4)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA46() throws IllegalAccessException {
        // TC46: Ray starts at the surface and goes inside
        ray = new Ray(new Point(0.2, 0.4, 4), new Vector(1, 2, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 4)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA47() throws IllegalAccessException {
        // TC47: Ray starts inside
        ray = new Ray(new Point(1, 0.5, 3), new Vector(2, 1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 3)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA48() throws IllegalAccessException {
        // TC48: Ray starts at the surface and goes outside
        ray = new Ray(new Point(2, 1, 3), new Vector(5, 6, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA49() throws IllegalAccessException {
        // TC49: Ray starts after
        ray = new Ray(new Point(3, 2, 3), new Vector(3, 5, 2));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA50() throws IllegalAccessException {
        // TC50: Ray starts before and goes through the axis head
        ray = new Ray(new Point(-2, 1, 1), new Vector(1, 0, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result,"must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0, 1, 1), new Point(2, 1, 1)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA51() throws IllegalAccessException {
        // TC51: Ray starts at the surface and goes inside and goes through the axis head
        ray = new Ray(new Point(1, 2, 1), new Vector(0, -1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull( result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 0, 1)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA52() throws IllegalAccessException {
        // TC52: Ray starts inside and the line goes through the axis head
        ray = new Ray(new Point(1, 1.5, 1), new Vector(0, -1, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull( result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 0, 1)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA53() throws IllegalAccessException {
        // TC53: Ray starts at the surface and the line goes outside and goes through the axis head
        ray = new Ray(new Point(2, 1, 1), new Vector(1, 0, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA54() throws IllegalAccessException {
        // TC54: Ray starts after and the line goes through the axis head
        ray = new Ray(new Point(5, 1, 1), new Vector(1, 0, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA55() throws IllegalAccessException {
        // TC55: Ray start at the axis head
        ray = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "Bad intersections");
    }
    //endregion

    //region  **** Group: Ray's line is neither parallel nor orthogonal to the axis and begins against axis head
    Point p0 = new Point(0, 2, 1);

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA61() throws IllegalAccessException {
        // TC61: Ray's line is outside the tube = 0 points
        ray = new Ray(new Point(0,2,1), new Vector(1, 1, 1));
        List<Point>  result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA62() throws IllegalAccessException {
        // TC62: Ray's line crosses the tube and begins before
        ray = new Ray(p0, new Vector(2, -1, 1));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(2, 1, 2), new Point(0.4, 1.8, 1.2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA63() throws IllegalAccessException {
        // TC63: Ray's line crosses the tube and begins at surface and goes inside - 1 point
        ray = new Ray(new Point(0.4, 1.8, 1), new Vector(2, -1, 1));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull( result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1.8)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA64() throws IllegalAccessException {
        // TC64: Ray's line crosses the tube and begins inside - 1 point
        ray = new Ray(new Point(1, 1.5, 1), new Vector(2, -1, -2));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(),"must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 0)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA65() throws IllegalAccessException {
        // TC65: Ray's line crosses the tube and begins at the axis head - 1 point
        ray = new Ray(new Point(1, 1, 1), new Vector(2, 0, -2));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 0)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA66() throws IllegalAccessException {
        // TC66: Ray's line crosses the tube and begins at surface and goes outside - 0 points
        ray = new Ray(new Point(2, 1, 1), new Vector(7, 4, 12));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA67() throws IllegalAccessException {
        // TC67: Ray's line is tangent and begins before - 0 points
        ray = new Ray(new Point(-5,0,1), new Vector(5, 0, 7));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA68() throws IllegalAccessException {
        // TC68: Ray's line is tangent and begins at the tube surface
        ray = new Ray(new Point(1, 0, 1), new Vector(5, 0, 7));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA69() throws IllegalAccessException {
        // TC69: Ray's line is tangent and begins after - 0 points
        ray = new Ray(new Point(3, 0, 1), new Vector(1, 0, 1));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }
    //endregion

    //region **** Group: Ray's line is neither parallel nor orthogonal to the axis and does not begin against axis head
    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA71() throws IllegalAccessException {
        // TC71: Ray's crosses the tube and the axis - 2 points
        ray = new Ray(new Point(-2, 1, 6), new Vector(4, 0, -2));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0,1,5), new Point(2,1,4)),
                result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA72() throws IllegalAccessException {
        // TC72: Ray's crosses the tube and the axis head - 2 points
        ray = new Ray(new Point(-1, 1, -1), new Vector(3, 0, 3));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0,1,0), new Point(2,1,2)),
                result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA73() throws IllegalAccessException {
        // TC73: Ray's begins at the surface and goes inside crossing the axis - 1 point
        ray = new Ray(new Point(0, 1, 6), new Vector(2, 0, -2));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 4)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA74() throws IllegalAccessException {
        // TC74: Ray's begins at the surface and goes inside crossing the axis head - 1 point
        ray = new Ray(new Point(0, 1, 0), new Vector(2, 0, 2));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA75() throws IllegalAccessException {
        // TC75: Ray's begins inside and the line crosses the axis - 1 point
        ray = new Ray(new Point(0.5, 1, 6), new Vector(1.5,0,-2));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2,1,4)), result, "Bad intersections");

    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA76() throws IllegalAccessException {
        // TC77: Ray's begins inside and the line crosses the axis head - 1 point
        ray = new Ray(new Point(0.5, 1, 0.5), new Vector(1.5, 0, 1.5));
        List<Point> result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2,1,2)), result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA77() throws IllegalAccessException {
        // TC77: Ray's begins at the axis - 1 point
        ray = new Ray(new Point(1, 1, 4), new Vector(1, 0, 2));
        List<Point>  result = tube.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2,1,6)), result, "Bad intersections");

    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA78() throws IllegalAccessException {
        // TC79: Ray's begins at the surface and goes outside - 0 points
        ray = new Ray(new Point(2, 1, 7), new Vector(6, 13, 11));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA79() throws IllegalAccessException {
        // TC79: Ray's begins at the surface and goes outside and the line "crosses" the axis - 0 points
        ray = new Ray(new Point(2,1,4), new Vector(1.5, 1, -2));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersectionBVA80() throws IllegalAccessException {
        // TC81: Ray's begins at the surface and goes outside and the line "crosses" the axis head - 0 points
        ray = new Ray(new Point(2,1,2), new Vector(1.5, 0, 1.5));
        List<Point> result = tube.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }
    //endregion

    //endregion

    List<Intersectable.GeoPoint> res=null;
    /**
     * Test method for {@link Tube#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void findGeoIntersectionsHelperTest1() throws IllegalAccessException {
        res=tube.findGeoIntersectionsHelper(new Ray(new Point(-3,1,3),new Vector(1,0,0)),2d);
        assertNull(res,"wrong zero intersections");
    }

    /**
     * Test method for {@link Tube#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void findGeoIntersectionsHelperTest2() throws IllegalAccessException {
        res=tube.findGeoIntersectionsHelper(new Ray(new Point(-3,1,3),new Vector(1,0,0)),3.8);
        assertEquals(1,res.size(),"wrong one intersections");
    }

    /**
     * Test method for {@link Tube#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void findGeoIntersectionsHelperTest3() throws IllegalAccessException {
        res=tube.findGeoIntersectionsHelper(new Ray(new Point(-3,1,3),new Vector(1,0,0)),7d);
        assertEquals(2,res.size(),"wrong two intersections");
    }

}




































