/**
 * 
 */
package geometries;

import java.util.List;

import primitives.*;

/**
 * represents all objects that can be intersected with a ray.
 * 
 * @author Yahel and Ashi
 *
 */
public abstract class Intersectable {

	public static class GeoPoint {
		/** the geometry which the point is on */
		public Geometry geometry;
		/** the point itself */
		public Point point;

		/**
		 * Creating a GeoPoint
		 * 
		 * @param geometry the geometry to which the point belongs
		 * @param point    the point itself
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return obj instanceof GeoPoint other && (this.point == other.point) && (this.geometry == other.geometry);
		}

		@Override
		public String toString() {
			return this.point + " " + this.geometry;
		}
	}

	/**
	 * find intersections between a ray and a geometric shape.
	 * 
	 * @param ray
	 * @return list of intersection points
	 */
	public final List<Point> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * ########################################################################################
	 * 
	 * @param ray
	 * @return
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * ########################################################################################
	 * 
	 * @param ray
	 * @return
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
