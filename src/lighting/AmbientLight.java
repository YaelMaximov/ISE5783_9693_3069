package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    public static final Color NONE = Color.BLACK;
    public static final Double3 KA0 = Double3.ZERO;
    Color intensity;
    public AmbientLight(Color IA, Double3 KA){
        this.intensity = IA.scale(KA);
    }

    public AmbientLight(Color IA, double KA)
    {
        this.intensity = IA.scale(KA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
