
package renderer;

import static primitives.Util.isZero;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

/**
 * @author Yahel and Ashi
 */

public class TargetArea {
	private final static double DISTANCE = 100;
	private final static int DENSITY = 5;
	private final Point p0;
	private final Vector vRight, vUp, vTo;
	private double width, height, distance;

	public TargetArea(Ray ray, double size) {
		p0 = ray.getP0();
		vTo = ray.getDir();
		double a = vTo.getX(), b = vTo.getY(), c = vTo.getZ();
		vRight = (a == b && b == c) ? new Vector(0, -a, a).normalize() : new Vector(b - c, c - a, a - b).normalize();
		vUp = vRight.crossProduct(vTo);
		this.height = this.width = size;
		this.distance = DISTANCE;
	}

	public TargetArea(Point p0, Vector vTo, Vector vUp) {
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight = vTo.crossProduct(vUp);
		this.p0 = p0;
	}

	/**
	 * Sets width and height in builder pattern
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets distance in builder pattern
	 * 
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Constructs ray from origin's location to a the center of a given pixel in a
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
		double xJ = (j - ((nX - 1) / 2.0)) * (width / nX);
		// Avoiding creation of zero vector (which is unnecessary anyway)
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));
		return new Ray(p0, pIJ.subtract(p0));
	}

	/**
	 * Constructs a grid of rays in the target area
	 * 
	 * @return list of rays
	 */
	public List<Ray> constructRayBeamGrid() {
		List<Ray> rays = new LinkedList<>();
		for (int i = 0; i < DENSITY; ++i)
			for (int j = 0; j < DENSITY; j++)
				rays.add(constructRay(DENSITY, DENSITY, j, i));
		return rays;
	}
}
