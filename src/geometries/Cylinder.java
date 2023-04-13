package geometries;

import primitives.*;

/**
 * Cylinder Class is the basic class representing a Cylinder (a tube with a
 * finite height) of Euclidean geometry in Cartesian 3-Dimensional coordinate
 * system.
 * 
 * @author Yahel and Ashi
 */
public class Cylinder extends Tube {
	/** The hight of the cylinder. */
	private final double height;

	/**
	 * Constructs a new Cylinder object with the specified axis ray, radius and
	 * height.
	 * 
	 * @param axisRay The axis ray of the cylinder.
	 * @param radius  The radius of the cylinder.
	 * @param height  The height of the cylinder.
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		if (Util.alignZero(height) <= 0) {
			throw new IllegalArgumentException("Hight  must be greater than zero");
		}
		this.height = height;
	}

	@Override
	public Vector getNormal(Point p0) {
		return null;
	}

	/**
	 * Returns the hight of the cylinder.
	 * 
	 * @return hight of cylinder.
	 */
	public double getHight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder{" + "\nhight=" + height + "\n" + super.toString().substring(6);
	}

}
