package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in the scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity The intensity of the light.
     * @param direction The direction of the light.
     * @throws IllegalArgumentException if an illegal access exception occurs.
     */
    public DirectionalLight(Color intensity, Vector direction) throws IllegalArgumentException {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the direction vector of the light.
     *
     * @param p The point for which to calculate the direction vector.
     * @return The direction vector of the light.
     */
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p The point for which to calculate the intensity.
     * @return The intensity of the light.
     */
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    /**
     * Returns the distance between the light source and the specified point.
     *
     * @param point The point for which to calculate the distance.
     * @return The distance between the light source and the point (always positive infinity for directional lights).
     */
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
