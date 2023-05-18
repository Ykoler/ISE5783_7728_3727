
package renderer;

import java.util.List;
import static primitives.Util.*;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

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
		return calcColor(ray.findClosestGeoPoint(intersectionPoints), ray);
	}

	/**
	 * Calculates the color at a certain point of intersection.
	 * 
	 * @param p intersection point
	 * @return color at the point
	 */
	private Color calcColor(GeoPoint p, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(p, ray));
	}

	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		Material mat = gp.geometry.getMaterial();
		if (nv == 0)
			return color;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(mat, Math.abs(nl))), iL.scale(calcSpecular(mat, n, l, nl, v)));
			}
		}
		return color;
	}

	private Double3 calcDiffusive(Material mat, double nl) {
		return mat.Kd.scale(nl);
	}

	private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
		return mat.Ks.scale(powr(Math.max(0, -v.dotProduct(l.subtract(n.scale(nl * 2)))), mat.nShininess));
	}

	private double powr(double b, int e) {
		double res = 1;
		for (int i = 0; i < e; ++i)
			res *= b;
		for (int i = 0; i > e; --i)
			res /= b;
		return res;
	}

}
