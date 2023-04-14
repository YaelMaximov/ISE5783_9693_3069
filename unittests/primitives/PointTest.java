package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//bbj
class PointTest {

    @Test
    public void testAdd() throws IllegalAccessException {
        Vector vector=new Vector(1,2,3);
        Vector vector1= new Vector(2,1,1);
        Vector vector2 = new Vector(3,3,4);
        assertEquals(vector.add(vector1),vector2,"ERROR");

    }
    @Test
    void testSubtract() throws IllegalAccessException {
        Vector vector= new Vector(3,3,3);
        Point point=new Point(1,1,1);
        Vector vector2= new Vector(2,2,2);
        assertEquals(vector.subtract(point),vector2,"ERROR");

    }

    @Test
    void testDistance() {
        Point point= new Point(2,0,3);
        Point point1= new Point(0,2,2);
        assertEquals(point.distance(point1),3,"ERROR");
    }

    @Test
    void testDistanceSquared() {
        Point point= new Point(2,0,3);
        Point point1= new Point(0,2,2);
        assertEquals(point.distanceSquared(point1),9,"ERROR");
    }
}