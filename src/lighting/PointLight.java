package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source in the scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity The intensity of the light.
     * @param position  The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor of the light source.
     *
     * @param kC The constant attenuation factor to set.
     * @return The current PointLight object.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor of the light source.
     *
     * @param kL The linear attenuation factor to set.
     * @return The current PointLight object.
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor of the light source.
     *
     * @param kQ The quadratic attenuation factor to set.
     * @return The current PointLight object.
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Returns the direction vector from the light source to the specified point.
     *
     * @param p The point for which to calculate the direction vector.
     * @return The direction vector from the light source to the point.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Vector getL(Point p) throws IllegalAccessException {
        Vector L = p.subtract(position);
        return L.normalize();
    }

    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p The point for which to calculate the intensity.
     * @return The intensity of the light.
     * @throws IllegalAccessException if an illegal access exception occurs.
     */
    public Color getIntensity(Point p) throws IllegalAccessException {
        double d = position.distance(p);
        double denominator = kC + kL * d + kQ * Math.pow(d, 2);
        Color IL = getIntensity().scale(1 / denominator);
        return IL;
    }

    /**
     * Returns the distance between the light source and the specified point.
     *
     * @param point The point for which to calculate the distance.
     * @return The distance between the light source and the point.
     */
    public double getDistance(Point point) {
        return point.distance(position);
    }
}
