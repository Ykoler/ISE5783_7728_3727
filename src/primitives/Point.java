package primitives;

/**
 * Point Class is the basic class representing a point of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */

public class Point {
	/** Coordinates of point */
	final Double3 xyz;

	/**
	 * Constructor to initialize Point using three number values
	 * 
	 * @param x for x coordinate
	 * @param y for y coordinate
	 * @param z for z coordinate
	 */
	public Point(double x, double y, double z) {
		xyz = new Double3(x, y, z);
	}

	/**
	 * Constructor to initialize Point using an object of the Double3 class
	 * 
	 * @param xyz values of the three coordinates
	 */
	Point(Double3 xyz) {
		this.xyz = xyz;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Point other)
			return this.xyz.equals(other.xyz);
		return false;
	}

	@Override
	public String toString() {
		return xyz.toString();
	}

	/**
	 * Sum the floating point triads of two points into a new triad where each
	 * couple of numbers is summarized in a new Point.
	 * 
	 * @param rhs right hand side operand for addition
	 * @return result of add
	 */
	public Point add(Vector rhs) {
		return new Point(this.xyz.add(rhs.xyz));
	}

	/**
	 * Subtract a floating point triad of one point from another, returning the
	 * resulting triad in the form of a new vector.
	 * 
	 * @param rhs right hand side operand for subtraction
	 * @return result of subtract
	 */
	public Vector subtract(Point rhs) {
		return new Vector(this.xyz.subtract(rhs.xyz));
	}

	/**
	 * Finds the square of the euclidean distance between two points using the
	 * pythagoras theorem.
	 * 
	 * @param other point from which to measure square of distance
	 * @return result of distanceSquared
	 */
	public double distanceSquared(Point other) {
		return (this.xyz.d1 - other.xyz.d1) * (this.xyz.d1 - other.xyz.d1)
				+ (this.xyz.d2 - other.xyz.d2) * (this.xyz.d2 - other.xyz.d2)
				+ (this.xyz.d3 - other.xyz.d3) * (this.xyz.d3 - other.xyz.d3);
	}

	/**
	 * Finds the euclidean distance between two points using the distanceSquared
	 * function.
	 * 
	 * @param other point from which to measure distance
	 * @return result of distance
	 */
	public double distance(Point other) {
		return Math.sqrt(this.distanceSquared(other));
	}
}