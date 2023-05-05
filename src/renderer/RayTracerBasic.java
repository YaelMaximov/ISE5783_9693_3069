package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * A basic implementation of a RayTracer.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a new RayTracerBasic object with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Computes the color of the given ray in the scene and returns it.
     *
     * @param ray the ray to be traced
     * @return the color of the ray in the scene
     * @throws IllegalAccessException if there is an error accessing the scene or objects in the scene
     */
    @Override
    public Color traceRay(Ray ray) throws IllegalAccessException {
        // Find intersections of the ray with objects in the scene
        List<Point> list = scene.geometries.findIntsersections(ray);
        if (list == null) {
            // If there are no intersections, return the background color of the scene
            return scene.background;
        }
        // Find the closest intersection point
        Point closestPoint = ray.findClosestPoint(list);
        // Calculate the color of the point using the scene's ambient light
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color of the given point using the scene's ambient light.
     *
     * @param point the point to calculate the color for
     * @return the color of the point using the scene's ambient light
     */
    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }

}

