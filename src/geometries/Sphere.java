package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeomtry {
	final Point center;

	public Sphere(Point p, double radius) {
		super(radius);
		this.center = p;
	}

	public Point getCenter() {
		return center;
	}

	public Vector getNormal(Point p0) {
		return null;
	}

}
