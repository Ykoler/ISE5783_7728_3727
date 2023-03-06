package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * 
 * Tube class represents a tube in 3D Cartesian coordinate system
 * 
 * @author Yahel and Ashi
 */
public class Tube extends RadialGeometry {
	/** The axis ray of the tube. */
	final Ray axisRay;

	/**
	 * Constructs a new Tube object with the specified axis ray and radius.
	 * 
	 * @param axisRay The axis ray of the tube.
	 * @param radius  The radius of the tube.
	 */
	public Tube(double radius, Ray axisRay) {
		super(radius);
		this.axisRay = axisRay;
	}

	/**
	 * Returns the axis ray of the tube.
	 * 
	 * @return The axis ray of the tube.
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	public Vector getNormal(Point p) {
		return null;
	}

	@Override
	public String toString() {
		return "Tube{" + "\naxisRay=" + axisRay + "\nradius=" + radius + "\n}";
	}
}
