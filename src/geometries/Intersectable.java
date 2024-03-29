package geometries;

import java.util.List;

import primitives.*;

/**
 * Represents all objects that can be intersected with a ray.
 * 
 * @author Yahel and Ashi
 */
public abstract class Intersectable {

	double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY,
			maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;

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
			return obj instanceof GeoPoint other && (this.geometry == other.geometry)
					&& (this.point.equals(other.point));
		}

		@Override
		public String toString() {
			return this.point + " " + this.geometry;
		}
	}

	/**
	 * Find intersections between a ray and a geometric shape.
	 * 
	 * @param ray
	 * @return list of intersection points
	 */
	public final List<Point> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Find intersections between a ray and a group of geometric objects, returning
	 * GeoPoints instead of points.
	 * 
	 * @param ray ray with which to find intersections
	 * @return list of intersections (in the form of GeoPoints)
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * Find intersections between a ray and a group of geometric objects, returning
	 * GeoPoints closer than the max distance instead of points.
	 * 
	 * @param ray         ray with which to find intersections
	 * @param maxDistance max distance of intersection points
	 * @return list of intersections (in the form of GeoPoints)
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		return findGeoIntersectionsHelper(ray, maxDistance);
	}

	/**
	 * Find intersections between a ray and any Intersectable, returning GeoPoints
	 * closer than the max distance instead of points.
	 * 
	 * @param ray         ray with which to find intersections
	 * @param maxDistance max distance of intersection points
	 * @return list of intersections (in the form of GeoPoints)
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

	/**
	 * #####################################################################
	 * 
	 * @return
	 */
	public final List<Double> getEdges() {
		return List.of(minX, minY, minZ, maxX, maxY, maxZ);
	}

}
