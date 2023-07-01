package pictures;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;

public class StlShape {
    List<Triangle> triangles = new LinkedList<>();
    public StlShape(String filePath) {

        try (RandomAccessFile binaryFile = new RandomAccessFile(filePath, "r")) {
            byte[] header = new byte[80];
            binaryFile.read(header); // Read the binary STL file header (80 bytes)

            byte[] numTrianglesBytes = new byte[4];
            binaryFile.read(numTrianglesBytes); // Read the number of triangles (4 bytes)
            int numTriangles = ByteBuffer.wrap(numTrianglesBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();


            for (int i = 0; i < numTriangles; i++) {


                byte[] normalBytes = new byte[12];
                binaryFile.read(normalBytes); // Read the normal vector (12 bytes)
                float[] normal = new float[3];
                ByteBuffer.wrap(normalBytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(normal);

                float[][] vertices = new float[3][3];
                for (int j = 0; j < 3; j++) {
                    byte[] vertexBytes = new byte[12];
                    binaryFile.read(vertexBytes); // Read each vertex of the triangle (12 bytes per vertex)
                    ByteBuffer.wrap(vertexBytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(vertices[j]);
                }

                byte[] garbage = new byte[2];
                binaryFile.read(garbage); // Read the garbage data (2 bytes)

                // Create a triangle using the extracted vertex coordinates
                Triangle triangle = new Triangle(
                        new Point(vertices[0][0], vertices[0][1], vertices[0][2]),
                        new Point(vertices[1][0], vertices[1][1], vertices[1][2]),
                        new Point(vertices[2][0], vertices[2][1], vertices[2][2])
                );
                triangle.setEmission(new Color(255,255,255));

                triangles.add(triangle);
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public List<Triangle> getGeometries() {
        return triangles;
    }


    }

