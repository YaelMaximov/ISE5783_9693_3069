package primitives;

/**
 * The Material class represents the material properties of an object in a 3D scene.
 * It defines various properties such as diffuse reflection, specular reflection, shininess, transparency, etc.
 */
public class Material {
    /**
     * Diffuse reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Shininess value.
     */
    public int nShininess = 0;

    /**
     * Reflection coefficient.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Transparency coefficient.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param kD The diffuse reflection coefficient.
     * @return The Material object for method chaining.
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param kD The diffuse reflection coefficient.
     * @return The Material object for method chaining.
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param kS The specular reflection coefficient.
     * @return The Material object for method chaining.
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param kS The specular reflection coefficient.
     * @return The Material object for method chaining.
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess value.
     *
     * @param nShininess The shininess value.
     * @return The Material object for method chaining.
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Sets the reflection coefficient.
     *
     * @param kR The reflection coefficient.
     * @return The Material object for method chaining.
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }
    public Material setkR(double kR) {
        this.kR =new Double3(kR);
        return this;
    }

    /**
     * Sets the transparency coefficient.
     *
     * @param kT The transparency coefficient.
     * @return The Material object for method chaining.
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
}
