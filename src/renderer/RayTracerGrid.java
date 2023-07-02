package renderer;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import regulargrid.Grid;
import scene.*;
import geometries.*;

/**
 * a class that represents a ray tracer that traces a grid of voxels(greatly
 * improves performance)
 * 
 * @autor Yahel and Ashi
 */
public class RayTracerGrid extends RayTracerBasic {

	private Grid grid;

	public RayTracerGrid(Scene scene) {
		super(scene);
	}

	@Override
	protected GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(grid.travese(ray).findGeoIntersections(ray));
	}

	@Override
	protected boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
		Vector lightDir = l.scale(-1);
		Ray lightRay = new Ray(gp.point, lightDir, n);

		var intersections = grid.travese(lightRay, true).findGeoIntersections(lightRay, light.getDistance(gp.point));
		if (intersections == null)
			return true;
		for (GeoPoint p : intersections)
			if (!p.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;
		return true;
	}

	@Override
	protected Double3 transparency(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
		Vector lightDir = l.scale(-1);
		Ray lightRay = new Ray(gp.point, lightDir, n);

		var intersections = grid.travese(lightRay, true).findGeoIntersections(lightRay, light.getDistance(gp.point));
		Double3 ktr = Double3.ONE;
		if (intersections == null)
			return ktr;
		for (GeoPoint p : intersections) {
			ktr = ktr.product(p.geometry.getMaterial().kT);
			if (ktr.lowerThan(MIN_CALC_COLOR_K))
				return Double3.ZERO;
		}
		return ktr;
	}
}
