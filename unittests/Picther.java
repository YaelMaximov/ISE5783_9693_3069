/**
 *
 */


import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class Picther {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() throws IllegalAccessException {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }



    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() throws IllegalAccessException {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}