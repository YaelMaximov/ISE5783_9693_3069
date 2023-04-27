package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class GeometriesTests {
    @Test
    void testFindIntersections() throws IllegalAccessException {
        Geometries g;
        Ray r;
        Sphere s =new Sphere(2, new Point(1,2,0));
        Triangle t=new Triangle(new Point(-1,1,4),new Point(1,4,4),new Point(2,1,4));
        Plane p=new Plane(new Point(-1,1,-4),new Point(1,3,-4),new Point(2,1,-4));
        g=new Geometries(s,t,p);
        // ============ Equivalence Partitions Tests ==============
        //TC01 the list of geometries is full but there is more than one intersection point with the shapes(1 point)
        r =new Ray(new Point(2,1,3),new Vector(0,1,-3));
        List<Point> result= g.findIntsersections(r);
        assertEquals(3,result.size(),"found wrong number of intersection points in a full list of geometries ");

        // =============== Boundary Values Tests ==================
        //TC11 the list of geometries is full but the ray has no intersection with the shapes in the list
        r=new Ray((new Point(1,2,5)),new Vector(0,-1,1));
        assertNull(g.findIntsersections(r),"Shouldn't return a value for a ray that doesn't intersect the shapes");

        //TC12  the list of geometries is full but there is only one intersection point with the shapes(1 point)
        r=new Ray(new Point(2,1,-3),new Vector(-1,1,-2));
         result= g.findIntsersections(r);
        assertEquals(1,result.size(),"should return only one intersection point with the shapes(with the plane)");

        //TC13 the list of geometries is full,the ray crosses all the shapes(1 point).
        r=new Ray(new Point(1,1,5),new Vector(0,2,-5));
        result= g.findIntsersections(r);
        assertEquals(4,result.size(),"wrong number of intersection points for a ray that crosses all the shapes");
        //TC14 empty list
        g=new Geometries();
        r=new Ray(new Point(1,1,1),new Vector(1,0,2));
        assertNull(g.findIntsersections(r),"the list of the geometries should be null");
    }

}