package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

	final Point q0;
	final Vector normal;

	public Plane(Point q0, Vector normal) {
		this.q0 = q0;
		this.normal = normal.normalize();
	}

	public Plane(Point p1, Point p2, Point p3) {
		if ((p1 == p2) || (p2 == p3) || (p1 == p3)) {
			throw new IllegalArgumentException("Two points are in the same place, unable to construct plane");
		}
		normal = null;
		this.q0 = p2;
	}

	@Override
	public Vector getNormal(Point p0) {
		return null;
	}

	public Vector getNormal() {

		return null;
	}

	public Point getQ0() {
		return q0;
	}

	@Override
	public String toString() {
		return "Refrence point: " + q0.toString() + "\nNormal vector: " + normal.toString();
	}
}
