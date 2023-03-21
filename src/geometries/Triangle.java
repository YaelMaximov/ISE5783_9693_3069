package geometries;

import primitives.Point;

/**
 * The Triangle class represents a triangle in 3D space.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the given vertices.
     *
     * @param vertices the vertices of the triangle
     * @throws IllegalAccessException if less than 3 vertices are given
     */
    public Triangle(Point... vertices) throws IllegalAccessException {
        super(vertices);
    }

}
