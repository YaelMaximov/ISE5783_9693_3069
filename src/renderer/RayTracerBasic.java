package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A basic implementation of a RayTracer.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double INITIAL_K = 1.0;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    @Override
    public Color traceRay(Ray ray) throws IllegalArgumentException {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of the given point using the scene's ambient light.
     *
     * @param gp the point to calculate the color for
     * @return the color of the point using the scene's ambient light
     */
    private Color calcColor(GeoPoint gp, Ray ray) throws IllegalArgumentException {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K))
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of the given point using local effects (diffusion and specular reflection) and global effects (reflection and refraction).
     *
     * @param gp    the point to calculate the color for
     * @param ray   the ray that reached the point
     * @param level the current recursion level
     * @param k     the accumulated attenuation factor
     * @return the color of the point with all the effects applied
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) throws IllegalArgumentException {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the color of the given point using local effects (diffusion and specular reflection).
     *
     * @param gp the point to calculate the color for
     * @param ray the ray that reached the point
     * @param k the accumulated attenuation factor
     * @return the color of the point with local effects applied
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) throws IllegalArgumentException {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return color;
        }
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (new Double3(MIN_CALC_COLOR_K).lowerThan(ktr.product(k))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v))
                    );
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffusion effect for the given material and light direction.
     *
     * @param material the material of the geometry
     * @param nl       the dot product between the normal and light direction
     * @return the diffusion effect color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection effect for the given material, normal, light direction, and viewer direction.
     *
     * @param material the material of the geometry
     * @param n        the normal at the point
     * @param l        the direction from the point to the light source
     * @param nl       the dot product between the normal and light direction
     * @param v        the direction from the point to the viewer
     * @return the specular reflection effect color
     * @throws IllegalArgumentException if the viewer direction is not normalized
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) throws IllegalArgumentException {
        Vector r = l.subtract(n.scale(nl * 2)).normalize();
        return material.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.nShininess));
    }

    /**
     * Calculates the global effects (reflection and refraction) for the given point.
     *
     * @param gp    the point to calculate the effects for
     * @param ray   the ray that reached the point
     * @param level the current recursion level
     * @param k     the accumulated attenuation factor
     * @return the color of the point with global effects applied
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) throws IllegalArgumentException {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR)
                .add(calcColorGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
    }

    /**
     * Calculates the global effect (reflection or refraction) for the given ray.
     *
     * @param ray   the ray to calculate the effect for
     * @param level the current recursion level
     * @param k     the accumulated attenuation factor
     * @param kx    the attenuation factor for the effect
     * @return the color of the ray with the global effect applied
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) throws IllegalArgumentException {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) {
            return scene.background.scale(kx);
        }
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Calculates the transparency factor for the given point, light source, light direction, and normal.
     *
     * @param geoPoint     the point to calculate the transparency for
     * @param lightSource  the light source
     * @param l            the direction from the point to the light source
     * @param n            the normal at the point
     * @return the transparency factor
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) throws IllegalArgumentException {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        Double3 ktr = Double3.ONE;
        if (intersections == null) {
            return ktr;
        }

        double distance = lightSource.getDistance(geoPoint.point);

        for (GeoPoint intersection : intersections) {
            if (distance > intersection.point.distance(geoPoint.point)) {
                ktr = ktr.product(intersection.geometry.getMaterial().kT);
            }
        }
        return ktr;
    }

    /**
     * Constructs a reflected ray from the given point, viewer direction, and normal.
     *
     * @param gp the point to construct the reflected ray from
     * @param v  the viewer direction
     * @param n  the normal at the point
     * @return the reflected ray
     * @throws IllegalArgumentException if the viewer direction is not normalized
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) throws IllegalArgumentException {
        double nv = alignZero(n.dotProduct(v));
        Vector r = v.subtract(n.scale(nv * 2)).normalize();
        return new Ray(gp.point, r, gp.geometry.getNormal(gp.point));
    }

    /**
     * Constructs a refracted ray from the given point, viewer direction, and normal.
     *
     * @param gp the point to construct the refracted ray from
     * @param v  the viewer direction
     * @param n  the normal at the point
     * @return the refracted ray
     * @throws IllegalArgumentException if the viewer direction is not normalized
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) throws IllegalArgumentException {
        return new Ray(gp.point, v, gp.geometry.getNormal(gp.point));
    }

    /**
     * Finds the closest intersection of the given ray with the objects in the scene.
     *
     * @param ray the ray to find the closest intersection for
     * @return the closest intersection point
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private GeoPoint findClosestIntersection(Ray ray) throws IllegalArgumentException {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }
}
