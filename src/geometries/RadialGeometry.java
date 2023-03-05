package geometries;
/**
 * Class RadialGeomtry is the basic class representing a Vector of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public abstract class RadialGeometry {
	final double radius;

	public RadialGeometry(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}
}
