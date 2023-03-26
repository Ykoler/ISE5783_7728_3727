package geometries;

import primitives.Util;

/**
 * Class RadialGeomtry is the basic class representing a Vector of Euclidean
 * geometry in Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public abstract class RadialGeometry implements Geometry {
	/** The radius of the geometry. */
	final double radius;

	/**
	 * Constructs a new RadialGeometry object with the specified radius.(the class
	 * is abstract so we can't create an object from it but the constructor is used
	 * used to initialize the radius of the geometry).
	 * 
	 * @param radius The radius of the geometry.
	 */
	public RadialGeometry(double radius) {
		if(Util.isZero(radius) || radius < 0) {
			throw new IllegalArgumentException("Radius must be greater than zero");
		}
		this.radius = radius;
	}

	/**
	 * Returns the radius of the geometry.
	 * 
	 * @return The radius of the geometry.
	 */
	public double getRadius() {
		return radius;
	}
}
