
package renderer;

import java.util.List;

import lighting.AmbientLight;
import primitives.*;
import scene.Scene;

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
		List<Point> intersectionPoints = scene.geometries.findIntersections(ray);
		if (intersectionPoints == null)
			return scene.background;
		return calcColor(ray.findClosestPoint(intersectionPoints));
	}

	private Color calcColor(Point p) {
		return scene.ambientLight.getIntensity();
	}

}
