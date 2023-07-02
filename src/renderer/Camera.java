package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import java.util.MissingResourceException;

import static java.lang.Double.isNaN;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import java.util.stream.*;

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

    private int nss;



    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    private int threadsCount;
    private PixelManager pixelManager;
    private long printInterval = 0l;
    private int maxLevelAdaptiveSS;

    /**
     * Constructs a camera with the given position and orientation vectors.
     *
     * @param p0  The position of the camera.
     * @param vUp The up vector of the camera.
     * @param vTo The direction vector the camera is pointing towards.
     * @throws IllegalArgumentException If vUp and vTo are not orthogonal.
     */
    public Camera(Point p0, Vector vUp, Vector vTo) throws IllegalArgumentException {
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
     * @throws IllegalArgumentException If vUp and vTo are not orthogonal.
     */
    public Ray constructRay(int nX, int nY, int j, int i) throws IllegalArgumentException {
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
    public Camera setThreadsCount(int threadsCount) {
        this.threadsCount = threadsCount;
        return this;
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
     * Sets the number of rays to be used for super sampling (nss).
     * Super sampling helps to reduce aliasing artifacts in the rendered image.
     * Higher values of nss result in smoother images but require more computation.
     *
     * @param nss the number of rays to be used for super sampling
     * @return the camera instance after setting the value of nss
     */
    public Camera setNss(int nss) {
        this.nss = nss;
        return this;
    }

    /**
     * Sets the maximum level of adaptive super sampling (maxLevelAdaptiveSS).
     * Adaptive super sampling is a technique that selectively applies super sampling
     * to regions of the image where aliasing is more pronounced.
     * Higher values of maxLevelAdaptiveSS result in finer sampling and more accurate rendering
     * but require more computation.
     *
     * @param maxLevelAdaptiveSS the maximum level of adaptive super sampling
     * @return the camera instance after setting the value of maxLevelAdaptiveSS
     */
    public Camera setMaxLevelAdaptiveSS(int maxLevelAdaptiveSS) {
        this.maxLevelAdaptiveSS = maxLevelAdaptiveSS;
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
     * Renders the image by tracing rays for each pixel and writing the resulting colors to the {@link ImageWriter} object.
     *
     * @throws IllegalArgumentException if any required resources (such as the image writer or ray tracer) are missing or null
     */


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
     * Renders the image by casting rays for each pixel.
     * The method checks if all the necessary resources are initialized.
     * It then iterates over each pixel in the image and casts a ray for that pixel.
     * The behavior of the ray casting can be parallelized based on the number of threads specified.
     * The result is written to the image writer.
     *
     * @return the camera instance after rendering the image
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    public Camera renderImage() throws IllegalArgumentException {
        everythingInitialized();
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, printInterval);
        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        } else {
            IntStream.range(0, nY).parallel()
                    .forEach(i -> IntStream.range(0, nX).parallel() // for each row:
                            .forEach(j -> {
                                try {
                                    castRay(nX, nY, j, i);
                                } catch (IllegalArgumentException e) {
                                    throw new RuntimeException(e);
                                }
                            }));
        }
        return this;
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
    /**
     * Constructs a beam of rays for regular super-sampling at the specified pixel coordinates (j, i).
     * The method creates a list of rays that includes the center ray of the pixel and additional rays
     * distributed around the center to perform super-sampling.
     *
     * @param nX the number of pixels in the x-axis of the image
     * @param nY the number of pixels in the y-axis of the image
     * @param j  the x-coordinate of the pixel in the image
     * @param i  the y-coordinate of the pixel in the image
     * @return a list of rays for super-sampling at the specified pixel coordinates
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private List<Ray> constructBeamSuperSampling(int nX, int nY, int j, int i) throws IllegalArgumentException {
        // creating rays
        List<Ray> beam = new LinkedList<>();
        // add the ray of the center of the pixel
        beam.add(constructRay(nX, nY, j, i));
        // calculate the measures of each pixel
        double ry = height / nY;
        double rx = width / nX;
        // find how many steps to go on the x and y coordinates
        double yScale = alignZero((j - nX / 2d) * rx + rx / 2d);
        double xScale = alignZero((i - nY / 2d) * ry + ry / 2d);
        // get to the middle of the picture by adding the right distance
        Point pixelCenter = p0.add(vTo.scale(distance));
        if (!isZero(yScale))
            pixelCenter = pixelCenter.add(vRight.scale(yScale));
        if (!isZero(xScale))
            pixelCenter = pixelCenter.add(vUp.scale(-1 * xScale));
        Random rand = new Random();
        for (int c = 0; c < nss; c++) {
            // the rand returns randomly true or false and according to it positive or negative random number is chosen
            double dxfactor = rand.nextBoolean() ? rand.nextDouble() : -1 * rand.nextDouble();
            double dyfactor = rand.nextBoolean() ? rand.nextDouble() : -1 * rand.nextDouble();
            double dx = rx * dxfactor;
            double dy = ry * dyfactor;
            Point randomPoint = pixelCenter;
            if (!isZero(dx))
                randomPoint = randomPoint.add(vRight.scale(dx));
            if (!isZero(dy))
                randomPoint = randomPoint.add(vUp.scale(-1 * dy));

            beam.add(new Ray(p0, randomPoint.subtract(p0)));
        }
        return beam;
    }

    /**
     * Calculates the color using adaptive super-sampling at the specified pixel coordinates (j, i).
     * The method constructs a beam of rays for the pixel and checks the color of each ray.
     * If the colors differ from the center color, the method recursively calls itself with a higher level of super-sampling.
     * The final color is obtained by summing the colors and reducing them.
     *
     * @param nX          the number of pixels in the x-axis of the image
     * @param nY          the number of pixels in the y-axis of the image
     * @param j           the x-coordinate of the pixel in the image
     * @param i           the y-coordinate of the pixel in the image
     * @param maxLevel    the maximum level of super-sampling
     * @param centerColor the color of the center ray
     * @return the final color obtained by adaptive super-sampling
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Color calcAdaptiveSuperSampling(int nX, int nY, int j, int i, int maxLevel, Color centerColor) throws IllegalArgumentException {
        if (maxLevel <= 0)
            return centerColor;
        Color color = centerColor;
        Ray[] beam = new Ray[]{constructRay(2 * nX, 2 * nY, 2 * j, 2 * i),
                constructRay(2 * nX, 2 * nY, 2 * j, 2 * i + 1),
                constructRay(2 * nX, 2 * nY, 2 * j + 1, 2 * i),
                constructRay(2 * nX, 2 * nY, 2 * j + 1, 2 * i + 1)};
        for (int ray = 0; ray < 4; ray++) {
            Color currentColor = rayTracerBase.traceRay(beam[ray]);
            if (!currentColor.equals(centerColor))
                currentColor = calcAdaptiveSuperSampling(2 * nX, 2 * nY,
                        2 * j + ray / 2, 2 * i + ray % 2, (maxLevel - 1), currentColor);
            color = color.add(currentColor);
        }
        return color.reduce(5);
    }


    /** Cast ray from camera and color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) throws IllegalArgumentException {
        imageWriter.writePixel(col, row, rayTracerBase.traceRay(constructRay(nX, nY, col, row)));
        pixelManager.pixelDone();
    }
    /**
     * Casts a beam of rays for adaptive super-sampling at the specified pixel coordinates (j, i).
     * The method calculates the color of the center ray and then applies adaptive super-sampling
     * to improve the color accuracy in areas of the image where aliasing is more prominent.
     *
     * @param j the x-coordinate of the pixel in the image
     * @param i the y-coordinate of the pixel in the image
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private void castBeamAdaptiveSuperSampling(int j, int i) throws IllegalArgumentException {
        Ray center = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        Color centerColor = rayTracerBase.traceRay(center);
        imageWriter.writePixel(j, i, calcAdaptiveSuperSampling(imageWriter.getNx(), imageWriter.getNy(), j, i, maxLevelAdaptiveSS, centerColor));
        pixelManager.pixelDone();
    }

    /**
     * Casts a beam of rays for regular super-sampling at the specified pixel coordinates (j, i).
     * The method calculates the color for multiple rays in the beam and averages the colors to
     * obtain the final color for the pixel.
     *
     * @param j the x-coordinate of the pixel in the image
     * @param i the y-coordinate of the pixel in the image
     * @return the color obtained by averaging the colors of the rays in the beam
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    private Color castBeamSuperSampling(int j, int i) throws IllegalArgumentException {
        List<Ray> beam = constructBeamSuperSampling(imageWriter.getNx(), imageWriter.getNy(), j, i);
        Color color = Color.BLACK;
        for (Ray ray : beam) {
            color = color.add(rayTracerBase.traceRay(ray));
        }
        return color.reduce(nss);
    }

    /**
     * Checks if all required resources are initialized, including imageWriter, rayTracerBase, width, height, and distance.
     * Throws MissingResourceException if any of the required resources is missing.
     *
     * @throws IllegalArgumentException if any of the required resources is missing
     */
    public void everythingInitialized() throws IllegalArgumentException {
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
    }

    /**
     * Renders the image using super-sampling for improved image quality.
     * Casts a beam of rays for each pixel in the image, applies regular super-sampling,
     * and writes the final color to the imageWriter for each pixel.
     *
     * @return the camera object after rendering the image with super-sampling
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    public Camera renderImageSuperSampling() throws IllegalArgumentException {
        everythingInitialized();
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                Color color = castBeamSuperSampling(j, i);
                imageWriter.writePixel(j, i, color);
            }
        }
        return this;
    }

    /**
     * Renders the image using adaptive super-sampling for improved image quality.
     * Casts a beam of rays for each pixel in the image, applies adaptive super-sampling,
     * and writes the final color to the imageWriter for each pixel.
     *
     * @return the camera object after rendering the image with adaptive super-sampling
     * @throws IllegalArgumentException if there is an error accessing the scene or objects in the scene
     */
    public Camera renderImageAdaptiveSuperSampling() throws IllegalArgumentException {
        everythingInitialized();
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, printInterval);
        if (threadsCount == 0) {
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    castBeamAdaptiveSuperSampling(j, i);
                }
            }
        } else {
            IntStream.range(0, nY).parallel()
                    .forEach(i -> IntStream.range(0, nX).parallel()
                            .forEach(j -> {
                                try {
                                    castBeamAdaptiveSuperSampling(j, i);
                                } catch (IllegalArgumentException e) {
                                    throw new RuntimeException(e);
                                }
                            }));
        }
        return this;
    }



}




