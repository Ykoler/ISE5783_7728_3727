package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane Class is the basic class representing a Plane of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Plane implements Geometry {

	/** The reference point of the plane. */
	final Point q0;
	/** The normal vector of the plane. */
	final Vector normal;

	/**
	 * Constructs a new Plane object with the specified reference point and normal
	 * vector.
	 * 
	 * @param q0     The reference point of the plane.
	 * @param normal The normal vector of the plane.
	 */
	public Plane(Point q0, Vector normal) {
		this.q0 = q0;
		this.normal = normal.normalize();
	}

	/**
	 * Constructs a new Plane object with three points on the plane.
	 * 
	 * @param p1 The first point of the plane.
	 * @param p2 The second point of the plane.
	 * @param p3 The third point of the plane.
	 */
	public Plane(Point p1, Point p2, Point p3) {
		if ((p1 == p2) || (p2 == p3) || (p1 == p3)) {
			throw new IllegalArgumentException("Two points are in the same place, unable to construct plane");
		}
		normal = null;
		this.q0 = p2;
	}

	@Override
	public Vector getNormal(Point p0) {
		return null;
	}

	/**
	 * Return the normal vector of the plane.
	 * 
	 * @return
	 */
	public Vector getNormal() {
		return null;
	}

	/**
	 * Returns the reference point of the plane.
	 * 
	 * @return The reference point of the plane.
	 */
	public Point getQ0() {
		return q0;
	}

	@Override
	public String toString() {
		return "Refrence point: " + q0.toString() + "\nNormal vector: " + normal.toString();
	}
}
