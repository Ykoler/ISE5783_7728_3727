package primitives;

import static primitives.Util.*;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Ray Class is the basic class representing a Ray of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Ray {
	/** The starting point of the ray. */
	final Point p0;

	/** The directional vector of the ray. */
	final Vector dir;

	public static final double DELTA = 0.00001;

	/**
	 * Creates a new Ray object with the specified starting point and directional
	 * vector. The directional vector will be normalized.
	 * 
	 * @param p0  The starting point of the ray.
	 * @param dir The directional vector of the ray.
	 */
	public Ray(Point p0, Vector dir) {
		this.p0 = p0;
		this.dir = dir.normalize();
	}

	/**
	 * New constructor for ray that also receives a normal vector. It then moves the
	 * ray's origin a short distance in the normal's direction.
	 * 
	 * @param p0     the original point
	 * @param dir    the direction vector
	 * @param normal the normal along which to move the origin point
	 */
	public Ray(Point p0, Vector dir, Vector normal) {
		double res = dir.dotProduct(normal);
		this.p0 = isZero(res) ? p0 : res > 0 ? p0.add(normal.scale(DELTA)) : p0.add(normal.scale(-DELTA));
		this.dir = dir.normalize();
	}

	/**
	 * Returns the starting point of the ray.
	 * 
	 * @return starting point of ray.
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * Returns the directional vector of the ray.
	 * 
	 * @return directional vector of ray.
	 */
	public Vector getDir() {
		return dir;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Ray other && p0.equals(other.p0) && dir.equals(other.dir);
	}

	@Override
	public String toString() {
		return "Origin point: " + p0 + "\nDirectional vector: " + dir;
	}

	/**
	 * Returns a point at a distance of t from the origin in the direction of the
	 * vector
	 * 
	 * @param t scalar
	 * @return the desired point
	 */
	public Point getPoint(double t) {
		try {
			return p0.add(dir.scale(t));
		} catch (Exception e) {
			return p0;
		}
	}

	/**
	 * Returns the closest point to the ray's origin point
	 * 
	 * @param points points to check
	 * @return closest point
	 */
	public Point findClosestPoint(List<Point> intersections) {
		return intersections == null || intersections.isEmpty() ? null
				: findClosestGeoPoint(intersections.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * Returns the closest GeoPoint along the ray.
	 * 
	 * @param points GeoPoints to check
	 * @return closest GeoPoint
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
		GeoPoint closest = null;
		double d = Integer.MAX_VALUE;
		double calcD;

		if (points == null)
			return null;

		// For each point, checks if it's closer than the previous, and if so, replaces
		// it
		for (GeoPoint point : points) {
			calcD = point.point.distanceSquared(p0);
			if (calcD < d) {
				closest = point;
				d = calcD;
			}
		}
		return closest;
	}
}
