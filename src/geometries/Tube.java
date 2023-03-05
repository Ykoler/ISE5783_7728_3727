package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube extends RadialGeometry{
	final Ray axisRay;
	
	public Tube(double radius, Ray axisRay) {
		super(radius);
		this.axisRay = axisRay;
	}

	public Ray getAxisRay() {
		return axisRay;
	}
	
	public Vector getNormal(Point p) {
		return null;
	}
}
