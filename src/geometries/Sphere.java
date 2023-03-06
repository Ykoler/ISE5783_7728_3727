package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere Class is the basic class representing a Sphere of Euclidean geometry
 * in Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Sphere extends RadialGeometry {
	/** The center point of the sphere. */
	final Point center;

	/**
	 * Constructs a new Sphere object with the specified center point and radius.
	 * 
	 * @param p      The center point of the sphere.
	 * @param radius The radius of the sphere.
	 */
	public Sphere(Point p, double radius) {
		super(radius);
		this.center = p;
	}

	/**
	 * Returns the center point of the sphere.
	 * 
	 * @return The center point of the sphere.
	 */
	public Point getCenter() {
		return center;
	}

	public Vector getNormal(Point p0) {
		return null;
	}

	@Override
	public String toString() {
		return "Sphere{" + "\ncenter=" + center + "\nradius=" + radius + "\n}";
	}
}
