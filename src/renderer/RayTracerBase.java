package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * A base class for ray tracing algorithms.
 */
public abstract class RayTracerBase {
    /**
     * The scene to be rendered.
     */
    protected Scene scene;

    /**
     * Constructs a new RayTracerBase object with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Computes the color of the given ray in the scene and returns it.
     *
     * @param ray the ray to be traced
     * @return the color of the ray in the scene
     * @throws IllegalAccessException if there is an error accessing the scene or objects in the scene
     */
    public abstract Color traceRay(Ray ray) throws IllegalAccessException;
}

