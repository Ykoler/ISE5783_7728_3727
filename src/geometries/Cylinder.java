package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;;

/**
 * Cylinder Class is the basic class representing a Cylinder (a tube with a
 * finite hight) of Euclidean geometry in Cartesian 3-Dimensional coordinate
 * system.
 * 
 * @author Yahel and Ashi
 */
public class Cylinder extends Tube {
	/** The hight of the cylinder. */
	final double hight;

	/**
	 * Constructs a new Cylinder object with the specified axis ray, radius and
	 * hight.
	 * 
	 * @param axisRay The axis ray of the cylinder.
	 * @param radius  The radius of the cylinder.
	 * @param hight   The hight of the cylinder.
	 */
	public Cylinder(double hight, Ray axisRay, double radius) {
		super(radius, axisRay);
		this.hight = hight;
	}

	@Override
	public Vector getNormal(Point p0) {
		return null;
	}

	/**
	 * Returns the hight of the cylinder.
	 * 
	 * @return The hight of the cylinder.
	 */
	public double getHight() {
		return hight;
	}

}
