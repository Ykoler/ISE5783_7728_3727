
package renderer;

import static primitives.Util.isZero;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import primitives.*;

/**
 * @author Yahel and Ashi
 */

public class TargetArea {
	private final static double DISTANCE = 100;
	private int density = 6;
	private final Point p0;
	private final Vector vRight, vUp, vTo;
	private double width, height, distance;
	private Random random = new Random();

	/**
	 * Constructor for TargetArea class
	 * 
	 * @param ray  the main ray to the target area
	 * @param size the size of the target area
	 */
	public TargetArea(Ray ray, double size) {
		p0 = ray.getP0();
		vTo = ray.getDir();
		vRight = vTo.makePerpendicularVector();
		vUp = vRight.crossProduct(vTo);
		this.height = this.width = size;
		this.distance = DISTANCE;
	}

	/**
	 * Constructor for TargetArea class
	 * 
	 * @param p0  the origin point of the target area
	 * @param vTo the direction vector of the target area
	 * @param vUp the up vector of the target area
	 */
	public TargetArea(Point p0, Vector vTo, Vector vUp) {
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight = vTo.crossProduct(vUp);
		this.p0 = p0;
	}

	/**
	 * Sets width and height
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets distance
	 * 
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Sets density of rays.
	 * 
	 * @param density
	 */
	public void setDensity(int density) {
		this.density = density;
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
	public Ray constructRay(int nX, int nY, double j, double i) {
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
	 * Constructs a grid of rays in the target area in jittered distribution.
	 * 
	 * @return list of rays
	 */
	public List<Ray> constructRayBeamGrid() {
		List<Ray> rays = new LinkedList<>();
		for (int i = 0; i < density; ++i)
			for (int j = 0; j < density; j++)
				rays.add(constructRay(density, density, random.nextDouble() - 0.5 + j, random.nextDouble() - 0.5 + i));
		return rays;
	}
}
