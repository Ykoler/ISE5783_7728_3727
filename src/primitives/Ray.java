package primitives;

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
		this.p0 = new Point(p0.xyz);
		this.dir = new Vector(dir.xyz).normalize();
	}

	/**
	 * Returns the starting point of the ray.
	 * 
	 * @return The starting point of the ray.
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * Returns the directional vector of the ray.
	 * 
	 * @return The directional vector of the ray.
	 */
	public Vector getDir() {
		return dir;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ray other)
			return p0.equals(other.p0) && dir.equals(other.dir);
		return false;
	}

	@Override
	public String toString() {
		return "Origin point: " + p0.toString() + "\nDirectional vector: " + dir.toString();
	}

}
