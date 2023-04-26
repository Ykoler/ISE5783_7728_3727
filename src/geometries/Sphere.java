package geometries;

import java.util.List;
import java.lang.Math;
import primitives.*;

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
	public List<Point> findIntersections(Ray ray) {
		Point p0 = ray.getP0();
		Vector dir = ray.getDir();
		Vector hypotenuse = this.center.subtract(p0);
		double base = dir.dotProduct(hypotenuse);
		double perpendicular = Math.sqrt(hypotenuse.dotProduct(hypotenuse) - Math.pow(base, 2));
		double inside = Math.sqrt(Math.pow(this.radius, 2) - Math.pow(perpendicular, 2));
		if (base - inside > 0 && base + inside > 0)
			return List.of(ray.getPoint(base - inside), ray.getPoint(base + inside));
		else if (base - inside > 0)
			return List.of(ray.getPoint(base - inside), ray.getPoint(base + inside));
		if (base + inside > 0)
			return List.of(ray.getPoint(base - inside), ray.getPoint(base + inside));
		return null;
	}
}
