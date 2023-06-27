package pictures;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Sphere;
import primitives.Color;
import primitives.Material;
import primitives.Point;

import static java.awt.Color.WHITE;

public class Snow_man {
    private static final int SHININESS = 200;
    private static final double KD = 0.7;
    private static final double KS = 0.3;

    private final Color sphereColor = new Color(WHITE).reduce(2);

    private final Geometry sphere1;
    private final Geometry sphere2;
    private final Geometry sphere3;
    private final Geometry rEye;
    private final Geometry lEye;

    private final Geometry button1;
    private final Geometry button2;
    private final Geometry button3;

    public Snow_man(Point baseCenter) throws IllegalAccessException {
        sphere1 = new Sphere(16d, new Point(0, -10, -50))
                .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
        sphere2 = new Sphere(20d, new Point(0, -35, -50))
                .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
        sphere3 = new Sphere(13d, new Point(0, 13, -50))
                .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
        rEye = new Sphere(2d, new Point(5, 14, -37))
                .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
        lEye = new Sphere(2d, new Point(-5, 14, -37))
                .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));

        button1 = new Sphere(2d, new Point(0, -5, -34))
                .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
        button2 = new Sphere(2d, new Point(0, -15, -34))
                .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
        button3 = new Sphere(2d, new Point(0, -27, -30))
                .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    }
    public Geometries getGeometries() {
        return new Geometries(sphere1,sphere2,sphere3,rEye,lEye,button1,button2,button3);
    }


}
