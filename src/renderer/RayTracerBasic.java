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
     * @throws IllegalAccessException if there is an error accessing the scene or objects in the scene
     */
    @Override
    public Color traceRay(Ray ray) throws IllegalAccessException {
        // Find intersections of the ray with objects in the scene
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of the given point using the scene's ambient light.
     * @param gp the point to calculate the color for
     * @return the color of the point using the scene's ambient light
     */
//    private Color calcColor(GeoPoint gp,Ray ray) throws IllegalAccessException {
//        return   scene.ambientLight.getIntensity().add(gp.geometry.getEmission()).add(calcLocalEffects(gp,ray));
//    }
    private Color calcGlobalEffects(GeoPoint gp, Ray ray,
                                    int level, Double3 k) throws IllegalAccessException {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(constructReflectedRay(gp, v, n),level, k, material.kR)
                .add(calcColorGLobalEffect(constructRefractedRay(gp, v, n),level, k, material.kT));}
    private Color calcColorGLobalEffect(Ray ray, int level, Double3 k, Double3 kx) throws IllegalAccessException {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);

        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }
    private Color calcColor(GeoPoint intersection, Ray ray) throws IllegalAccessException {
        //non recursive
        return calcColor(intersection, ray,MAX_CALC_COLOR_LEVEL,new Double3(INITIAL_K)).
                add(scene.ambientLight.getIntensity());

    }

    private Color calcColor(GeoPoint intersection, Ray ray,int level, Double3 k) throws IllegalAccessException {
        //recursive-original method
        Color color = calcLocalEffects(intersection, ray);
        return 1 == level ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }










    private Color calcLocalEffects(Intersectable.GeoPoint gp, Ray ray) throws IllegalAccessException {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv ==0) {
            return color;
        }
        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            //* nv
            if (nl* nv >0) { // sign(nl) == sing(nv)
                if(unshaded(gp,l,n,lightSource)){
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));//check what is the effect of each of them
            }
          }
        }
        return color;
    }
    /**
     * Determines whether a given point is unshaded by a light source in the scene.
     * The point is considered unshaded if there are no intersections between the point and the light source.
     *
     * @param gp     The GeoPoint representing the point to be checked for shading.
     * @param l      The direction vector from the light source to the point.
     * @param n      The normal vector at the point.
     * @param light  The LightSource to check for shading.
     * @return       {@code true} if the point is unshaded by the light source, {@code false} otherwise.
     * @throws IllegalAccessException if an illegal access exception occurs during the calculation.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) throws IllegalAccessException {
        //if it is not transparent than check the shadows else -if it is transparent return that there is no shadow
         if (gp.geometry.getMaterial().kT== Double3.ZERO){
            // Compute the opposite direction of the light vector
            Vector lightDirection = l.scale(-1);

            // Calculate an epsilon vector to slightly move the point in the direction of the normal
            Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

            // Move the point slightly in the direction of the normal
            Point point = gp.point.add(epsVector);

            // Create a ray from the adjusted point towards the light source
            Ray lightRay = new Ray(point, lightDirection);

            // Find intersections between the light ray and the geometries in the scene
            List<Point> intersection = scene.geometries.findIntersections(lightRay);

            // If there are no intersections, the point is unshaded
            if (intersection == null)
                return true;

            // Check if any intersection point is closer to the light source than the current point
            for (Point point1 : intersection) {
                double d = point1.distance(lightRay.getP0());
                if (d < light.getDistance(point1))
                    return false;
            }

            // If no closer intersection point is found, the point is unshaded
            return true;
        }
      else{
            return true;
        }

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
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) throws IllegalAccessException {
        double nv = alignZero(n.dotProduct(v));
        Vector r=v.subtract(n.scale(nv*2)).normalize();
        // Compute the opposite direction of the light vector
        Vector rayDir = r.scale(-1);

        // Calculate an epsilon vector to slightly move the point in the direction of the normal
        Vector epsVector = n.scale(n.dotProduct(rayDir) > 0 ? DELTA : -DELTA);

        // Move the point slightly in the direction of the normal
        Point point = gp.point.add(epsVector);
        return  new Ray(point,r);


    }
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) throws IllegalAccessException {
        // Compute the opposite direction of the light vector
        Vector rayDir = v.scale(-1);

        // Calculate an epsilon vector to slightly move the point in the direction of the normal
        Vector epsVector = n.scale(n.dotProduct(rayDir) > 0 ? DELTA : -DELTA);

        // Move the point slightly in the direction of the normal
        Point point = gp.point.add(epsVector);

        return new Ray(point,v);
    }
    private GeoPoint findClosestIntersection(Ray ray) throws IllegalAccessException {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections==null)
            return null;
        if(intersections.size()==2)
        { GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return closestPoint;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return closestPoint;
    }



}

