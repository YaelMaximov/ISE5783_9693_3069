package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    Point position;
    private double kC=1;
    private double kL=0;
    private double kQ=0;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
    public Vector getL(Point p) throws IllegalAccessException {
        Vector L=p.subtract(position);
        return L;
    }
    public Color getIntensity(Point p) throws IllegalAccessException {
        double d=position.distance(p);
        double denominator=kC+kL*d+kQ*Math.pow(d,2);
        Color IL=getIntensity().scale(1/d);
        return IL;

    }

}
