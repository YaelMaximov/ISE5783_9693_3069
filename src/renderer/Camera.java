package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point p0;

    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    private double distance;
    private double width;
    private double height;

    public Camera(Point p0, Vector vUp, Vector vTo) throws IllegalAccessException {
        if(!isZero(vTo.dotProduct(vUp)))
        {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");
        }
        this.p0 = p0;

        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();

        vRight=this.vTo.crossProduct((this.vUp));
    }
    public Camera setVPSize(double width, double height){
        this.width=width;
        this.height=height;
        return this;
    }
    public Camera setVPDistance(double distance){
        this.distance=distance;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i) throws IllegalAccessException {
        //view plane center point
        Point Pc=p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx=width/nX;
        double Ry=height/nY;

        //Pij point[i,j] in view plane coordinates
        Point Pij=Pc;

        //delta values for moving on the view-plane
        double Xj=(j-(nX-1)/2d)*Rx;
        double Yi=-(i-(nY-1)/2d)*Ry;

        if(!isZero(Xj)){
            Pij=Pij.add(vRight.scale(Xj));
        }
        if(!isZero(Yi)){
            Pij=Pij.add(vUp.scale(Yi));
        }

        //vector from camera's eye in the direction of point(i,j) in the view-plane
        Vector Vij=Pij.subtract(p0);

        return new Ray(p0,Vij);
    }


    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getDistance() {
        return distance;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
