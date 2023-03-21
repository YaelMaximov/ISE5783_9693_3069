package primitives;

import java.util.Objects;

public class Point {
    final Double3 xyz;
    public Point(double d1, double d2, double d3) {
        xyz = new Double3(d1, d2, d3);
    }

     Point(Double3 double3) {

        xyz = new Double3(double3.d1, double3.d2, double3.d3);
    }


    public Vector subtract(Point p1) throws IllegalAccessException {
        return new Vector(xyz.subtract(p1.xyz));
    }

    public Point add(Vector vector) throws IllegalAccessException {
        return new Point(xyz.add(vector.xyz));

    }
    public double distanceSquared(Point other){
        return (other.xyz.d1-xyz.d1)*(other.xyz.d1-xyz.d1)+
                (other.xyz.d2- xyz.d2)*(other.xyz.d2- xyz.d2)+
                (other.xyz.d2- xyz.d3)*(other.xyz.d2- xyz.d3);

    }
    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz);
        return false;
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
