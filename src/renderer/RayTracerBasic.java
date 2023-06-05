
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
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.01;
	private static final double INITIAL_K = 1.0;

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

	/**
	 * Function to prevent breaking DRY that returns the closest intersection point
	 * along a ray.
	 * 
	 * @param ray the ray with which the geometries will intersect
	 * @return the closest geopoint
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * Calculates the color at a certain point of intersection by using a separate
	 * recursive funtion. This function sends the initial values for the recursion.
	 * 
	 * @param p   intersection point
	 * @param ray from camera to the point
	 * @return color at the point
	 */
	private Color calcColor(GeoPoint p, Ray ray) {
		return calcColor(p, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Additional calcColor function enabling recursion based on depth and light
	 * intensity.
	 * 
	 * @param gp    the point at which to calculate the light
	 * @param ray   ray from camera to the point
	 * @param level depth of recursion
	 * @param k     weight of light at a certain point in recursion
	 * @return if bottom level reached - color at the point, otherwise - the color
	 *         at the point, in addition to the added affects achieved by the
	 *         deepening of the recursion
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
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
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
				// Checks shade percentage between the light source and the point and only
				// calculates the intensity from a given light as a factor of the transparency.
				Double3 ktr = transparency(gp, l, n, nl, lightSource);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(mat, nl < 0 ? -nl : nl)),
							iL.scale(calcSpecular(mat, n, l, nl, v)));
				}
			}
		}
		return color;
	}

	/**
	 * Recursively returns the added affects for two additional rays emanating from
	 * the intersection point: the reflected ray and the refracted ray.
	 * 
	 * @param gp    the point from which to send the secondary rays
	 * @param ray   the from the camera to the intersection point
	 * @param level the depth of recursion
	 * @param k     the weight of light at the current point in the recursion
	 * @return the color obtained from the secondary rays.
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffects(constructReflectedRay(gp, v, n), level, k, material.kR)
				.add(calcGlobalEffects(constructRefractedRay(gp, v, n), level, k, material.kT));
	}

	/**
	 * Checks that the weight of the light intensity at this point in the recursion
	 * is above the minimum (if not, black is returned) and proceeds to find the
	 * nearest intersection along that ray, adding it's affects if necessary,
	 * returning black or the background otherwise (if the camera ray is
	 * perpendicular to the normal or no intersection point exists, accordingly).
	 * 
	 * @param ray   camera ray
	 * @param level depth of recursion
	 * @param k     current weight for light intensity at this point in recursion
	 * @param kx    weight to be added given the increasing level of recursion
	 * @return the color to be returned along the given secondary ray.
	 */
	private Color calcGlobalEffects(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		// Checks if weight for light intensity is below minimum
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		// Returns background color if no intersection points are found
		if (gp == null)
			return scene.background.scale(kx);
		// If they are, color at the nearest intersection is returned
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
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
		double vr = v.dotProduct(l.mirror(n, nl));
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

		var intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		if (intersections == null)
			return true;
		for (GeoPoint p : intersections)
			if (!p.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;
		return true;
	}

	/**
	 * For a point, light source and the ray between them, all intersections capable
	 * of creating shade have their affects added by scaling in accordance with
	 * their level of transparency.
	 * 
	 * @param gp
	 * @param l
	 * @param n
	 * @return
	 */
	private Double3 transparency(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
		Vector lightDir = l.scale(-1);
		Ray lightRay = new Ray(gp.point.add(n.scale(nl < 0 ? DELTA : -DELTA)), lightDir);

		var intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		Double3 ktr = Double3.ONE;
		if (intersections == null)
			return ktr;
		for (GeoPoint p : intersections)
			ktr = ktr.product(p.geometry.getMaterial().kT);

		return ktr;
	}

	/**
	 * ###########################################################################
	 * 
	 * @param ray
	 * @param p
	 * @param n
	 * @return
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector dir, Vector n) {
		return new Ray(gp.point, dir.mirror(n, dir.dotProduct(n)), n);
	}

	/**
	 * ###########################################################################
	 * 
	 * @param ray
	 * @param p
	 * @param n
	 * @return
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector dir, Vector n) {
		return new Ray(gp.point, dir, n);
	}
}