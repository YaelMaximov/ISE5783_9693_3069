package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for Sphere class
 * @author Moria and Yaeli
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
    // * Test method for {@link geometries.Sphere#findIntsersections(Ray)}
    @Test
    public void findIntersectionsSphere() throws IllegalAccessException
    {

        Sphere sphere4= new Sphere(1, new Point(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01:Ray's line is outside the sphre(point 0)
        assertNull(sphere4.findIntersections(new Ray(new Point(-1,0,0),new Vector(1,1,0))),"Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere4.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        /* assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");*/

        // TC03: Ray starts inside the sphere (1 point)
        List<Point>numPoin1=sphere4.findIntersections(new Ray(new Point(1,0.5,0),new Vector(1,1,0)));
        assertEquals(1,numPoin1.size(),"Ray starts inside");
        Point p031 = new Point(1.4114378277661477,0.9114378277661477,0.0);
        assertEquals(List.of(p031),numPoin1 ,"Ray starts inside");
        // TC04: Ray starts after the sphere (0 points)
        List<Point> numberPoint2=sphere4.findIntersections(new Ray(new Point(2,4,0),new Vector(2,6,0)));
        assertNull(numberPoint2,"Ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // TC11: Ray starts at sphere and goes inside(1 points)
        List<Point>numberPoint3=sphere4.findIntersections(new Ray(new Point(0,0,0),new Vector(1.6,0.5,0)));
        assertEquals(1,numberPoint3.size(),"Ray starts at sphere and goes inside(1 points)");
        Point p111 = new Point(1.8220640569395017,0.5693950177935942,0.0);
        assertEquals(List.of(p111),numberPoint3,"Ray starts at sphere and goes inside(1 points)");
        // TC12: Ray starts at sphere and goes outside (0 points)//האם לפתוח כל בדיקה רשימה חדשה או שמספיק אחת (אם הבמספר הנקודות נשמר?)
        List<Point>numberPoint1=sphere4.findIntersections(new Ray(new Point(0,0,0),new Vector(0,1,0)));
        assertNull(numberPoint1,"Ray starts at sphere and goes outside ");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        List<Point>numPoint=sphere4.findIntersections(new Ray(new Point(-1,0,0),new Vector(1,0,0)));
        assertEquals(2,numPoint.size(),"Ray starts before the sphere ");
        Point p131 = new Point(0,0,0);
        Point p132 = new Point(2,0,0);
        assertEquals(List.of(p131, p132),numPoint,"Ray starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)//מתחיל בספרה והולך לבחוץ
        List<Point> numberPoin=sphere4.findIntersections(new Ray(new Point(0,0,0),new Vector(2,0,0)));
        assertEquals(1,numberPoin.size(),"Ray starts at sphere and goes inside");
        assertEquals(List.of(p132), numberPoin,"Ray starts at sphere and goes inside");
        // TC15: Ray starts inside (1 points)//מתחיל מבפנים
        List<Point>numPoint1=sphere4.findIntersections(new Ray(new Point(1.5,0,0),new Vector(2,0,0)));
        assertEquals(1,numPoint1.size(),"Ray starts inside");
        assertEquals(List.of(p132),numPoint1,"Ray starts inside" );
        // TC16: Ray starts at the center (1 points)//מתחיל בנקודת מרכז ויש נקודה 1
        //List <Point> points=sphere4.findIntsersections(new Ray(new Point(1,0,0),new Vector(1,0,1)));
        //assertEquals(1,points.size(),"Ray starts at the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        List<Point>pointList=sphere4.findIntersections(new Ray(new Point(2,0,0),new Vector(3,0,0)));
        assertNull(pointList,"Ray starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)
        List<Point>pointList1=sphere4.findIntersections(new Ray(new Point(3,0,0),new Vector(4,0,0)));
        assertNull(pointList1,"Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        List<Point>numPoint3=sphere4.findIntersections(new Ray(new Point(2,1,0),new Vector(1,1,0)));
        assertNull(numPoint3,"starts before the tangent point");
        // TC20: Ray starts at the tangent point
        List<Point>numPoint4=sphere4.findIntersections(new Ray(new Point(1,1,0),new Vector(0.5,1,0)));
        assertNull(numPoint4,"Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        List<Point>numPoint5=sphere4.findIntersections(new Ray(new Point(0.5,1,0),new Vector(0.2,1,0)));
        assertNull(numPoint5,"Ray starts after the tangent point");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        List<Point>numPoint6=sphere4.findIntersections(new Ray(new Point(1,2,0),new Vector(0.5,2,0)));
        assertNull(numPoint6,"Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}