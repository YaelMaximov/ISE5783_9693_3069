package pictures;

import geometries.Geometries;
import primitives.*;

/**
 * The Building class represents a building consisting of various geometric shapes.
 * It includes bases, cylinder bases, cubes, pyramids, and a clock.
 */
public class Building {
     private static final int SHININESS = 301;
     private static final Double3 KD3 = new Double3(0.3, 0.8, 0.4);
     private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

     /**
      * The color of the building when in shadow.
      */
     final Color Dark_Building_Color = new Color(191, 156, 95);

     /**
      * The color of the building in light.
      */
     final Color Light_Building_Color = new Color(219, 199, 129);

     /**
      * The color of the pyramid.
      */
     final Color Pyramid_Color = new Color(99, 138, 189);

     private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);

     Base firstB1;
     Base secondB1;
     CylinderBase firstCB1;
     Base firstB2;
     Base secondB2;
     CylinderBase firstCB2;
     CylinderBase smallB1;
     Cube clockC;
     Clock clock;
     CylinderBase smallB2;
     Pyramid pyramid;

     /**
      * Constructs a Building object with the given parameters.
      *
      * @param p  The base point of the building.
      * @param zx The size of the building in the X-axis.
      * @throws IllegalAccessException if an illegal access occurs.
      */
     public Building(Point p, double zx) throws IllegalAccessException {
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
      * @return The Geometries object containing all the geometries of the building.
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
                  pyramid.getGeometries());
     }
}



//


//    double l=x-0.2*x;
//    Point p1=new Point(d1, d2, d3);
//    Point p2=new Point(d1-x*0.1, d2+70d, d3-z*0.1);
//    Point p3=new Point(d1+x*0.1,d2+70d-x,d3+z);
//
//    //Base base=new Base(p1,z,x,40d,0.8).setBaseEmission(new Color(232,194,128)).setBaseMaterial(material);
//    Cube building=new Cube(p1.add(new Vector(0,40d,0)),z,x,y).setCubeEmission(new Color(232,194,128)).setCubeMaterial(material);
//    Pyramid p=new Pyramid(p2,z+z*0.2,x+x*0.2,30d).setPyramidEmission(new Color(126,147,229)).setPyramidMaterial(material);
//    Clock clock=new Clock(p3,l).setClockEmission(new Color(255,255,255),new Color(191,156,95)).setClockMaterial(material);
//    CylinderBase cb=new CylinderBase(p1,x,40d,0.6).setCylinderBaseEmission(new Color(219,199,129),new Color(191,156,95)).setCylinderBaseMaterial(material);
//    //additions a=new additions(p1.add(new Vector(0,40d,0)),z,x,y,new Color(219,199,129),new Color(191,156,95));
//        scene1.geometries.add(clock.getGeometries(),p.getGeometries(),cb.getGeometries(),building.getGeometries(),sky1,sky2,sky3,floor);

