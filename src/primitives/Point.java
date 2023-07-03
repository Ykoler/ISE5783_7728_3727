package primitives;

/**
 * Point Class is the basic class representing a point of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */

public class Point {

	public static final Point ZERO = new Point(0, 0, 0);

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
		return obj instanceof Point other && this.xyz.equals(other.xyz);
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
		double dx = this.xyz.d1 - other.xyz.d1;
		double dy = this.xyz.d2 - other.xyz.d2;
		double dz = this.xyz.d3 - other.xyz.d3;
		return dx * dx + dy * dy + dz * dz;
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

	public Point divideSize(int div) {
		return new Point(this.getX() / div, this.getY() / div, this.getZ() / div);
	}

	public double getX() {
		return xyz.d1;
	}

	public double getY() {
		return xyz.d2;
	}

	public double getZ() {
		return xyz.d3;
	}
}