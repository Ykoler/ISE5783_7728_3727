package geometries;

public abstract class RadialGeomtry {
	final double radius;

	public RadialGeomtry(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}
}
