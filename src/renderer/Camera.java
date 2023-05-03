package renderer;

import primitives.*;
import static primitives.Util.isZero;

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
		this.vRight = vUp.crossProduct(vTo).normalize();
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.p0 = p0;
	}

	/**
	 * Sets width and hight
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
	 * Sets distance
	 * 
	 * @param distance
	 * @return Camera that results
	 */
	public Camera setVPDistance(double distance) {
		this.distance = distance;
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
}
