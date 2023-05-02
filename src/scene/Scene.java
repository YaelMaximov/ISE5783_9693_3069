package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public Color ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    public Scene(String name) {
        this.name = name;
    }

    public Color getBackground() {
        return this.background;
    }


    public Geometries setGeometries(Geometries geometries) {
        return this.geometries;
    }

    public Color setAmbientLight(Color color) {
        return this.ambientLight;
    }
}
