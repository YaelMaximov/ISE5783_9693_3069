package geometries;

import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {
    @Test
    void testFindIntsersections() throws IllegalAccessException {
        // =============== Boundary Values Tests ==================
        //TC11 The list null
        Geometries g=new Geometries();
        Ray r=new Ray(new Point(1,1,1),new Vector(1,0,2));
        assertNull(g.findIntsersections(r),"the list of the geometries is null");

        //Tc12 Have geometries in the list, but they don't intersectable with the geometries.
        Sphere s =new Sphere(1, new Point(1,2,0));
        Triangle t=new Triangle(new Point(-1,1,0),new Point(-1,2,0),new Point(-2,1,0));
        Cylinder c=new Cylinder(1,new Ray(new Point(1,2,0),new Vector(1,5,0)),5);
        Geometries g1=new Geometries(s,t,c);
        Ray r1=new Ray((new Point(3,3,0)),new Vector(3,7,0));
        assertNull(g1.findIntsersections(r1));

        //TC13 Have geometries in the list, and only one intersectable with the geometries.
        Ray r2=new Ray(new Point(-1.5,1.2,0),new Vector(-1.5,0,0));
        assertEquals(t.findIntsersections(r2),g1.findIntsersections(r2),"just one geometry (the triangle) is Intersectable in the ray");

        //TC14 Have geometries in the list, and more then 1 geometries intersectable with the geometries.
        Ray r3 =new Ray(new Point(1,2,0),new Vector(1,6,0));
        assertEquals(g1.findIntsersections(r3),2,"a few geometry shape intersectable in the ray");

        //TC15 Have geometries in the list, and all the geometries intersectable with the geometries.
        Ray r4 =new Ray(new Point(-2,1.5,0),new Vector(3,1.5,0));
        assertEquals(g1.findIntsersections(r3),3,"all geometry shape intersectable in the ray");
    }

}