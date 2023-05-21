package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) throws IllegalAccessException {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    public Color getIntensity(Point p) throws IllegalAccessException {
        Color basic=super.getIntensity(p);
        double max=Math.max(0,direction.dotProduct(getL(p)));
        Color IL=basic.scale(max);
        return IL;
    }
}
