package primitives;

import java.util.Objects;

/**
 * Represents a point in 3D space with x, y, and z coordinates.
 */
public class Point {

    /**
     * The x, y, and z coordinates of the point.
     */
    protected final Double3 xyz;

    /**
     * Constructs a new point with the given x, y, and z coordinates.
     * @param d1 the x coordinate of the point
     * @param d2 the y coordinate of the point
     * @param d3 the z coordinate of the point
     */
    public Point(double d1, double d2, double d3) {
        xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructs a new point from a Double3 object.
     * @param double3 the Double3 object containing the x, y, and z coordinates of the point
     */
    public Point(Double3 double3) {
        xyz = new Double3(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Computes and returns the vector from this point to another point.
     * @param p1 the other point to compute the vector to
     * @return the vector from this point to the other point
     * @throws IllegalAccessException if the resulting vector is a zero vector
     */
    public Vector subtract(Point p1) throws IllegalAccessException {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * Computes and returns the point resulting from adding a vector to this point.
     * @param vector the vector to add to this point
     * @return the point resulting from adding the vector to this point
     * @throws IllegalAccessException if the resulting vector is a zero vector
     */
    public Point add(Vector vector) throws IllegalAccessException {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Computes and returns the squared distance between this point and another point.
     * @param other the other point to compute the squared distance to
     * @return the squared distance between this point and the other point
     */
    public double distanceSquared(Point other){
        return (other.xyz.d1-xyz.d1)*(other.xyz.d1-xyz.d1)+
                (other.xyz.d2- xyz.d2)*(other.xyz.d2- xyz.d2)+
                (other.xyz.d3- xyz.d3)*(other.xyz.d3- xyz.d3);
    }

    /**
     * Computes and returns the distance between this point and another point.
     * @param other the other point to compute the distance to
     * @return the distance between this point and the other point
     */
    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }
    /**
     * get X axis coordinate of a point
     * @return X axis coordinate - (double)
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * get Y axis coordinate of a point
     * @return Y axis coordinate - (double)
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * get Z axis coordinate of a point
     * @return Z axis coordinate - (double)
     */
    public double getZ() { return xyz.d3; }

    /**
     * Compares this point to another object for equality.
     * @param obj the object to compare this point to
     * @return true if the object is a Point with the same x, y, and z coordinates as this point, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz);
        return false;
    }

    /**
     * Returns a string representation of this point.
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
