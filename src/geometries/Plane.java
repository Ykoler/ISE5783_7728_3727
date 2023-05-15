package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;
import static geometries.Intersectable.GeoPoint;

/**
 * Plane Class is the basic class representing a Plane of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Plane extends Geometry {

	/** The reference point of the plane. */
	private final Point q0;
	/** The normal vector of the plane. */
	private final Vector normal;

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
		// calculate the normal vector of the plane
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		this.normal = v1.crossProduct(v2).normalize();
		this.q0 = p2;
	}

	@Override
	public Vector getNormal(Point p0) {
		return normal;
	}

	/**
	 * Return the normal vector of the plane.
	 * 
	 * @return normal to plane.
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * Returns the reference point of the plane.
	 * 
	 * @return reference point of plane.
	 */
	public Point getQ0() {
		return q0;
	}

	@Override
	public String toString() {
		return "Refrence point: " + q0 + ", Normal vector: " + normal;
	}

	/**
	 * @param ray
	 * @return
	 */
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Point p0 = ray.getP0();
		Vector v = ray.getDir();
		// Ray begins at the plane's reference point (will cause a zero vector)
		if (q0.equals(p0)) {
			return null;
		}
		double nv = normal.dotProduct(v);
		// Ray is parallel to the plane
		if (isZero(nv)) {
			return null;
		}
		double t = (q0.subtract(p0)).dotProduct(normal) / nv;
		// Checking if intersection is behind the start of the ray
		if (alignZero(t) <= 0) {
			return null;
		}
		return List.of(new GeoPoint(this, ray.getPoint(t)));
	}
}
