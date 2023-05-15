package geometries;

import primitives.*;

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
		// Finding the offset of the nearest point to the given point that is on the
		// axis ray
		double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
		// Returning the subtraction of one from the other
		return p.subtract(axisRay.getPoint(t)).normalize();
	}

	@Override
	public String toString() {
		return "Tube{" + "\naxisRay=" + axisRay + "\nradius=" + radius + "\n}";
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		return null;
	}
}