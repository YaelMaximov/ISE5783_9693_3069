package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;
import java.util.OptionalDouble;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices)  throws IllegalArgumentException{
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
      if (bvhIsOn)createBoundingBox();

   }

   /**
    * Returns the normal vector of the plane geometry at the specified point.
    * This method delegates the calculation of the normal vector to the underlying plane.
    *
    * @param point The point on the plane.
    * @return The normal vector of the plane.
    */
   @Override
   public Vector getNormal(Point point) {
      return plane.getNormal();
   }

   /**
    * Creates a bounding box for this object.
    * The bounding box is an axis-aligned bounding box (AABB) that encloses the object.
    *
    */
   protected void createBoundingBox()  {
      double minX = vertices.stream().mapToDouble(Point::getX).min().orElse(Double.POSITIVE_INFINITY);
      double maxX = vertices.stream().mapToDouble(Point::getX).max().orElse(Double.NEGATIVE_INFINITY);

      double minY = vertices.stream().mapToDouble(Point::getY).min().orElse(Double.POSITIVE_INFINITY);
      double maxY = vertices.stream().mapToDouble(Point::getY).max().orElse(Double.NEGATIVE_INFINITY);

      double minZ = vertices.stream().mapToDouble(Point::getZ).min().orElse(Double.POSITIVE_INFINITY);
      double maxZ = vertices.stream().mapToDouble(Point::getZ).max().orElse(Double.NEGATIVE_INFINITY);

      box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
   }


   /**
    * find intersection between ray and polygon
    * @param ray ray towards the plane
    * @return  immutable list of one intersection point as  {@link GeoPoint} object
    */
   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)  {
      // find intersection between ray and plane containing the polygon
      List<GeoPoint> points=plane.findGeoIntersections(ray,maxDistance);
      // no intersections with plane , ray does not intersect polygon
      if (points==null)
         return null;

      //check that intersection point is closer to ray origin than
      // max distance parameter
      double distance = points.get(0).point.distance(ray.getP0());
      if(alignZero(distance-maxDistance)>0)
         return null;

      // check that intersection point is within polygon boundary
      // by  creating vectors from ray origin to each pair of adjacent vertices in polygon
      // if the sign of the dot product of the vertices for all pairs is matching. ray intersects polygon
      Point p0 = ray.getP0();
      Vector direction = ray.getDir();

      // get vector from ray origin to first vertices of polygon
      Vector v1 = vertices.get(0).subtract(p0);

      // get vector from ray origin to adjacent vertices of previous vertices
      Vector v2 = vertices.get(1).subtract(p0);

      // get sign of dot product of the vectors
      double sign = direction.dotProduct(v2.crossProduct(v1));

      // if dot product == 0 ray does not intersect polygon
      if (isZero(sign))
         return null;

      // flag setting the sign of the dot product of the first pair of vertices
      boolean checkSign = sign > 0;

      // loop over all adjacent vertices in polygon and check sign of dot-product for constructed
      // vectors
      for (int i = vertices.size() - 1; i > 0; --i) {
         v2 = v1;
         v1 = vertices.get(i).subtract(p0);
         sign = alignZero(direction.dotProduct(v2.crossProduct(v1)));

         // vectors constructed are orthogonal , ray does not intersect polygon
         if (isZero(sign))
            return null;

         //  sign is not matching
         if (checkSign != (sign > 0))
            return null;
      }

      // all signs were matching return the intersection point
      return List.of(new GeoPoint(this,points.get(0).point));
   }

}