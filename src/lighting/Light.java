package lighting;

import primitives.Color;
/**

 The Light class is an abstract class representing a light source in a lighting system.
 It provides a common interface for accessing the intensity of the light.
 */

abstract class Light {
    private Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity the color intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the color intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}