package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    public DirectionalLight(Color intensity, Vector direction) throws IllegalAccessException {
        super(intensity);
        this.direction = direction.normalize();
    }
    public Vector getL(Point p) {
        return direction;
    }
    public Color getIntensity(Point p){
        return getIntensity();
    }
    public double getDistance(Point point){
        return Double.POSITIVE_INFINITY;
    }

}
