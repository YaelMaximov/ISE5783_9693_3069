package pictures;

import geometries.Geometries;
import primitives.*;

/**
 * The Building class represents a building composed of various geometric shapes.
 */
public class Building {
     private static final int SHININESS = 301;
     private static final Double3 KD3 = new Double3(0.3, 0.8, 0.4);
     private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

     final Color Dark_Building_Color = new Color(191, 156, 95);
     final Color Light_Building_Color = new Color(219, 199, 129);
     final Color Pyramid_Color = new Color(99, 138, 189);

     private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
     private Base firstB1;
     private Base secondB1;
     private CylinderBase firstCB1;
     private Base firstB2;
     private Base secondB2;
     private CylinderBase firstCB2;
     private CylinderBase smallB1;
     private Cube clockC;
     private Clock clock;
     private CylinderBase smallB2;
     private Pyramid pyramid;

     /**
      * Constructs a Building object with the given parameters.
      *
      * @param p  The starting point of the building.
      * @param zx The length of the building along the x-axis.
      * @throws IllegalArgumentException if any of the input parameters are invalid.
      */
     public Building(Point p, double zx) throws IllegalArgumentException {
          double nextY = 4.5d;
          firstB1 = new Base(p, zx, nextY, 0.8).setBaseEmission(Light_Building_Color, Dark_Building_Color).setBaseMaterial(material);
          Point nextP = p.add(new Vector(0, nextY, 0));
          secondB1 = new Base(nextP, zx, nextY, 0.8).setBaseEmission(Light_Building_Color, Dark_Building_Color).setBaseMaterial(material);

          nextP = nextP.add(new Vector(0, nextY, 0));
          nextY = 20d;
          firstCB1 = new CylinderBase(nextP, zx, nextY, 0.7).setCylinderBaseEmission(Light_Building_Color, Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP = nextP.add(new Vector(0, nextY, 0));
          nextY = 4.5d;
          firstB2 = new Base(nextP, zx, nextY, 0.8).setBaseEmission(Light_Building_Color, Dark_Building_Color).setBaseMaterial(material);
          nextP = nextP.add(new Vector(0, nextY, 0));
          secondB2 = new Base(nextP, zx, nextY, 0.8).setBaseEmission(Light_Building_Color, Dark_Building_Color).setBaseMaterial(material);

          nextP = nextP.add(new Vector(0, nextY, 0));
          nextY = 20d;
          firstCB2 = new CylinderBase(nextP, zx, nextY, 0.7).setCylinderBaseEmission(Light_Building_Color, Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP = nextP.add(new Vector(0, nextY, 0));
          nextY = 5d;
          smallB1 = new CylinderBase(nextP, zx, nextY, 0.7).setCylinderBaseEmission(Light_Building_Color, Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP = nextP.add(new Vector(0, nextY, 0));
          nextY = 30d;
          clockC = new Cube(nextP, zx, nextY).setCubeEmission(Light_Building_Color).setCubeMaterial(material);
          double l = zx - 0.2 * zx;
          Point PClock = nextP.add(new Vector(zx * 0.1, zx * 0.1, zx + 1));
          clock = new Clock(PClock, l).setClockEmission(new Color(255, 255, 255), Dark_Building_Color).setClockMaterial(material);

          nextP = nextP.add(new Vector(0, nextY, 0));
          nextY = 5d;
          smallB2 = new CylinderBase(nextP, zx, nextY, 0.7).setCylinderBaseEmission(Light_Building_Color, Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP = nextP.add(new Vector(-0.1 * zx, nextY, -0.1 * zx));
          nextY = 25d;
          pyramid = new Pyramid(nextP, zx * 1.2, nextY).setPyramidEmission(Pyramid_Color).setPyramidMaterial(material);
     }

     /**
      * Returns the geometries of the building.
      *
      * @return The geometries of the building.
      */
     public Geometries getGeometries() {
          return new Geometries(
                  firstB1.getGeometries(),
                  secondB1.getGeometries(),
                  firstCB1.getGeometries(),
                  firstB2.getGeometries(),
                  secondB2.getGeometries(),
                  firstCB2.getGeometries(),
                  smallB1.getGeometries(),
                  clockC.getGeometries(),
                  clock.getGeometries(),
                  smallB2.getGeometries(),
                  pyramid.getGeometries()
          );
     }
}
