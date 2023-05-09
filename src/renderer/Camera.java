package renderer;

import primitives.*;
import static primitives.Util.isZero;

import java.nio.channels.NonReadableChannelException;
import java.util.MissingResourceException;

/**
 * @author Yahel and Ashi
 *
 */
/**
 * @author ashih
 *
 */
public class Camera {
	private Point p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;

	/**
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * @return the vUp
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * @return the vTo
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * @return the vRight
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

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
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight = vUp.crossProduct(vTo);
		this.p0 = p0;
	}

	/**
	 * Sets width and height in builder pattern
	 * 
	 * @param width
	 * @param height
	 * @return Camera that results
	 */
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * Sets distance in builder pattern
	 * 
	 * @param distance
	 * @return Camera that results
	 */
	public Camera setVPDistance(double distance) {
		this.distance = distance;
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
		Point pIJ = p0.add(vTo.scale(distance));
		// Calculate distance on x,y axes to the designated point
		double yI = (((nY - 1) / 2.0) - i) * (height / nY);
		double xJ = (((nX - 1) / 2.0) - j) * (width / nX);
		// Avoiding creation of zero vector (which is unnecessary anyway)
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));
		return new Ray(p0, pIJ.subtract(p0));
	}

	/**
	 * renders an image.
	 * ###############################################################################################################################
	 */
	public void renderImage() {
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", ImageWriter.class.getCanonicalName(), "");
		if (rayTracer == null)
			throw new MissingResourceException("Ray tracer was null", RayTracerBase.class.getCanonicalName(), "");
		throw new UnsupportedOperationException();
	}

	/**
	 * Prints a grid over the image at an interval of pixels and colors it
	 * accordingly
	 * 
	 * @param interval the space in pixels
	 * @param color    color to paint
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("Image writer was null", ImageWriter.class.getCanonicalName(), "");
		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();
		for (int i = 0; i <= nY; i += interval) {
			for (int j = 0; j <= nX; j += interval) {
				imageWriter.writePixel(j, i, color);
			}
		}
	}

}
