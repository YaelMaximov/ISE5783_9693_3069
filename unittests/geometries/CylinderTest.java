package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for {@link Cylinder} class functionalities
 */
class CylinderTest {

    Vector direction= new Vector(1,0,0);
    Ray ray=new Ray(new Point(0,0,0),direction);
    Cylinder cylinder1 = new Cylinder(ray,1,4);

    CylinderTest() throws IllegalAccessException {
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormalEP1() throws IllegalAccessException {
        assertEquals(new Vector(0,0,1), cylinder1.getNormal(new Point(3,0,1)),
                "returned normal vector is incorrect");

    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormalEP2() throws IllegalAccessException {
        assertEquals(direction.normalize(), cylinder1.getNormal(new Point(0,0.5,0)),
                "returned normal vector is incorrect");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormalEP3() throws IllegalAccessException {
        assertEquals(direction.normalize(), cylinder1.getNormal(new Point(4,0.5,0)),
                "returned normal vector is incorrect");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormalBVE1() throws IllegalAccessException {
        assertEquals(direction.normalize(), cylinder1.getNormal(new Point(4,0,0)),
                "returned normal vector is incorrect");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormalBVE2() throws IllegalAccessException {
        assertEquals(direction.normalize(), cylinder1.getNormal(new Point(0,0,0)),
                "returned normal vector is incorrect");
    }


    // ============ Equivalence Partitions Tests ==============



    Cylinder cylinder = new Cylinder(new Ray(new Point(2,0,0), new Vector(0,0,1)), 1d,2d);
    List<Point> result=null;
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP1() throws IllegalAccessException {
      //TC01 ray is outside and parallel to the cylinder's ray

        result = cylinder.findIntersections(new Ray(new Point(5, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP2() throws IllegalAccessException {
        //TC02 ray starts inside and parallel to the cylinder's ray

        result = cylinder.findIntersections(new Ray(new Point(2.5, 0, 1), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 2)), result, "Bad intersection point");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP3() throws IllegalAccessException {
        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point(2.5, 0, -1), new Vector(0, 0, 1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(2.5, 0, 2)), result, "Bad intersection point");
    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP4() throws IllegalAccessException {
        //TC04 ray starts from outside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point(-2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 0.5), new Point(3, 0, 0.5)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP5() throws IllegalAccessException {
        //TC05 ray starts from inside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP6() throws IllegalAccessException {
        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder

        result = cylinder.findIntersections(new Ray(new Point(5, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP7() throws IllegalAccessException {
        //TC07 ray starts from outside and crosses base and surface

        result = cylinder.findIntersections(new Ray(new Point(1, 0, -1), new Vector(1, 0, 1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0), new Point(3, 0, 1)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestEP8() throws IllegalAccessException {
        //TC08 ray starts from outside and crosses surface and base

        result = cylinder.findIntersections(new Ray(new Point(4, 0, 2), new Vector(-1, 0, -1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0), new Point(3, 0, 1)), result, "Bad intersection points");

    }
        // =============== Boundary Values Tests ==================

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA1() throws IllegalAccessException {
        //TC09 ray is on the surface of the cylinder (not bases)

        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA2() throws IllegalAccessException {
        //TC10 ray is on the base of the cylinder and crosses 2 times

        result = cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");
    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA3() throws IllegalAccessException {
        //TC11 ray is in center of the cylinder

        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 2)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA4() throws IllegalAccessException {
        //TC12 ray is perpendicular to cylinder's ray and starts from outside the tube

        result = cylinder.findIntersections(new Ray(new Point(-2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 0.5), new Point(3, 0, 0.5)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA5() throws IllegalAccessException {
        //TC13 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)

        result = cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA6() throws IllegalAccessException {
        //TC14 ray is perpendicular to cylinder's ray and starts from the center of cylinder

        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA7() throws IllegalAccessException {
        //TC15 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside

        result = cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA8() throws IllegalAccessException {
        //TC16 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside

        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");
    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA9() throws IllegalAccessException {

        //TC17 ray starts from the surface to outside

        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA10() throws IllegalAccessException {
        //TC18 ray starts from the surface to inside

        result = cylinder.findIntersections(new Ray(new Point(3,0,0.5), new Vector(-1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,0,0.5)), result, "Bad intersection point");

    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA11() throws IllegalAccessException {
        //TC19 ray starts from the center

        result = cylinder.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,1)), result, "Bad intersection point");

    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA12() throws IllegalAccessException {
        //TC20 prolongation of ray crosses cylinder

        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA13() throws IllegalAccessException {
        //TC21 ray is on the surface starts before cylinder

        result = cylinder.findIntersections(new Ray(new Point(3,0,-1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA14() throws IllegalAccessException {

        //TC22 ray is on the surface starts at bottom's base

        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA15() throws IllegalAccessException {
        //TC23 ray is on the surface starts on the surface

        result = cylinder.findIntersections(new Ray(new Point(3,0,1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");
    }
    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersectionsTestBVA16() throws IllegalAccessException {
        //TC24 ray is on the surface starts at top's base

        result = cylinder.findIntersections(new Ray(new Point(3,0,2), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");
    }

    List<Intersectable.GeoPoint> res=null;
    /**
     * Test method for {@link Cylinder#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void findGeoIntersectionsHelperTest1() throws IllegalAccessException {
        res=cylinder.findGeoIntersectionsHelper(new Ray(new Point(-1,0,1),new Vector(1,0,0)),1d);
        assertNull(res,"wrong zero intersections");
    }

    /**
     * Test method for {@link Cylinder#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void findGeoIntersectionsHelperTest2() throws IllegalAccessException {
        res=cylinder.findGeoIntersectionsHelper(new Ray(new Point(-1,0,1),new Vector(1,0,0)),2.5d);
        assertEquals(1,res.size(),"wrong one point intersections");
    }

    /**
     * Test method for {@link Cylinder#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void findGeoIntersectionsHelperTest3() throws IllegalAccessException {
        res=cylinder.findGeoIntersectionsHelper(new Ray(new Point(-1,0,1),new Vector(1,0,0)),5d);
        assertEquals(2,res.size(),"wrong two point intersections");
    }



}