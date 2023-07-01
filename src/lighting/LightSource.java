package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface represents a light source in the scene.
 */
public interface LightSource {
    /**
     * Returns the distance between the light source and the specified point.
     *
     * @param point The point for which to calculate the distance.
     * @return The distance between the light source and the point.
     */
    double getDistance(Point point);

    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p The point for which to calculate the intensity.
     * @return The intensity of the light.
     * @throws IllegalArgumentException if an illegal access exception occurs.
     */
    Color getIntensity(Point p) throws IllegalArgumentException;

    /**
     * Returns the direction vector from the light source to the specified point.
     *
     * @param p The point for which to calculate the direction vector.
     * @return The direction vector from the light source to the point.
     * @throws IllegalArgumentException if an illegal access exception occurs.
     */
    Vector getL(Point p) throws IllegalArgumentException;
}





