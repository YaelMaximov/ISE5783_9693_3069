package pictures;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;

public class SpotStreetLight {
    private static final int SHININESS = 301;

    private static final Double3 KD3 = new Double3(0.3, 0.8, 0.4);

    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
    Cylinder Base;
    Cylinder lamp;
    PointLight light;
    Cylinder top;
    public  SpotStreetLight(Point p, Vector normal) throws IllegalArgumentException {
        double baseH=30d;
        double lampH=10d;
        Ray ray=new Ray(p,normal);
        Base=new Cylinder(ray,1d,baseH);
        Base=(Cylinder) Base.setEmission(new Color(0,0,0));
        Base=(Cylinder) Base.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        Point nextP=p.add(new Vector(0,baseH,0));
        ray=new Ray(nextP,normal);
        lamp=new Cylinder(ray,5d,lampH);
        lamp=(Cylinder)  lamp.setEmission(new Color(255,207,73));
        lamp=(Cylinder)  lamp.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.5));
        nextP=nextP.add(new Vector(0,lampH/2,0));
        light=new SpotLight(new Color(800, 255, 255), nextP,new Vector(0,-1,0))
                .setkL(0.0001).setkQ(0.0002);
        ray=new Ray(nextP.add(new Vector(0,lampH/2,0)),normal);
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
