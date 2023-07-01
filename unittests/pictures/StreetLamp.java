package pictures;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import lighting.PointLight;
import primitives.*;

public class StreetLamp {
    private static final int SHININESS = 301;

    private static final Double3 KD3 = new Double3(0.3, 0.8, 0.4);

    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
    Cylinder Base;
    Sphere lamp;
    PointLight light;
    Cylinder top;
    public StreetLamp(Point p, Vector normal) throws IllegalArgumentException {
        double sphereR=6d;
        double cylinderH=30d;
        Ray ray=new Ray(p,normal);
        Base=new Cylinder(ray,1d,cylinderH);
        Base=(Cylinder) Base.setEmission(new Color(0,0,0));
        Base=(Cylinder) Base.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Point nextP=p.add(new Vector(0,sphereR+cylinderH,0));
        lamp=new Sphere(sphereR, nextP);
        lamp=(Sphere)  lamp.setEmission(new Color(255,207,73));
        lamp=(Sphere)  lamp.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.5));
        light=new PointLight(new Color(800, 255, 255), nextP)
                .setkL(0.0001).setkQ(0.0002);
        ray=new Ray(nextP.add(new Vector(0,sphereR,0)),normal);
        top=new Cylinder(ray,1d,2d);
        top=(Cylinder) top.setEmission(new Color(0,0,0));
        top=(Cylinder) top.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

    }
    public Geometries getGeometries() {
        return new Geometries(Base,top,lamp);
    }
    public PointLight getLight() {
        return light;
    }




}
