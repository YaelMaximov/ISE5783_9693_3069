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
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private Scene scene = new Scene("front");

    private final Camera camera1 = new Camera(new Point(0, 0, 1500),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000).setMaxLevelAdaptiveSS(3).setThreadsCount(8);//front
    private final Camera camera2 = new Camera(new Point(2000, 0, -520),
            new Vector(0, 1, 0), new Vector(-1, 0, 0))
            .setVPSize(150, 150).setVPDistance(1000).setMaxLevelAdaptiveSS(3).setThreadsCount(8);//side

    private final Camera camera3 = new Camera(new Point(2000, 0, 1500),
            new Vector(0, 1, 0), new Vector(-1, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000).setMaxLevelAdaptiveSS(3).setThreadsCount(8);//half-side

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);

    private final Material waterMaterial = new Material().setkT(0.7).setkR(0.3);

    //new Color(143,211,240)
    private final Geometry floor =new Plane(new Point(1500, -100, -1500), new Point(-1500, -100, -1500),
            new Point(670, -100, 3000)).setEmission(new Color(39,45,74))//
            .setMaterial(waterMaterial);
    private final Geometry water =new Plane(new Point(1500, -105, -1500), new Point(-1500, -100, -1500),
            new Point(670, -100, 3000)).setEmission(new Color(25,49,145))//
            .setMaterial(waterMaterial);
    private final Cube floor1 =new Cube(new Point(-2400, -100, -3600),3300,5).setCubeEmission(new Color(39,45,100)).setCubeMaterial(material);

    public void  setupCamera(Camera camera,String fileName) throws IllegalArgumentException
    {
        ImageWriter imageWriter = new ImageWriter(fileName, 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
    public void  setupLights() throws IllegalArgumentException
    {
        scene.lights.add(new DirectionalLight(new Color(80, 26, 26), new Vector(0, -0.2, -1)));

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
    public void  setupBackground() throws IllegalArgumentException{
        Point p=new Point(60,-95,-590);
        SpotStreetLight l1=new SpotStreetLight(p,floor1.getCubeTopNormal(p));
        p=new Point(100,-95,-515);
        StreetLamp l2=new StreetLamp(p,floor1.getCubeTopNormal(p));
        p=new Point(140,-95,-460);
        StreetLamp l3=new StreetLamp(p,floor1.getCubeTopNormal(p));
        p=new Point(-50,-95,-590);
        SpotStreetLight l4=new SpotStreetLight(p,floor1.getCubeTopNormal(p));
        p=new Point(-90,-95,-515);
        StreetLamp l5=new StreetLamp(p,floor1.getCubeTopNormal(p));
        p=new Point(-130,-95,-460);
        StreetLamp l6=new StreetLamp(p,floor1.getCubeTopNormal(p));
        scene.lights.add(l1.getLight());
        scene.lights.add(l2.getLight());
        scene.lights.add(l3.getLight());
        scene.lights.add(l4.getLight());
        scene.lights.add(l5.getLight());
        scene.lights.add(l6.getLight());
        scene.geometries.add(water,floor,floor1.getGeometries()
                ,l1.getGeometries()
                ,l2.getGeometries()
                ,l3.getGeometries()
                ,l4.getGeometries()
                ,l5.getGeometries()
                ,l6.getGeometries());
    }

    public void  setupBuilding() throws IllegalArgumentException{
        Point p=new Point(-10, -95, -670);
        Building b=new Building(p,30);
        scene.geometries.add(b.getGeometries());

    }
    public void  setupNight() throws IllegalArgumentException{
        Color skyColor =new Color(18,48,129);
        Material skymaterial = new Material().setkD(KD3).setnShininess(10);
        Ray ray1=new Ray(new Point(80,230,-2998),new Vector(0,0,1));
        Geometry moon=new Cylinder(ray1,20d,1d).setEmission(new Color(255,255,255));
        Ray ray2=new Ray(new Point(90,230,-2998),new Vector(0,0,1));
        Geometry shadow=new Cylinder(ray2,20d,1d).setEmission(skyColor).setMaterial(material);
        Geometry sky1 =new Plane(new Point(-150, 50, -3000), new Point(-150, 250, -3000), new Point(150, 250, -3000))
                .setEmission(skyColor).setMaterial(skymaterial);
        Geometry sky2 =new Plane(new Point(-3000, 50,-150), new Point(-3000, 250, -3000), new Point(-3000,250, -60))
                .setEmission(skyColor).setMaterial(skymaterial);
        Geometry sky3 =new Plane(new Point(3000, 50,-150), new Point(3000, 250, -3000), new Point(3000,250, -60))
                .setEmission(skyColor).setMaterial(skymaterial);
        setupLights();

        scene.geometries.add(sky1,sky2,sky3,shadow,moon);

    }

    public Pictures() throws IllegalArgumentException {
    }

    @Test
    public void front() throws IllegalArgumentException {
        setupBackground();
        setupBuilding();
        setupNight() ;
        setupCamera(camera1,"front");


    }
    @Test
    public void side() throws IllegalArgumentException {
        setupBackground();
        setupBuilding();
        setupNight();
        setupCamera(camera2,"side");
    }

    @Test
    public void halfSide() throws IllegalArgumentException {
        setupBackground();
        setupBuilding();
        setupNight();
        setupCamera(camera3,"half_side");
    }
}
