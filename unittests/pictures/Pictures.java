package pictures;

import geometries.Cylinder;
import geometries.Geometry;
import geometries.Plane;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class Pictures {

    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private Scene scene1 = new Scene("front");
    private final Color sphereLightColor = new Color(800, 500, 0);

    private final Camera camera1 = new Camera(new Point(0, 0, 1500),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);//front
    private final Camera camera2 = new Camera(new Point(2000, 0, -520),
            new Vector(0, 1, 0), new Vector(-1, 0, 0))
            .setVPSize(150, 150).setVPDistance(1000);//side

//    private final Camera camera4 = new Camera(new Point(1600, 0, -520),
//            new Vector(0, 1, 0), new Vector(1, 0, 0))
//            .setVPSize(150, 150).setVPDistance(1000);//side
    private final Camera camera3 = new Camera(new Point(2000, 0, 1500),
            new Vector(0, 1, 0), new Vector(-1, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);//half-side

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);

    private static final Color skyColor =new Color(44,56,144);
    private final Material skyMaterial = new Material().setkR(0.3);
    private final Material waterMaterial = new Material().setkT(0.7).setkR(0.3);
    private final Geometry sky1 =new Plane(new Point(-150, 50, -3000), new Point(-150, 250, -3000), new Point(150, 250, -3000))
            .setEmission(skyColor).setMaterial(material);
    private final Geometry sky2 =new Plane(new Point(-3000, 50,-150), new Point(-3000, 250, -3000), new Point(-3000,250, -60))
            .setEmission(skyColor).setMaterial(material);
    private final Geometry sky3 =new Plane(new Point(3000, 50,-150), new Point(3000, 250, -3000), new Point(3000,250, -60))
            .setEmission(skyColor).setMaterial(material);
    Ray ray1=new Ray(new Point(80,230,-2998),new Vector(0,0,1));

    private final Geometry moon=new Cylinder(ray1,20d,1d).setEmission(new Color(255,255,255));

    Ray ray2=new Ray(new Point(90,230,-2998),new Vector(0,0,1));

    private final Geometry shadow=new Cylinder(ray2,20d,1d).setEmission(skyColor);


    //new Color(143,211,240)
    private final Geometry floor =new Plane(new Point(1500, -100, -1500), new Point(-1500, -100, -1500),
            new Point(670, -100, 3000)).setEmission(new Color(39,45,74))//
            .setMaterial(waterMaterial);
//    private final Geometry water =new Plane(new Point(1500, -105, -1500), new Point(-1500, -100, -1500),
//            new Point(670, -100, 3000)).setEmission(new Color(102,169,162))//
//            .setMaterial(waterMaterial);
    private final Cube floor1 =new Cube(new Point(-1200, -100, -2400),2100,5).setCubeEmission(new Color(39,45,100)).setCubeMaterial(material);

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
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(200, 200, -10))
                .setkL(0.0001).setkQ(0.00002));

        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(240, 240, -2998))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(150,150, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-40, 250, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-150, 100, -2998))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-230, 200, -2998))
                .setkL(0.0001).setkQ(0.0002));

        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 240, -700))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998,150, -650))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 250, -500))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 100, -430))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 200, -380))
                .setkL(0.0001).setkQ(0.0002));

        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 240, -700))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998,150, -650))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 250, -500))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 100, -430))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 200, -380))
                .setkL(0.0001).setkQ(0.0002));

    }

    public void  setupBuilding(Scene scene) throws IllegalAccessException{
        Point p=new Point(-10, -95, -480);
        Building b=new Building(p,30,100);
        scene.geometries.add(b.getGeometries(),sky1,sky2,sky3,floor,floor1.getGeometries(),shadow,moon);

    }




    public Pictures() throws IllegalAccessException {
    }

    @Test
    public void front() throws IllegalAccessException {
        setupBuilding(scene1);
        setupLights(scene1);
        setupCamera(camera1,"front");


    }
    @Test
    public void side() throws IllegalAccessException {
        setupBuilding(scene1);
        setupLights(scene1);
        setupCamera(camera2,"side");
    }
//    @Test
//    public void otherside() throws IllegalAccessException {
//        setupGeometries(scene1);
//        setupLights(scene1);
//        setupCamera(camera4,"side2");
//    }
    @Test
    public void halfSide() throws IllegalAccessException {
        setupBuilding(scene1);
        setupLights(scene1);
        setupCamera(camera3,"half_side");
    }
}
