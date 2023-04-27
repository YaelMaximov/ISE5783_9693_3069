package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Sphere class represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a sphere with the given radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the normal vector to the sphere at a given point.
     *
     * @param p a point on the sphere (unused)
     * @return the normal vector to the sphere
     */
    @Override
    public Vector getNormal(Point p) throws IllegalAccessException {
        return p.subtract(this.center).normalize();
    }

    @Override
    public List<Point> findIntsersections(Ray ray) throws IllegalAccessException {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector u;
        try {
            u = center.subtract(p0);
        }
        catch (IllegalAccessException e){
            return List.of(ray.getDir());// לעבור ולתקן לא נכון!!
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquares = tm == 0 ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquares);
        if (thSquared <= 0)
        {
            return null;
        }
        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0)
        {
            return null;
        }
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0)
        {
            return null;
        }
        if (t1 > 0 && t2 >0)
        {
            return List.of(ray.getDir(),ray.getP0());//לא נכון חייבת לחזור!!
        }
        if (t1 > 0)
        {
            return List.of(ray.getDir());//לא נכון לבדוק שובב
        }
        else
            return List.of(ray.getDir());// לא נכון לבדוק שוב



       // Vector L= new Vector(center.subtract(ray.getP0()).xyz);
        ray.getDir();
        double tm= (center.subtract(ray.getP0()).dotProduct(ray.getDir()));
        double d= Math.pow((Math.pow((center.subtract(ray.getP0()).length()), 2))-(Math.pow(tm, 2)),0.5);//האם זה בסדר להתשתמש בpow לחזקה או שזה פעולה יקרה מדי?
        if (d>=radius)//no intersections
        {

            return null;
        }
        double th= Math.pow((Math.pow(radius, 2))-(Math.pow(d, 2)),0.5);
        double t1= tm-th;
        double t2= tm+th;

        if (isZero(th))//one intersection
        {
            ArrayList<Point> arr= new ArrayList<Point>();
            arr.add(ray.getP0().add(ray.getDir().scale(t1)));
            return arr;
        }
        else
        {
            if(t1>0 || t2>0)//two intersections
            {
                ArrayList<Point> arr= new ArrayList<Point>();
                if(t1 >0)
                    arr.add(ray.getP0().add(ray.getDir().scale(t1)));
                if(t2>0)
                    arr.add(ray.getP0().add(ray.getDir().scale(t2)));
                return arr;
            }
            return null;
        }

    }
}
