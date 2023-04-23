package geometries;

import primitives.*;
import static primitives.Util.isZero;

import java.util.List;

/**
 * 
 * Tube class represents a tube in 3D Cartesian coordinate system
 * 
 * @author Yahel and Ashi
 */
public class Tube extends RadialGeometry {
	/** The axis ray of the tube. */
	protected final Ray axisRay;

	/**
	 * Constructs a new Tube object with the specified axis ray and radius.
	 * 
	 * @param axisRay The axis ray of the tube.
	 * @param radius  The radius of the tube.
	 */
	public Tube(Ray axisRay, double radius) {
		super(radius);
		this.axisRay = axisRay;
	}

	/**
	 * Returns the axis ray of the tube.
	 * 
	 * @return axis ray of tube.
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	public Vector getNormal(Point p) {
		// Finding the nearest point to the given point that is on the axis ray
		Vector dir = axisRay.getDir();
		Point p0 = axisRay.getP0();
		double t = dir.dotProduct(p.subtract(p0));
		Point o;
		if (!isZero(t))
			o = p0.add(dir.scale(t));
		else
			o = p0;
		// Returning the subtraction of one from the other
		return p.subtract(o).normalize();
	}

	@Override
	public String toString() {
		return "Tube{" + "\naxisRay=" + axisRay + "\nradius=" + radius + "\n}";
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}