package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import primitives.Util;;

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
		if(Util.isZero(hight) || hight < 0) {
			throw new IllegalArgumentException("Hight  must be greater than zero");
		}
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

	@Override
	public String toString() {
		return "Cylinder{" + "\nhight=" + hight + "\n" + super.toString().substring(6);
	}

}
