package pictures;

import geometries.Cylinder;
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

import static java.awt.Color.BLUE;

public class Pictures {

    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private Scene scene1 = new Scene("front");
    private final Color sphereLightColor = new Color(800, 500, 0);

    private final Camera camera1 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);//front
    private final Camera camera2 = new Camera(new Point(1000, 0, -50),
            new Vector(0, 1, 0), new Vector(-1, 0, 0))
            .setVPSize(150, 150).setVPDistance(1000);//side
    private final Camera camera3 = new Camera(new Point(1100, 0, 1000),
            new Vector(0, 1, 0), new Vector(-1, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);//half-side

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);


    private final Geometry sky1 =new Plane(new Point(-150, 50, -3000), new Point(-150, 250, -3000), new Point(150, 250, -3000))
            .setEmission(new Color(17,45,81)).setMaterial(material);
    private final Geometry sky2 =new Plane(new Point(-3000, 50,-150), new Point(-3000, 250, -3000), new Point(-3000,250, -60))
            .setEmission(new Color(17,45,81)).setMaterial(material);
    private final Geometry sky3 =new Plane(new Point(3000, 50,-150), new Point(3000, 250, -3000), new Point(3000,250, -60))
            .setEmission(new Color(17,45,81)).setMaterial(material);
    private final Geometry streetLampC = new Cylinder(new Ray(new Point(45,-35,-20),new Vector(0,1,0)),1,30).setEmission(new Color(255,255,255));
    private final Geometry moon1 = new Sphere(20, new Point(80,230,-2998)).setEmission(new Color(255,255,255));
    private final Geometry moon2 = new Sphere(20, new Point(90,230,-2998)).setEmission(new Color(17,45,81));
    private final Geometry s = new Sphere(5d, new Point(45,0,-20)).setEmission(new Color(255,255,255)) //
            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30));

    //new Color(143,211,240)
    private final Geometry floor =new Plane(new Point(1500, -100, -1500), new Point(-1500, -100, -1500),
            new Point(670, -100, 3000)).setEmission(new Color(128,128,128))//
            .setMaterial(material);
    public void  setupCamera(Camera camera,String fileName) throws IllegalAccessException
    {
        ImageWriter imageWriter = new ImageWriter(fileName, 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage();
    }
    public void  setupLights(Scene scene) throws IllegalAccessException
    {
        //scene.lights.add(new DirectionalLight(sphereLightColor,new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(200, 200, 30))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(240, 240, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(150,150, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-40, 250, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-150, 100, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-230, 200, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-235, 200, -2998))
                .setkL(0.0001).setkQ(0.0002));
        //scene.lights.add(new PointLight(new Color(800, 255, 255),new Point(45,0,-20))
          //      .setkL(0.0001).setkQ(0.0002));
        //scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(45,0,-20), new Vector(0, 0, -1)) //
          //      .setkL(4E-5).setkQ(2E-7));
        scene.lights.add( //
                new PointLight(new Color(255,255,255), new Point(40, 0,-20)) //
                        .setkL(0.01).setkQ(2E-5));

    }
    public void  setupGeometries(Scene scene) throws IllegalAccessException
    {
        double d1=-10;
        double d2=-50;
        double d3=-130;
        double x=27d;
        double y=30d;
        double z=27d;
        double l=x-0.2*x;
        Point p1=new Point(d1, d2, d3);
        Point p2=new Point(d1-x*0.1, d2+70d, d3-z*0.1);
        Point p3=new Point(d1+x*0.1,d2+70d-x,d3+z);

        Base base=new Base(p1,z,x,40d,0.8).setBaseEmission(new Color(232,194,128)).setBaseMaterial(material);
        Cube building=new Cube(p1.add(new Vector(0,40d,0)),z,x,y).setCubeEmission(new Color(232,194,128)).setCubeMaterial(material);
        Pyramid p=new Pyramid(p2,z+z*0.2,x+x*0.2,30d).setPyramidEmission(new Color(126,147,229)).setPyramidMaterial(material);
        Clock clock=new Clock(p3,l).setClockEmission(new Color(255,255,255),new Color(218,178,86)).setClockMaterial(material);

        scene1.geometries.add(clock.getGeometries(),p.getGeometries(),base.getGeometries(),building.getGeometries(),sky1,sky2,floor,s,streetLampC,moon1,moon2);
    }




    public Pictures() throws IllegalAccessException {
    }

    @Test
    public void front() throws IllegalAccessException {
        setupGeometries(scene1);
        setupLights(scene1);
        setupCamera(camera1,"front");


    }
    @Test
    public void side() throws IllegalAccessException {
        setupGeometries(scene1);
        setupLights(scene1);
        setupCamera(camera2,"side");
    }
    @Test
    public void halfSide() throws IllegalAccessException {
        setupGeometries(scene1);
        setupLights(scene1);
        setupCamera(camera3,"half_side");
    }
}
