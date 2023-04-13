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
	public Vector getNormal(Point p0) {
		if (p0.equals(axisRay.getP0()))
			return axisRay.getDir().normalize().scale(-1);
		double t = axisRay.getDir().dotProduct(p0.subtract(axisRay.getP0()));
		if (t==0)
			return axisRay.getDir().normalize().scale(-1);
		if (t==height)
			return axisRay.getDir().normalize();
		return super.getNormal(p0);
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

}
