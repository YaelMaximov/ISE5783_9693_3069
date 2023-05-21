package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import java.util.MissingResourceException;

import static java.lang.Double.isNaN;
import static primitives.Util.isZero;

/**
 * Camera class represents a virtual camera in a 3D scene.
 */
public class Camera {
    private Point p0;

    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    private double distance;
    private double width;
    private double height;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    /**
     * Constructs a camera with the given position and orientation vectors.
     *
     * @param p0  The position of the camera.
     * @param vUp The up vector of the camera.
     * @param vTo The direction vector the camera is pointing towards.
     * @throws IllegalAccessException If vUp and vTo are not orthogonal.
     */
    public Camera(Point p0, Vector vUp, Vector vTo) throws IllegalAccessException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");
        }
        this.p0 = p0;

        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();

        vRight = this.vTo.crossProduct((this.vUp));
    }

    /**
     * Sets the size of the view plane.
     *
     * @param width  The width of the view plane.
     * @param height The height of the view plane.
     * @return The camera object itself, for method chaining.
     */


    /**
     * Constructs a ray from the camera passing through a specified pixel on the view plane.
     *
     * @param nX The number of pixels in the x-axis of the view plane.
     * @param nY The number of pixels in the y-axis of the view plane.
     * @param j  The x-coordinate of the pixel.
     * @param i  The y-coordinate of the pixel.
     * @return A ray passing through the specified pixel.
     * @throws IllegalAccessException If vUp and vTo are not orthogonal.
     */
    public Ray constructRay(int nX, int nY, int j, int i) throws IllegalAccessException {
        //view plane center point
        Point Pc = p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx = width / nX;
        double Ry = height / nY;

        //Pij point[i,j] in view plane coordinates
        Point Pij = Pc;

        //delta values for moving on the view-plane
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));
        }

        //vector from camera's eye in the direction of point(i,j) in the view-plane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }

    /**
     * Returns the position of the camera.
     *
     * @return The position of the camera.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Sets the viewport size of this Camera object to the specified width and height.
     * The viewport is the area of the screen where the Camera's view is displayed.
     *
     * @param width  the width of the viewport, in pixels
     * @param height the height of the viewport, in pixels
     * @return a reference to this Camera object, for method chaining
     */

    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance of the view plane from the camera.
     *
     * @param distance The distance of the view plane from the camera.
     * @return The camera object itself, for method chaining.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Sets the {@link ImageWriter} object to be used for rendering the image.
     *
     * @param imageWriter the {@link ImageWriter} object to be used for rendering the image
     * @return a reference to this Camera object, for method chaining
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the {@link RayTracerBase} object to be used for casting rays and generating colors.
     *
     * @param rayTracerBase the {@link RayTracerBase} object to be used for casting rays and generating colors
     * @return a reference to this Camera object, for method chaining
     */
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * Casts a ray and returns the resulting color.
     *
     * @param ray the ray to cast
     * @return the color of the object that the ray intersects with
     * @throws IllegalAccessException if the ray tracer has not been set for this Camera object
     */
    private Color castRay(Ray ray) throws IllegalAccessException {
        Color color = rayTracerBase.traceRay(ray);
        return color;
    }

    /**
     * Renders the image by tracing rays for each pixel and writing the resulting colors to the {@link ImageWriter} object.
     *
     * @throws IllegalAccessException if any required resources (such as the image writer or ray tracer) are missing or null
     */
    public Camera renderImage() throws IllegalAccessException {
        if (imageWriter == null || rayTracerBase == null || isNaN(width) || isNaN(height) || isNaN(distance)) {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resources", "Camera", "imageWriter");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("missing resources", "Camera", "rayTracerBase");
            }
            if (isNaN(width)) {
                throw new MissingResourceException("missing resources", "Camera", "width");
            }
            if (isNaN(height)) {
                throw new MissingResourceException("missing resources", "Camera", "height");
            }
            if (isNaN(distance)) {
                throw new MissingResourceException("missing resources", "Camera", "distance");
            }
            throw new UnsupportedOperationException();
        }
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j++) {
                {
                    Ray ray = constructRay(nX, nY, j, i);
                    Color color = castRay(ray);
                    imageWriter.writePixel(j, i, color);
                }
            }
        return this;
    }

    /**
     * Prints a grid on the image with the given interval and color.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @throws MissingResourceException If the imageWriter is null.
     */
    public void printGrid(int interval, Color color) throws MissingResourceException {
        if (imageWriter == null) {
            throw new MissingResourceException("imageWriter", "Camera", "The value of imageWriter is null.");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        imageWriter.writeToImage();
    }

    /**
     * Writes the image to file using the ImageWriter.
     *
     * @throws MissingResourceException If the imageWriter is null.
     */
    public Camera writeToImage() throws MissingResourceException {
        if (imageWriter == null) {
            throw new MissingResourceException("imageWriter", "Camera", "The value of imageWriter is null.");
        }
        imageWriter.writeToImage();
        return this;
    }


}




