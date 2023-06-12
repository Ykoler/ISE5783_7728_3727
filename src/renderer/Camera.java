package renderer;

import primitives.*;
import static primitives.Util.isZero;

import java.util.MissingResourceException;
import java.util.concurrent.TimeUnit;

/**
 * @author Yahel and Ashi
 *
 */
/**
 * @author ashih
 *
 */
public class Camera {
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;
	private TargetArea targetArea;

	/**
	 * Constructs new Camera object given a starting point and direction vectors.
	 * 
	 * @param p0  starting point
	 * @param vUp vector facing upwards in relation to the camera.
	 * @param vTo vector facing away from the camera.
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!isZero(vUp.dotProduct(vTo)))
			throw new IllegalArgumentException("ERROR: given vectors weren't perpendicular");
		this.targetArea = new TargetArea(p0, vTo, vUp);
	}

	/**
	 * Sets width and height in builder pattern
	 * 
	 * @param width
	 * @param height
	 * @return Camera that results
	 */
	public Camera setVPSize(double width, double height) {
		this.targetArea.setSize(width, height);
		return this;
	}

	/**
	 * Sets distance in builder pattern
	 * 
	 * @param distance
	 * @return Camera that results
	 */
	public Camera setVPDistance(double distance) {
		this.targetArea.setDistance(distance);
		return this;
	}

	/**
	 * Sets image writer in builder pattern
	 * 
	 * @param image writer
	 * @return Camera that results
	 */
	public Camera setImageWriter(ImageWriter iw) {
		this.imageWriter = iw;
		return this;
	}

	/**
	 * Sets ray tracer in builder pattern
	 * 
	 * @param ray tracer
	 * @return Camera that results
	 */
	public Camera setRayTracer(RayTracerBase rt) {
		this.rayTracer = rt;
		return this;
	}

	/**
	 * ###############################################################
	 * 
	 * @param glossAndDiffuse
	 */
	public Camera setGlossAndDiffuse(double glossAndDiffuse) {
		this.rayTracer.setGlossAndDiffuse(glossAndDiffuse);
		return this;
	}

	/**
	 * Uses the constructRay function to build the ray from the camera to the pixel,
	 * and then uses the traceRay function to send back the color of the nearest
	 * intersection point along that ray.
	 * 
	 * @param i the pixel's number on the y axis
	 * @param j the pixel's number on the x axis
	 * @return the color of the closest intersection point on the ray through pixel
	 *         i,j
	 */
	private Color castRay(int i, int j) {
		return rayTracer.traceRay(constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i));
	}

	/**
	 * Constructs ray from camera's location to a the center of a given pixel in a
	 * view plane.
	 * 
	 * @param nX number of columns
	 * @param nY number of rows
	 * @param j  current pixel's x index
	 * @param i  current pixel's y index
	 * @return resulting Ray
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		return targetArea.constructRay(nX, nY, j, i);
	}

	/**
	 * Casts a ray through every pixel in the image writer, writing to it the color
	 * of each pixel (calculated using the castRay function).
	 */
	public Camera renderImage() {
		var startTime = System.currentTimeMillis();
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", getClass().getName(), "");
		if (rayTracer == null)
			throw new MissingResourceException("Ray tracer was null", getClass().getName(), "");

		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();

		for (int i = 0; i < nY; ++i) {
			for (int j = 0; j < nX; j++) {
				imageWriter.writePixel(j, i, castRay(i, j));
			}
			if (i % 1 == 0) {
				System.out.println("Reached " + (double) i / (double) nY * 100.0d + "% of tracing.");
				System.out.println(
						"Time elapsed: " + (double) (System.currentTimeMillis() - startTime) / 1000.0d + " seconds.");
				System.out.println("Estimated time remaining: "
						+ (double) (System.currentTimeMillis() - startTime) / (double) i * (double) (nY - i) / 1000.0d
						+ " seconds.\n");
			}
		}
		return this;
	}

	/**
	 * Prints a grid over the image at an interval of pixels and colors it
	 * accordingly
	 * 
	 * @param interval the space in pixels
	 * @param color    color of the grid
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", getClass().getName(), "");
		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();
		for (int i = 0; i < nY; i += interval)
			for (int j = 0; j < nX; j += 1)
				imageWriter.writePixel(i, j, color);
		for (int i = 0; i < nY; i += 1)
			for (int j = 0; j < nX; j += interval)
				imageWriter.writePixel(i, j, color);

	}

	/**
	 * Writes pixels to final image by delegating to the ImageWriter
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", ImageWriter.class.getCanonicalName(), "");
		imageWriter.writeToImage();
	}

}
