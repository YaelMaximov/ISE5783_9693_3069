package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
    /**
     *Adds the effects of the light on the color
     * @param p Intersections point of light in a geometric body
     * @return Color after adding the light effect
     * @throws IllegalAccessException
     */
    public Color getIntensity(Point p) throws IllegalAccessException;
    /**
     * Vector direction of light from the point of light to refraction in a geometric body
     * @param p Intersections point of light in a geometric body
     * @return The direction vector from the point of light to the cut
     * @throws IllegalAccessException
     */
    public Vector getL(Point p) throws IllegalAccessException;




}
