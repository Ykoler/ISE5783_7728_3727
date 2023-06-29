package geometries;

import java.util.List;
import java.lang.Math;
import primitives.*;
import static primitives.Util.*;

/**
 * Sphere Class is the basic class representing a Sphere of Euclidean geometry
 * in Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Sphere extends RadialGeometry {
	/** The center point of the sphere. */
	private final Point center;

	/**
	 * Constructs a new Sphere object with the specified center point and radius.
	 * 
	 * @param p      The center point of the sphere.
	 * @param radius The radius of the sphere.
	 */
	public Sphere(double radius, Point p) {
		super(radius);
		this.center = p;
		minX = center.getX() - radius;
		minY = center.getY() - radius;
		minZ = center.getZ() - radius;
		maxX = center.getX() + radius;
		maxY = center.getY() + radius;
		maxZ = center.getZ() + radius;
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
		return p0.subtract(center).normalize();
	}

	@Override
	public String toString() {
		return "Sphere{" + "\ncenter=" + center + "\nradius=" + radius + "\n}";
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		Point p0 = ray.getP0();
		Vector dir = ray.getDir();

		// Deals with case where ray starts from the center of the sphere
		if (p0.equals(center))
			return List.of(new GeoPoint(this, ray.getPoint(this.radius)));

		// Finding the hypotenuse, base and perpendicular of the triangle formed by
		// ray's starting point, the center of the sphere and the intersection point of
		// the ray and the perpendicular line crosing the sphere's center.
		Vector hypotenuse = this.center.subtract(p0);
		double base = dir.dotProduct(hypotenuse);
		double perpendicular = hypotenuse.lengthSquared() - base * base;
		double insideSquared = this.radiusSquared - perpendicular;

		// Dealing with a case in which the ray is perpendicular to the sphere at the
		// intersection point, or passes outside the Sphere.
		if (alignZero(insideSquared) <= 0)
			return null;

		// Returning intersection points, ensuring that only those intersected by the
		// ray are returned.
		double inside = Math.sqrt(insideSquared);
		double t2 = base + inside;
		double t1 = base - inside;
		if (alignZero(t2) <= 0 || alignZero(maxDistance - t1) <= 0)
			return null;

		boolean t2NotInRange = alignZero(maxDistance - t2) <= 0;

		if (alignZero(t1) <= 0) //
			return t2NotInRange //
					? null //
					: List.of(new GeoPoint(this, ray.getPoint(t2)));

		return t2NotInRange //
				? List.of(new GeoPoint(this, ray.getPoint(t1))) //
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	}
}
