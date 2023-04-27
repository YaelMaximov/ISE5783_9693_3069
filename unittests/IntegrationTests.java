import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntegrationTests {

    int findNumberOfIntersecions(Camera camera,Geometry geometry) throws IllegalAccessException {
        int points=0;
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++){
                Ray ray=camera.constructRay(3,3,j,i);
                if(geometry.findIntsersections(ray)!=null){
                    points+=geometry.findIntsersections(ray).size();
                }
            }
        }
        return points;

    }
    @Test
    void CameraSphereIntersections() throws IllegalAccessException {
        Camera camera=new Camera(new Point(0,0,0),new Vector(0,-1,0),new Vector(0,0,-1));
        camera.setVPSize(3,3);
        camera.setVPDistance(1);

        // TC01: small sphere in front of view plane(2 points)
        Sphere sphere=new Sphere(1,new Point(0,0,-3));
        assertEquals(2,findNumberOfIntersecions(camera,sphere),"wrong number of intersection points ");

        camera=new Camera(new Point(0,0,0.5),new Vector(0,-1,0),new Vector(0,0,-1));
        camera.setVPSize(3,3);
        camera.setVPDistance(1);

        // TC02: big sphere in front of view plane(18 points)
        sphere=new Sphere(2.5,new Point(0,0,-2.5));
        assertEquals(18,findNumberOfIntersecions(camera,sphere),"wrong number of intersection points ");

        // TC03: the corners rays of the view plane doesn't cross the sphere  (10 points)
        sphere=new Sphere(2,new Point(0,0,-2));
        assertEquals(10,findNumberOfIntersecions(camera,sphere),"wrong number of intersection points ");

        // TC04: sphere behind the view plane (0 points)
        sphere=new Sphere(0.5,new Point(0,0,1));
        assertEquals(0,findNumberOfIntersecions(camera,sphere),"wrong number of intersection points ");



    }
    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void testConstructRayOfPlane() throws IllegalAccessException {
        Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3, 3);

        //TC01: the plane are parallel to the view plane
        Plane plane1 = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        assertEquals(findNumberOfIntersecions(camera1, plane1), 3, "bad");

        //TC02: the plane approaching to the vie plan
        Plane plane2 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 2));
        assertEquals(findNumberOfIntersecions(camera1, plane1), 3, "bad");

        //TC03: the plane slowly approaching to the vie plan and don't touch the ray of the third pixel
        Plane plane3 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 1));
        assertEquals(findNumberOfIntersecions(camera1, plane1), 3, "bad");
    }

    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void testConstructRayOfTriangle() throws IllegalAccessException {
        Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3, 3);

        //TC01: the triangle small and just the ray from the middle pixel integrate
        Triangle triangle1 = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2));
        //assertEquals(findNumberOfIntersecions(camera1, triangle1), 1, "bad");

        //TC02: the triangle start across the middle pixel and continue to the top pixel
        Triangle triangle2 = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2));
        assertEquals(findNumberOfIntersecions(camera1, triangle2), 1, "bad");
    }
}
