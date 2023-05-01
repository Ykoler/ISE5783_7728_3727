package primitives;

import static primitives.Util.*;

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

	public Point getPoint(double t) {
		return isZero(t) ? p0 : p0.add(dir.scale(t));
	}
}
