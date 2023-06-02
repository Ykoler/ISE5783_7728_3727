
package renderer;

import static primitives.Util.*;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import static primitives.Ray.DELTA;

/**
 * Basic ray tracer.
 * 
 * @author Yahel and Ashi
 */

public class RayTracerBasic extends RayTracerBase {
	private static final int MAX_CALC_COLR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

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
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
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
			if (nl * nv > 0 && unshaded(gp, l, n, nl, lightSource)) {
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
		return mat.kD.scale(nl);
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
		return (alignZero(vr) > 0) ? Double3.ZERO : mat.kS.scale(Math.pow(-vr, mat.nShininess));
	}

	/**
	 * ###########################################################################
	 * 
	 * @param gp
	 * @param l
	 * @param n
	 * @return
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
		Vector lightDir = l.scale(-1);
		Ray lightRay = new Ray(gp.point.add(n.scale(nl < 0 ? DELTA : -DELTA)), lightDir);
		return scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point)) == null;
	}
}