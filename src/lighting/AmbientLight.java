package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents an ambient light in a scene.
 */
public class AmbientLight {
    /**
     * A color representing no ambient light.
     */
    public static final Color NONE = Color.BLACK;

    /**
     * A vector representing the ambient reflection coefficient for each color component (red, green, and blue).
     */
    public static final Double3 KA0 = Double3.ZERO;

    private Color intensity;

    /**
     * Constructs a new AmbientLight object with the given intensity and ambient reflection coefficient vector.
     *
     * @param IA the intensity of the ambient light
     * @param KA the ambient reflection coefficient vector
     */
    public AmbientLight(Color IA, Double3 KA) {
        this.intensity = IA.scale(KA);
    }

    /**
     * Constructs a new AmbientLight object with the given intensity and ambient reflection coefficient.
     *
     * @param IA the intensity of the ambient light
     * @param KA the ambient reflection coefficient
     */
    public AmbientLight(Color IA, double KA) {
        this.intensity = IA.scale(KA);
    }

    /**
     * Returns the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}