package pictures;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.lang.reflect.ParameterizedType;


public class StreetLamp {
    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private Scene scene1 = new Scene("front");
    private final Color sphereLightColor = new Color(800, 500, 0);


    private final Point sphereCenter = new Point(0, 0, -50);
    private static final double SPHERE_RADIUS1 = 16d;
    private static final double SPHERE_RADIUS2 = 20d;

    private static final double SPHERE_RADIUS3 = 13d;
    private final Camera camera1 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);//front
    private final Camera camera2 = new Camera(new Point(1000, 0, -50),
            new Vector(0, 1, 0), new Vector(-1, 0, 0))
            .setVPSize(150, 150).setVPDistance(1000);//side
    private final Camera camera3 = new Camera(new Point(1000, 0, 1000),
            new Vector(0, 1, 0), new Vector(-1, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);//half-side

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);


    private final Geometry sky =new Plane(new Point(-150, 50, -800), new Point(-150, 250, -800), new Point(150, 250, -800))
            .setEmission(new Color(0,0,0)).setMaterial(material);
    //new Color(143,211,240)
    private final Geometry floor =new Plane(new Point(1500, -50, -1500), new Point(-1500, -50, -1500),
            new Point(670, -50, 3000)).setEmission(new Color(217,239,249)).setEmission(new Color(20, 20, 20)) //
            .setMaterial(new Material().setkR(1));
    private final Geometry streetLampC = new Cylinder(new Ray(new Point(56,-10,0),new Vector(0,1,0)),1,30).setEmission(new Color(255,255,255));
    private final Geometry streetLampS = new Sphere(5, new Point(56,25,0)).setEmission(new Color(255,255,255));


    public StreetLamp() throws IllegalAccessException {
    }

    @Test
    public void front() throws IllegalAccessException {
        Point p1=new Point(0, -10, -50);
        Cube building=new Cube(p1,15d,15d,30d).setCubeEmission(new Color(12,12,200));
        scene1.geometries.add(sky,streetLampC,streetLampS);
        //scene1.lights.add(new DirectionalLight(new Color(255,255,255), new Vector(1, -1, -0.5)));//purple
        //scene1.lights.add(new DirectionalLight(new Color(500, 0, 0), new Vector(0, -1, -0.5)));
        //scene1.lights.add(new PointLight(new Color(255, 255, 255), new Point(56, 25, 0)));//red-purple
        scene1.lights.add(new SpotLight(new Color(255,255,255),new Point(56,25,0),new Vector(-1,-1,-1)));
        //scene1.lights.add(new SpotLight(new Color(500, 0, 100), new Point(0, 60, -760), new Vector(0, 1, -0.5))
        // .setkL(0.01).setkQ(0.000001));//green-blue



        ImageWriter imageWriter = new ImageWriter("front", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }
}
