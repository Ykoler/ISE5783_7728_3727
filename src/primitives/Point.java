package primitives;

/**
 * Class Point is the basic class representing a point of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Point {
	
	final Double3 xyz;
	
	public Point(double x,double y,double z) {
		xyz = new Double3(x,y,z);
	}
	
	Point(Double3 xyz){
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
	public String toString(){
		return xyz.toString();
	}
	
	public Point add(Vector vectorToAdd) {
		return new Point(this.xyz.add(vectorToAdd.xyz));
	}
	
	public Vector subtract(Point toSubtract) {
		return new Vector(this.xyz.subtract(toSubtract.xyz));
	}
	
	public double distanceSquared(Point other) {
		return (this.xyz.d1 - other.xyz.d1)*(this.xyz.d1 - other.xyz.d1) + (this.xyz.d2 - other.xyz.d2)*(this.xyz.d2 - other.xyz.d2) + (this.xyz.d3 - other.xyz.d3)*(this.xyz.d3 - other.xyz.d3); 
	}
	
	public double distance(Point other) {
		return Math.sqrt(this.distanceSquared(other));
	}
}