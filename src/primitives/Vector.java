package primitives;

/**
 * A class representing a vector in three-dimensional space.
 */
public class Vector extends Point {

    /**
     * Constructs a new vector with the given x, y, and z components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @throws IllegalAccessException if the vector is zero
     */
    public Vector(double x, double y, double z) throws IllegalAccessException {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector Zero is not allowed");
        }
    }

    /**
     * Constructs a new vector from a Double3 object.
     *
     * @param vec3 the Double3 object representing the vector
     * @throws IllegalAccessException if the vector is zero
     */
    Vector(Double3 vec3) throws IllegalAccessException {
        this(vec3.d1, vec3.d2, vec3.d3);
    }

    /**
     * Returns the result of adding this vector to another vector.
     *
     * @param vector the other vector to add to this vector
     * @return the resulting vector
     * @throws IllegalAccessException if the resulting vector is zero
     */
    public Vector add(Vector vector) throws IllegalAccessException {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Returns the result of scaling this vector by a scalar value.
     *
     * @param rhs the scalar value to scale this vector by
     * @return the resulting vector
     * @throws IllegalAccessException if the resulting vector is zero
     */
    public Vector scale(double rhs) throws IllegalAccessException {
        return new Vector(xyz.scale(rhs));
    }

    /**
     * Returns the dot product of this vector and another vector.
     *
     * @param v the other vector
     * @return the dot product of this vector and the other vector
     */
    public double dotProduct(Vector v) {
        return ((this.xyz.d1 * v.xyz.d1) +
                (this.xyz.d2 * v.xyz.d2) +
                (this.xyz.d3 * v.xyz.d3));
    }

    /**
     * Returns the cross product of this vector and another vector.
     *
     * @param other the other vector
     * @return the cross product of this vector and the other vector
     * @throws IllegalAccessException if the resulting vector is zero
     */
    public Vector crossProduct(Vector other) throws IllegalAccessException {
        double x = this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2;
        double y = this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3;
        double z = this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Returns the square of the length of this vector.
     *
     * @return the square of the length of this vector
     */
    public double lengthSquared() {
        return (((this.xyz.d1) * (this.xyz.d1)) +
                ((this.xyz.d2) * (this.xyz.d2)) +
                ((this.xyz.d3) * (this.xyz.d3)));
    }

    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a normalized version of this vector.
     *
     * @return the normalized version of this vector
     * @throws IllegalAccessException if the resulting vector is zero
     */
    public Vector normalize() throws IllegalAccessException {
        return null;
    }
}
