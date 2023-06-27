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
    private Scene scene = new Scene("front");
    private final Color sphereLightColor = new Color(800, 500, 0);

    private final Camera camera1 = new Camera(new Point(0, 0, 1500),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000).setNss(9).setThreadsCount(4);//front
    private final Camera camera2 = new Camera(new Point(2000, 0, -520),
            new Vector(0, 1, 0), new Vector(-1, 0, 0))
            .setVPSize(150, 150).setVPDistance(1000).setNss(300).setThreadsCount(4);//side

//    private final Camera camera4 = new Camera(new Point(1600, 0, -520),
//            new Vector(0, 1, 0), new Vector(1, 0, 0))
//            .setVPSize(150, 150).setVPDistance(1000);//side
    private final Camera camera3 = new Camera(new Point(2000, 0, 1500),
            new Vector(0, 1, 0), new Vector(-1, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000).setNss(300).setThreadsCount(4);//half-side

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);

    private final Material skyMaterial = new Material().setkR(0.3);
    private final Material waterMaterial = new Material().setkT(0.7).setkR(0.3);




    //new Color(143,211,240)
    private final Geometry floor =new Plane(new Point(1500, -100, -1500), new Point(-1500, -100, -1500),
            new Point(670, -100, 3000)).setEmission(new Color(39,45,74))//
            .setMaterial(waterMaterial);
    private final Geometry water =new Plane(new Point(1500, -105, -1500), new Point(-1500, -100, -1500),
            new Point(670, -100, 3000)).setEmission(new Color(25,49,145))//
            .setMaterial(waterMaterial);
    private final Cube floor1 =new Cube(new Point(-2400, -100, -3600),3300,5).setCubeEmission(new Color(39,45,100)).setCubeMaterial(material);

    public void  setupCamera(Camera camera,String fileName) throws IllegalAccessException
    {
        ImageWriter imageWriter = new ImageWriter(fileName, 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
    public void  setupLights() throws IllegalAccessException
    {

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

        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 240, -750))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998,150, -650))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 250, -500))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 100, -430))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(-2998, 200, -320))
                .setkL(0.0001).setkQ(0.0002));

        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 240, -750))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998,150, -650))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 250, -500))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 100, -430))
                .setkL(0.0001).setkQ(0.0002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(2998, 200, -320))
                .setkL(0.0001).setkQ(0.0002));

    }

    public void  setupBuilding() throws IllegalAccessException{
        Point p=new Point(60,-95,-460);
        StreetLamp l1=new StreetLamp(p,floor1.getCubeTopNormal(p));
        p=new Point(-50,-95,-460);
        StreetLamp l2=new StreetLamp(p,floor1.getCubeTopNormal(p));
        p=new Point(-10, -95, -460);
        Building b=new Building(p,30);
        scene.geometries.add(b.getGeometries(),l1.getGeometries(),l2.getGeometries(),water,floor,floor1.getGeometries());
        scene.lights.add(l1.light);
        scene.lights.add(l2.light);

    }
    public void  setupNight() throws IllegalAccessException{
        Color skyColor =new Color(18,48,129);
        Ray ray1=new Ray(new Point(80,230,-2998),new Vector(0,0,1));
        Geometry moon=new Cylinder(ray1,20d,1d).setEmission(new Color(255,255,255));
        Ray ray2=new Ray(new Point(90,230,-2998),new Vector(0,0,1));
        Geometry shadow=new Cylinder(ray2,20d,1d).setEmission(skyColor).setMaterial(material);
        Geometry sky1 =new Plane(new Point(-150, 50, -3000), new Point(-150, 250, -3000), new Point(150, 250, -3000))
                .setEmission(skyColor).setMaterial(material);
        Geometry sky2 =new Plane(new Point(-3000, 50,-150), new Point(-3000, 250, -3000), new Point(-3000,250, -60))
                .setEmission(skyColor).setMaterial(material);
        Geometry sky3 =new Plane(new Point(3000, 50,-150), new Point(3000, 250, -3000), new Point(3000,250, -60))
                .setEmission(skyColor).setMaterial(material);
        setupLights();

        scene.geometries.add(sky1,sky2,sky3,shadow,moon);

    }
    public void  setupDay() throws IllegalAccessException{
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(200, 200, -10))
                .setkL(0.0001).setkQ(0.00002));
        scene.lights.add(new PointLight(new Color(800, 255, 255), new Point(200, 200, -10))
                .setkL(0.0001).setkQ(0.002));

        Color skyColor =new Color(154,205,245);
        Geometry sky1 =new Plane(new Point(-150, 50, -3000), new Point(-150, 250, -3000), new Point(150, 250, -3000))
                .setEmission(skyColor).setMaterial(material);
        Geometry sky2 =new Plane(new Point(-3000, 50,-150), new Point(-3000, 250, -3000), new Point(-3000,250, -60))
                .setEmission(skyColor).setMaterial(material);
        Geometry sky3 =new Plane(new Point(3000, 50,-150), new Point(3000, 250, -3000), new Point(3000,250, -60))
                .setEmission(skyColor).setMaterial(material);

        scene.geometries.add(sky1,sky2,sky3);

    }




    public Pictures() throws IllegalAccessException {
    }

    @Test
    public void front() throws IllegalAccessException {
        //StlShape shape=new StlShape();

        setupBuilding();
        setupNight() ;
        setupCamera(camera1,"front");


    }
    @Test
    public void side() throws IllegalAccessException {
        setupBuilding();
        setupNight();
        setupCamera(camera2,"side");
    }
//    @Test
//    public void otherside() throws IllegalAccessException {
//        setupGeometries(scene);
//        setupLights(scene);
//        setupCamera(camera4,"side2");
//    }
    @Test
    public void halfSide() throws IllegalAccessException {
        setupBuilding();
        setupNight();
        setupCamera(camera3,"half_side");
    }
}
