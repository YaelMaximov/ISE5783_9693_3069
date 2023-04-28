import geometries.Geometry;
import geometries.Sphere;
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
    @Test
    void CameraPlaneIntersections() {
    }
    @Test
    void CameraTriangleIntersections() {
    }
}
