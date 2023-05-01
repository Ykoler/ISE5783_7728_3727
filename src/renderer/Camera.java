package renderer;

import primitives.*;
import static primitives.Util.isZero;

/**
 * @author Yahel and Ashi
 *
 */
public class Camera {
	private Point p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double hight;
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
	 * @return the hight
	 */
	public double getHight() {
		return hight;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	public Camera(Point p0, Vector vUp, Vector vTo) {
		if (!isZero(vUp.dotProduct(vTo)))
			throw new IllegalArgumentException("ERROR: given vectors weren't perpendicular");
		this.vRight = vUp.crossProduct(vTo).normalize();
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
	}
}
