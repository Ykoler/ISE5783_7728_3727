package primitives;

/**
 * Vector Class is the basic class representing a Vector of Euclidean geometry
 * in Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Vector extends Point {
	/**
	 * Constructor to initialize Vector using three number values
	 * 
	 * @param x for x coordinate
	 * @param y for y coordinate
	 * @param z for z coordinate
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("Vector Zero is not allowed");
		}

	}

	/**
	 * Constructor to initialize Vector using an object of the Double3 class
	 * 
	 * @param xyz values of the three coordinates
	 */
	Vector(Double3 xyz) {
		super(xyz);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("Vector Zero is not allowed");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Vector other)
			return super.equals(other);
		return false;
	}

	@Override
	public Vector add(Vector other) {
		return new Vector(other.xyz.add(this.xyz));
	}

	/**
	 * Scale (multiply) Vector by a number into a new Vector where each coordinate
	 * is multiplied by the number
	 * 
	 * @param scalar right hand side operand for scaling
	 * @return result of scale
	 */
	public Vector scale(double scalar) {
		return new Vector(xyz.scale(scalar));
	}

	/**
	 * Calculate dot product between two vectors where result is the sum of the
	 * multiplication of equivalent coordinates of the two vectors. the result is
	 * calculated by the following formula:
	 * 
	 * <pre>
	 * a1 a2 a3
	 * b1 b2 b3
	 * </pre>
	 * 
	 * where the result is: <i>a1*b1 + a2*b2 + a3*b3</i>
	 * 
	 * @param other right hand side operand for dot product
	 * @return result of dotProduct
	 */
	public double dotProduct(Vector other) {
		/*
		 * Calculate dot product between two vectors where result is the sum of the
		 * multiplication of equivalent coordinates of the two vectors. where the result
		 * is: a1*b1 + a2*b2 + a3*b3
		 */
		return (xyz.d1 * other.xyz.d1) + (xyz.d2 * other.xyz.d2) + (xyz.d3 * other.xyz.d3);
	}

	/**
	 * Calculate cross product between two vectors
	 * 
	 * @param other right hand side operand for cross product
	 * @return result of crossProduct
	 */
	public Vector crossProduct(Vector other) {
		/*
		 * between two vectors where result is a new vector which is perpendicular to
		 * both vectors. where the result is: (a2*b3 - a3*b2, a3*b1 - a1*b3, a1*b2 -
		 * a2*b1)
		 */
		return new Vector(this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2,
				this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3,
				this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1);
	}

	/**
	 * Calculate the squared length of the vector as the dot product of the vector
	 * with itself
	 * 
	 * @return result of lengthSquared
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/**
	 * Calculate the length of the vector as the square root of the function
	 * lengthSquared
	 * 
	 * @return result of length
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * Calculate the normalized vector by dividing each coordinate by the length of
	 * the vector
	 * 
	 * @return result of normalized vector
	 */
	public Vector normalize() {
		return new Vector(xyz.reduce(this.length()));
	}

	/**
	 * Returns the mirror image of a vector relative to the normal
	 * 
	 * @param n  the normal
	 * @param nl dot product of the vector to be mirrored and the normal
	 * @return the resulting vector
	 */
	public Vector mirror(Vector n, double nl) {
		return this.subtract(n.scale(nl * 2));
	}

	/**
	 * When given a vector, the function creates a vector perpendicular to it.
	 * 
	 * @return the resulting vector
	 */
	public Vector makePerpendicularVector() {
		double a = getX(), b = getY(), c = getZ();
		return (a == b && b == c) ? new Vector(0, -a, a).normalize() : new Vector(b - c, c - a, a - b).normalize();
	}
}