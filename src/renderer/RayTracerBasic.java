package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;


import java.util.List;

import static primitives.Util.alignZero;

/**
 * A basic implementation of a RayTracer.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;


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
        List<Intersectable.GeoPoint> list = scene.geometries.findGeoIntersections(ray);
        if (list == null) {
            // If there are no intersections, return the background color of the scene
            return scene.background;
        }
        // Find the closest intersection point
        Intersectable.GeoPoint closestPoint = ray.findClosestGeoPoint(list);
        // Calculate the color of the point using the scene's ambient light
        return calcColor(closestPoint,ray);
    }

    private Color calcColor(GeoPoint closesPoint, Ray ray) throws IllegalAccessException {
        // point.geometry.setEmission(scene.ambientLight.getIntensity());
        return scene.ambientLight.getIntensity().add(closesPoint.geometry.getEmission()).add(calcLocalEffects(closesPoint, ray));
    }
    private Double3 calcDiffusive(Material material,double nl){
        //kd*|l*n|
        return material.kD.scale(Math.abs(nl));
    }
    private Double3 calcSpecular(Material material,Vector n,Vector l,double nl,Vector v) throws IllegalAccessException {
        //r = l − 2 ∙ (l ∙ n )∙ n
        Vector r=l.subtract(n.scale(nl*2)).normalize();//stoped here
        //ks*(max(0,-v*r))^nsh)
        return material.kS.scale(Math.pow(Math.max(0,v.scale(-1).dotProduct(r)),material.nShininess));
    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray) throws IllegalAccessException {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir().normalize();
        Vector n = gp.geometry.getNormal(gp.point).normalize();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point).normalize();
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if ((unshaded( lightSource,l, n,gp))) {
                    Color iL = lightSource.getIntensity(gp.point);
                    try {
                        color = color.add(iL.scale(calcDiffusive(mat, nl)),
                                iL.scale(calcSpecular(mat, n, l, nl, v)));
                    } catch (Exception e) {
                        color = color.add(iL.scale(calcDiffusive(mat, nl).scale(-1.d)),
                                iL.scale(calcSpecular(mat, n, l, nl, v)));
                    }
                }
            }

        }
        return color;
    }



    private boolean unshaded(LightSource light,Vector l, Vector n, GeoPoint gp) throws IllegalAccessException {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay/*, gp.point.distance(lightRay.getP0())*/);
                                                                                                          if (intersections == null) return true;
        for (GeoPoint geo : intersections) {
            double d = geo.point.distance(lightRay.getP0());
            if (d > light.getDistance(geo.point))
                //if (gp.geometry.getMaterial().kT.equals(0))
                    return false;
        }
        return true;

    }


}
