package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) throws IllegalAccessException{
        super(x, y, z);
        if(xyz.equals(Double3.ZERO)){
            throw  new IllegalArgumentException("Vector Zero is not allowed");
        }
    }

     Vector (Double3 vec3)throws IllegalAccessException {
        this(vec3.d1, vec3.d2, vec3.d3);
    }
    public Vector add(Vector vector) throws IllegalAccessException {
        return new Vector(xyz.add(vector.xyz));
    }
    public Vector scale(double rhs)throws IllegalAccessException{
        return new Vector(xyz.scale(rhs));
    }
    public double dotProduct(Vector v){
        return ((this.xyz.d1*v.xyz.d1)+
                (this.xyz.d2*v.xyz.d2)+
                (this.xyz.d3*v.xyz.d3));
    }
    public Vector crossProduct(Vector other) throws IllegalAccessException {
        double x=this.xyz.d2*other.xyz.d3-this.xyz.d3*other.xyz.d2;
        double y=this.xyz.d3*other.xyz.d1-this.xyz.d1*other.xyz.d3;
        double z=this.xyz.d1*other.xyz.d2-this.xyz.d2*other.xyz.d1;
        return new Vector(x,y,z);
    }
    public double lengthSquared(){
        return (((this.xyz.d1)*(this.xyz.d1))+
                ((this.xyz.d2)*(this.xyz.d2))+
                ( (this.xyz.d3)*(this.xyz.d3)));
    }
    public double length(){
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize() throws IllegalAccessException {
        return new Vector(this.xyz.reduce(this.length()));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }





}
