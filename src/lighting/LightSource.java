package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
    public Color getIntensity(Point p) throws IllegalAccessException;
    public Vector getL(Point p) throws IllegalAccessException;




}
