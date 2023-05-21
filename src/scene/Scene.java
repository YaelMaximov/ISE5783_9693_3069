package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a scene in 3D space, containing a name, background color, ambient light, and geometries.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public String name;

    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light in the scene.
     */
    public AmbientLight ambientLight = new AmbientLight(AmbientLight.NONE, Double3.ZERO);

    /**
     * The geometries in the scene.
     */
    public Geometries geometries = new Geometries();

    public List<LightSource> lights=new LinkedList<>();


    /**
     * Constructs a new Scene object with the given name.
     *
     * @param nameScene the name of the scene
     */
    public Scene(String nameScene) {
        name = nameScene;
        geometries = new Geometries();
    }

    /**
     * Sets the background color of the scene and returns the Scene object.
     *
     * @param background the new background color of the scene
     * @return the Scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene and returns the Scene object.
     *
     * @param ambientLight the new ambient light of the scene
     * @return the Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene and returns the Scene object.
     *
     * @param geometries the new geometries of the scene
     * @return the Scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    /**
     * Sets the lights of the scene and returns the Scene object.
     *
     * @param lights the new lights of the scene
     * @return the Scene object
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
