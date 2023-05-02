package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoin() throws IllegalAccessException {
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(2, 2, 2));
        // ============ Equivalence Partitions Tests ==============
        List<Point> pointList = new ArrayList<Point>();
        pointList.add(new Point(3, 3, 3));
        pointList.add(new Point(1.5, 1.5, 1.5));
        pointList.add(new Point(6, 6, 6));
        assertEquals(new Point(1.5, 1.5, 1.5), ray.findClosestPoint(pointList), "The closest point is error");
        // =============== Boundary Values Tests ==================
        //T01: An empty list, there is no point close to the beginning of the ray
        List<Point> pointList1 = new ArrayList<Point>();
        assertNull(ray.findClosestPoint(pointList1), "The list is empty");
        //T02: The first point in the list is the one closest to the beginning of the ray
        List<Point> pointList2 = new ArrayList<Point>();
        pointList2.add(new Point(1.5, 1.5, 1.5));
        pointList2.add(new Point(3, 3, 3));
        pointList2.add(new Point(6, 6, 6));
        assertEquals(new Point(1.5, 1.5, 1.5), (ray.findClosestPoint(pointList2)), "The first point is min:error");
        //The first point in the list is equal to the beginning of the ray
        List<Point> pointList4 = new ArrayList<Point>();
        pointList4.add(new Point(1,1,1));
        pointList4.add(new Point(1.5,1.5,1.5));
        assertEquals(new Point(1,1,1), ray.findClosestPoint(pointList4),"The first point is beginning of the ray:error");
        //T0:03 The last point on the list is the one closest to the beginning of the ray
        List<Point>pointList3=new ArrayList<Point>();
        pointList3.add(new Point(6,6,6));
        pointList3.add(new Point(3,3,3));
        pointList3.add(new Point(1.5,1.5,1.5));
        assertEquals(new Point(1.5,1.5,1.5),ray.findClosestPoint(pointList3),"The last point is min:error");


    }
}