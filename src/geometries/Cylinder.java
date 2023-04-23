package geometries;

import primitives.*;
import static primitives.Util.isZero;

import java.util.List;

/**
 * Cylinder Class is the basic class representing a Cylinder (a tube with a
 * finite height) of Euclidean geometry in Cartesian 3-Dimensional coordinate
 * system.
 * 
 * @author Yahel and Ashi
 */
public class Cylinder extends Tube {
	/** The height of the cylinder. */
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
			throw new IllegalArgumentException("Hight must be greater than zero");
		}
		this.height = height;
	}

	@Override
	public Vector getNormal(Point p) {
		// Check that surface point is different from head of axisRay to avoid creating
		// a zero vector
		Vector dir = axisRay.getDir();
		Point p0 = axisRay.getP0();
		if (p.equals(p0))
			return dir.scale(-1);
		// Finding the nearest point to the given point that is on the axis ray
		double t = dir.dotProduct(p.subtract(p0));
		// Finds out if surface point is on a base and returns a normal appropriately
		if (isZero(t))
			return dir.scale(-1);
		if (t == height)
			return dir;
		// If surface point is on the side of the cylinder, the superclass (Tube) is
		// used to find the normal
		return super.getNormal(p);
	}

	/**
	 * Returns the height of the cylinder.
	 * 
	 * @return height of cylinder.
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder{" + "\nheight=" + height + "\n" + super.toString().substring(6);
	}
	
	@Override
	public List<Point> findIntersections(Ray ray){
		return null;
	}
}
