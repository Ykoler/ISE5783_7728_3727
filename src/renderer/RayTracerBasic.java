
package renderer;

import java.util.List;
import static primitives.Util.*;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

/**
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

	/**
	 * Calculates the color at a certain point as caused by local effects (the
	 * result of various light sources and the geometry's emission light). This is
	 * done using the Phong model for light propagation.
	 * 
	 * @param gp  the point at which the light intensity is calculated
	 * @param ray camera ray for which the aforementioned GeoPoint was found
	 * @return the color at the point
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		Material mat = gp.geometry.getMaterial();

		// returns no color in the case that camera ray is perpendicular to the normal
		// at the point's location
		if (nv == 0)
			return color;

		// For each light source in the scene, if the light is on the same side of the
		// object as the camera, it's affect on the point's color is added
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(mat, nl < 0 ? -nl : nl)),
						iL.scale(calcSpecular(mat, n, l, nl, v)));
			}
		}
		return color;
	}

	/**
	 * Calculates the effect of diffusive light return at the point
	 * 
	 * @param mat the material of the geometry the point belongs to
	 * @param nl  dot product of the normal at the point and the ray from the light
	 *            source to the point
	 * @return the diffusive color return at the point
	 */
	private Double3 calcDiffusive(Material mat, double nl) {
		return mat.Kd.scale(nl);
	}

	/**
	 * Calculates the effect of specular light return at the point
	 * 
	 * @param mat the material of the geometry the point belongs to
	 * @param n   normal at the point
	 * @param l   the ray from the light source to the point in question
	 * @param nl  dot product of the normal at the point and the ray from the light
	 *            source to the point
	 * @param v   the direction of the camera ray
	 * @return the specular color return at the point
	 */
	private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
		double vr = v.dotProduct(l.subtract(n.scale(nl * 2)));
		return (alignZero(vr) <= 0) ? Double3.ZERO : mat.Ks.scale(powr(-vr, mat.nShininess));
	}

	/**
	 * A homemade function to calculate the power of a number (made only for
	 * integers so as to save on runtime).
	 * 
	 * @param b the base
	 * @param e the exponent
	 * @return the result
	 */
	private double powr(double b, int e) {
		double res = 1;
		for (int i = 0; i < e; ++i)
			res *= b;
		for (int i = 0; i > e; --i)
			res /= b;
		return res;
	}

}
