package pictures;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.WHITE;

public class snowMan {
    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private Scene scene1 = new Scene("front");
    private final Color sphereLightColor = new Color(800, 500, 0);
    private final Color sphereColor = new Color(WHITE).reduce(2);

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
    private final Geometry sphere1 = new Sphere(16d, new Point(0,-10 , -50))
            .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry sphere2 = new Sphere(20d,new Point(0, -35, -50))
            .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry sphere3 = new Sphere(13d,new Point(0, 13, -50))
            .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry rEye = new Sphere(2d,new Point(5, 14, -37))
            .setEmission(new Color(102,51,0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry lEye = new Sphere(2d,new Point(-5, 14, -37))
            .setEmission(new Color(102,51,0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));

    private final Geometry button1 = new Sphere(2d,new Point(0, -5, -34))
            .setEmission(new Color(102,51,0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry button2 = new Sphere(2d,new Point(0, -15, -34))
            .setEmission(new Color(102,51,0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry button3 = new Sphere(2d,new Point(0, -27, -30))
            .setEmission(new Color(102,51,0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));

    private final Geometry sky =new Plane(new Point(1500, -1500, -800), new Point(-1500, 1500, -800),
                                        new Point(670, 670, -800)).setEmission(new Color(30,112,146));
    //new Color(143,211,240)
    private final Geometry floor =new Plane(new Point(1500, -50, -1500), new Point(-1500, -50, -1500),
            new Point(670, -50, 3000)).setEmission(new Color(217,239,249)).setEmission(new Color(20, 20, 20)) //
            .setMaterial(new Material().setkR(1));

    public snowMan() throws IllegalAccessException {
    }

    @Test
    public void front() throws IllegalAccessException {

       //scene1.geometries.add(sphere1,sphere2,sphere3,rEye,lEye,button1,button2,button3,sky,floor);
        scene1.geometries.add(sky,floor);
        //scene1.lights.add(new DirectionalLight(new Color(500, 200, 100), new Vector(1, -1, -0.5)));//purple
        //scene1.lights.add(new PointLight(new Color(500, 200, 100), new Point(70, 70, -800)).setkL(0.01).setkQ(0.0002));//yellow
       //scene1.lights.add(new SpotLight(new Color(500, 200, 100), new Point(60, 80, -760), new Vector(-1, -0.5, -0.5))
               //.setkL(0.01).setkQ(0.000001));//green-blue

        scene1.lights.add(new PointLight(new Color(800,100,300), new Point(70, 70, -800))
                .setkL(0.001).setkQ(0.0002));

        ImageWriter imageWriter = new ImageWriter("front", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }
    @Test
    public void side() throws IllegalAccessException {

        scene1.geometries.add(sphere1,sphere2,sphere3,rEye,lEye,button1,button2,button3,sky,floor);
        //scene1.lights.add(new DirectionalLight(new Color(500, 200, 100), new Vector(1, 0, 0.5)));//purple
        scene1.lights.add(new PointLight(new Color(255, 255, 255), new Point(5, 20, -50)).setkL(0.001).setkQ(0.0002));//yellow
        //scene1.lights.add(new SpotLight(new Color(300, 800, 0), new Point(90, 80, 25), new Vector(-1, -1, -0.5))
        //  .setkL(0.01).setkQ(0.0001));//green-blue

        ImageWriter imageWriter = new ImageWriter("side", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }
    @Test
    public void halfSide() throws IllegalAccessException {

        scene1.geometries.add(sphere1,sphere2,sphere3,rEye,lEye,button1,button2,button3,sky,floor);
        //scene1.lights.add(new DirectionalLight(new Color(500, 200, 100), new Vector(1, 0, 0.5)));//purple
        scene1.lights.add(new PointLight(new Color(255, 255, 255), new Point(80, -60, 25)).setkL(0.001).setkQ(0.0002));//yellow
        //scene1.lights.add(new SpotLight(new Color(300, 800, 0), new Point(90, 80, 25), new Vector(-1, -1, -0.5))
        //  .setkL(0.01).setkQ(0.0001));//green-blue

        ImageWriter imageWriter = new ImageWriter("half-side", 500, 500);
        camera3.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }
}
