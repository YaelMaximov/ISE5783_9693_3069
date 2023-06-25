package pictures;

import geometries.Geometries;
import primitives.*;

;

//    double d1=-10;
//    double d2=-50;
//    double d3=-130;

//    double x=27d;
//    double y=30d;
//    double z=27d;

public class Building {
     private static final int SHININESS = 301;

     private static final Double3 KD3 = new Double3(0.3, 0.8, 0.4);

     private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
     final Color Dark_Building_Color=new Color(191,156,95);
     //
     //new Color(198,193,180);
     final Color Light_Building_Color=new Color(219,199,129);
     //
     //new Color(228,218,193);
     final Color Pyramid_Color=new Color(104,152,159);
     //new Color(132,175,158);

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
     public  Building(Point p,double zx,double y) throws IllegalAccessException {
          //
          double nextY=4.5d;
          firstB1=new Base(p,zx,nextY,0.8).setBaseEmission(Light_Building_Color,Dark_Building_Color).setBaseMaterial(material);//base1
          Point nextP=p.add(new Vector(0,nextY,0));//for second
          secondB1=new Base(nextP,zx,nextY,0.8).setBaseEmission(Light_Building_Color,Dark_Building_Color).setBaseMaterial(material);//base2

          nextP=nextP.add(new Vector(0,nextY,0));//for firstCB1
          nextY=20d;
          firstCB1=new CylinderBase(nextP,zx,nextY,0.7).setCylinderBaseEmission(Light_Building_Color,Dark_Building_Color).setCylinderBaseMaterial(material);//first bae cylinder

          nextP=nextP.add(new Vector(0,nextY,0));//for firstB2
          nextY=4.5d;
          firstB2=new Base(nextP,zx,nextY,0.8).setBaseEmission(Light_Building_Color,Dark_Building_Color).setBaseMaterial(material);//base3
          nextP=nextP.add(new Vector(0,nextY,0));//for secondB2
          secondB2=new Base(nextP,zx,nextY,0.8).setBaseEmission(Light_Building_Color,Dark_Building_Color).setBaseMaterial(material);//base4

          nextP=nextP.add(new Vector(0,nextY,0));//for firstCB2
          nextY=20d;
          firstCB2=new CylinderBase(nextP,zx,nextY,0.7).setCylinderBaseEmission(Light_Building_Color,Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP=nextP.add(new Vector(0,nextY,0));//for//smallB1
          nextY=5d;
          smallB1=new CylinderBase(nextP,zx,nextY,0.7).setCylinderBaseEmission(Light_Building_Color,Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP=nextP.add(new Vector(0,nextY,0));//for clockC and clock
          nextY=30d;
          clockC=new Cube(nextP,zx,nextY).setCubeEmission(Light_Building_Color).setCubeMaterial(material);
          double l=zx-0.2*zx;
          Point PClock=nextP.add(new Vector(zx*0.1,zx*0.1,zx+1));
          clock=new Clock(PClock,l).setClockEmission(new Color(255,255,255),Dark_Building_Color).setClockMaterial(material);

          nextP=nextP.add(new Vector(0,nextY,0));//for smallB2
          nextY=5d;
          smallB2=new CylinderBase(nextP,zx,nextY,0.7).setCylinderBaseEmission(Light_Building_Color,Dark_Building_Color).setCylinderBaseMaterial(material);

          nextP=nextP.add(new Vector(-0.1*zx,nextY,-0.1*zx));//for pyramid
          nextY=25d;
          pyramid=new Pyramid(nextP,zx*1.2,nextY).setPyramidEmission(Pyramid_Color).setPyramidMaterial(material);

     }
     //firstB1 secondB1 firstCB1 firstB2 secondB2 firstCB2  smallB1 clockC clock smallB2 pyramid
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
}
