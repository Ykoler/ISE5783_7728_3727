package primitives;

import static primitives.Util.isZero;
/**
 * Vector Class is the basic class representing a Vector of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Vector extends Point {
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (isZero(d1) && isZero(d2) && isZero(d3)) {
			throw new IllegalArgumentException("Vector Zero is not allowed");
		}

	}

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
		return new Vector(super.add(other).xyz);
	}

	public Vector scale(double scalar) {
		return new Vector(xyz.scale(scalar));
	}
	

	public double dotProduct(Vector other) {
		return (xyz.d1 * other.xyz.d1) + (xyz.d2 * other.xyz.d2) + (xyz.d3 * other.xyz.d3);
	}

	public Vector crossProduct(Vector other) {
		return new Vector(this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2,
				this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3,
				this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1);
	}
	
	public double lengthSquared() {
		return this.dotProduct(this);
	}
	
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	public Vector normalize() {
		return new Vector(xyz.reduce(this.length()));
	}
	
}
