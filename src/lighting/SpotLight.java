package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The SpotLight class represents a spotlight in the scene.
 * It extends the PointLight class.
 */
public class SpotLight extends PointLight {
    private Vector direction;

    /**
     * Constructs a SpotLight object with the specified intensity, position, and direction.
     *
     * @param intensity  The intensity of the spotlight.
     * @param position   The position of the spotlight.
     * @param direction  The direction of the spotlight.
     * @throws IllegalArgumentException if an illegal access exception occurs.
     */
    public SpotLight(Color intensity, Point position, Vector direction) throws IllegalArgumentException {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the spotlight at the specified point.
     * The intensity is determined by the basic intensity of the PointLight
     * multiplied by the dot product of the spotlight's direction and the direction to the point.
     *
     * @param p The point for which to calculate the intensity.
     * @return The intensity of the spotlight.
     * @throws IllegalArgumentException if an illegal access exception occurs.
     */
    public Color getIntensity(Point p) throws IllegalArgumentException {
        Color basic = super.getIntensity(p);
        double max = Math.max(0, direction.dotProduct(getL(p)));
        Color IL = basic.scale(max);
        return IL;
    }
}
