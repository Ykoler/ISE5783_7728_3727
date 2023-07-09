package renderer;

import primitives.*;
import static primitives.Util.isZero;
import static renderer.PixelManager.*;

import java.util.LinkedList;
import java.util.MissingResourceException;

/**
 * @author Yahel and Ashi
 *
 */
public class Camera {

	/**
	 * Pixel manager for supporting:
	 * <ul>
	 * <li>multi-threading</li>
	 * <li>debug print of progress percentage in Console window/tab</li>
	 * <ul>
	 */
	private PixelManager pixelManager;

	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;
	private TargetArea targetArea;
	private int threadsCount = 0;
	private double printInterval = 0;

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
	 * Sets number of threads in builder pattern.
	 * 
	 * @param threadNum
	 * @return Camera that results
	 */
	public Camera setMultiThreading(int threadsCount) {
		this.threadsCount = threadsCount;
		return this;
	}

	/**
	 * Sets interval size for progress printing in builder pattern.
	 * 
	 * @param d
	 * @return Camera that results
	 */
	public Camera setDebugPrint(double d) {
		this.printInterval = d;
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
	private void castRay(int nX, int nY, int col, int row) {
		imageWriter.writePixel(col, row, rayTracer.traceRay(constructRay(nX, nY, col, row)));
		pixelManager.pixelDone();
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
	 * 
	 * @return the Camera itself
	 */
	public Camera renderImage() {
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", getClass().getName(), "");
		if (rayTracer == null)
			throw new MissingResourceException("Ray tracer was null", getClass().getName(), "");

		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();
		pixelManager = new PixelManager(nY, nX, printInterval);
		if (threadsCount == 0)
			for (int i = 0; i < nY; ++i)
				for (int j = 0; j < nX; ++j)
					castRay(nX, nY, j, i);
		else { // see further... option 2
			var threads = new LinkedList<Thread>(); // list of threads
			while (threadsCount-- > 0) // add appropriate number of threads
				threads.add(new Thread(() -> { // add a thread with its code
					Pixel pixel; // current pixel(row,col)
					// allocate pixel(row,col) in loop until there are no more pixels
					while ((pixel = pixelManager.nextPixel()) != null)
						// cast ray through pixel (and color it – inside castRay)
						castRay(nX, nY, pixel.col(), pixel.row());
				}));
			// start all the threads
			for (var thread : threads)
				thread.start();
			// wait until all the threads have finished
			try {
				for (var thread : threads)
					thread.join();
			} catch (InterruptedException ignore) {
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
	public Camera printGrid(int interval, Color color) {
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
		return this;
	}

	/**
	 * Writes pixels to final image by delegating to the ImageWriter
	 */
	public Camera writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", ImageWriter.class.getCanonicalName(), "");
		imageWriter.writeToImage();
		return this;
	}
}
