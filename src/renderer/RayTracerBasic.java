package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {


    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) throws IllegalAccessException {
        List<Point> list = scene.geometries.findIntsersections(ray);
        if (list == null) {
            return scene.background;
        }
        Point closesPoint = ray.findClosestPoint(list);
        return calcColor(closesPoint);
    }

    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }

}
