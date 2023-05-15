
package renderer;

import java.util.List;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * 
 * Basic ray tracer.
 * 
 * @author Yahel and Ashi
 */

public class RayTracerBasic extends RayTracerBase {

	/**
	 * Creates basic ray tracer
	 * 
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
		if (intersectionPoints == null)
			return scene.background;
		return calcColor(ray.findClosestGeoPoint(intersectionPoints));
	}

	/**
	 * Calculates the color at a certain point of intersection.
	 * 
	 * @param p intersection point
	 * @return color at the point
	 */
	private Color calcColor(GeoPoint p) {
		return scene.ambientLight.getIntensity().add(p.geometry.getEmission());
	}

}
