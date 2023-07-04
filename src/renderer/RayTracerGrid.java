package renderer;

import java.util.List;

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

	public RayTracerGrid(Scene scene, int density) {
		super(scene);
		grid = new Grid(scene.geometries, density);
	}

	@Override
	public Color traceRay(Ray ray) {
		return super.traceRay(ray);
	}

	@Override
	protected GeoPoint findClosestIntersection(Ray ray) {
		Geometries outerGeometries = grid.getOuterGeometries();
		GeoPoint closestPoint = null;
		if (!outerGeometries.getGeometries().isEmpty())
			closestPoint = ray.findClosestGeoPoint(outerGeometries.findGeoIntersections(ray));
		double result = grid.cutsGrid(ray);
		double distanceToOuterGeometries = closestPoint == null ? Double.POSITIVE_INFINITY
				: closestPoint.point.distance(ray.getP0());

		if (distanceToOuterGeometries < result)
			return closestPoint;

		if (result == Double.POSITIVE_INFINITY)
			return null;
		// if(true)
		// return ray.findClosestGeoPoint(grid.getGeomet().findGeoIntersections(ray));

		Geometries geometries = grid.traverse();
		// while (!geometries.getGeometries().isEmpty()) {
		// GeoPoint point =
		// ray.findClosestGeoPoint(geometries.findGeoIntersections(ray));
		// if (point != null)
		// return point.point.distance(ray.getP0()) < distanceToOuterGeometries ? point
		// : closestPoint;
		// geometries = grid.traverse();
		// }
		if (!geometries.getGeometries().isEmpty()) {
			GeoPoint point = ray.findClosestGeoPoint(geometries.findGeoIntersections(ray));
			if (point != null)
				return point.point.distance(ray.getP0()) < distanceToOuterGeometries ? point : closestPoint;
		}
		return closestPoint;
	}

	@Override
	protected boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
		Vector lightDir = l.scale(-1);
		Ray lightRay = new Ray(gp.point, lightDir, n);
		List<GeoPoint> intersections;
		double res = grid.cutsGrid(lightRay);
		if (res == Double.POSITIVE_INFINITY)
			intersections = grid.getOuterGeometries().findGeoIntersections(lightRay);
		else {
			Geometries targets = grid.traverse(true);
			intersections = targets == null ? null
					: targets.findGeoIntersections(lightRay, light.getDistance(gp.point));
		}
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
		List<GeoPoint> intersections;
		double res = grid.cutsGrid(lightRay);
		if (res == Double.POSITIVE_INFINITY)
			intersections = grid.getOuterGeometries().findGeoIntersections(lightRay);
		else {
			Geometries targets = grid.traverse(true);
			intersections = targets == null ? null
					: targets.findGeoIntersections(lightRay, light.getDistance(gp.point));
		}
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
