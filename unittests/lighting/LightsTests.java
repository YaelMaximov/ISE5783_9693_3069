package lighting;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private final Scene scene1 = new Scene("Test scene");
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

    private final Camera camera1 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(150, 150).setVPDistance(1000);
    private final Camera camera2 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 1, 0), new Vector(0, 0, -1))
            .setVPSize(200, 200).setVPDistance(1000);

    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
    private final Color trianglesLightColor = new Color(800, 500, 250);
    private final Color sphereLightColor = new Color(800, 500, 0);
    private final Color sphereColor = new Color(BLUE).reduce(2);

    private final Point sphereCenter = new Point(0, 0, -50);
    private static final double SPHERE_RADIUS = 50d;

    // The triangles' vertices:
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
            .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    public LightsTests() throws IllegalArgumentException {
    }

    /**
     * Produce a Pictures of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() throws IllegalArgumentException {

        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a Pictures of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() throws IllegalArgumentException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setkL(0.001).setkQ(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a Pictures of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() throws IllegalArgumentException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                .setkL(0.001).setkQ(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a Pictures of a sphere lighted by all the lights
     */
    @Test
    public void sphereAllLights() throws IllegalArgumentException {

        scene1.geometries.add(sphere);
       scene1.lights.add(new DirectionalLight(new Color(500, 200, 100), new Vector(1, 0, 0.5)));//purple
        scene1.lights.add(new PointLight(new Color(800, 600, 0), new Point(80, -60, 25)).setkL(0.001).setkQ(0.0002));//yellow
        scene1.lights.add(new SpotLight(new Color(300, 800, 0), new Point(90, 80, 25), new Vector(-1, -1, -0.5))
                .setkL(0.01).setkQ(0.0001));//green-blue

        ImageWriter imageWriter = new ImageWriter("sphereAllLights", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a Pictures of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() throws IllegalArgumentException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a Pictures of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() throws IllegalArgumentException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setkL(0.001).setkQ(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a Pictures of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() throws IllegalArgumentException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setkL(0.001).setkQ(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }
    /**
     * Produce a Pictures of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesAllLights() throws IllegalArgumentException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(new Color(50,0,100), new Vector(1, 1, -0.5)));
        scene2.lights.add(new PointLight(new Color(800,100,300), new Point(-10,-70,-110))
                .setkL(0.001).setkQ(0.0002));//red-purple
        scene2.lights.add(new SpotLight(new Color(150,200,800), new Point(40,0,-100), new Vector(-2,1,-2))
                .setkL(0.001).setkQ(0.0001));//blue

        ImageWriter imageWriter = new ImageWriter("TrianglesAllLights", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

//    /**
//     * Produce a Pictures of a sphere lighted by a narrow spotlight
//     */
//    @Test
//    public void sphereSpotSharp() throws IllegalArgumentException {
//        scene1.geometries.add(sphere);
//        scene1.lights
//                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
//                        .setNarrowBeam(10).setKl(0.001).setKq(0.00004));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracer(new RayTracerBasic(scene1)) //
//                .renderImage() //
//                .writeToImage(); //
//    }
//
//    /**
//     * Produce a Pictures of two triangles lighted by a narrow spotlight
//     */
//    @Test
//    public void trianglesSpotSharp() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setNarrowBeam(10).setKl(0.001).setKq(0.00004));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracer(new RayTracerBasic(scene2)) //
//                .renderImage() //
//                .writeToImage(); //
//    }

}
