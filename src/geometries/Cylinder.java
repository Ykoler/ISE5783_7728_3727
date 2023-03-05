package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;;

public class Cylinder extends Tube {
	final double hight;

	public Cylinder(double hight, Ray axisRay, double radius) {
		super(radius, axisRay);
		this.hight = hight;
	}

	@Override
	public Vector getNormal(Point p0) {
		return null;
	}

	public double getHight() {
		return hight;
	}

}

