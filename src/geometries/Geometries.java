package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

/**
 * A class representing a number of objects capable of intersecting with a ray
 * using the composite design pattern.
 * 
 * @author Yahel and Ashi
 *
 */
public class Geometries extends Intersectable {
	List<Intersectable> geometries = new LinkedList<>();

	/**
	 * Constructs a new empty geometries object representing a composite of
	 * intersectable objects.
	 */
	public Geometries() {
	}

	/**
	 * Constructs a new geometries object representing a composite of intersectable
	 * objects with the given objects.
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * Adds a given list of objects to the composite.
	 * 
	 * @param geometries list of objects to be added
	 */
	public void add(Intersectable... geometries) {
		this.geometries.addAll(List.of(geometries));
		for (Intersectable element : geometries) {
			List<Double> edges = element.getEdges();
			double tMinX = edges.get(0), tMinY = edges.get(1), tMinZ = edges.get(2), tMaxX = edges.get(3),
					tMaxY = edges.get(4), tMaxZ = edges.get(5);
			minX = tMinX < minX ? tMinX : minX;
			minY = tMinY < minY ? tMinY : minY;
			minZ = tMinZ < minZ ? tMinZ : minZ;
			maxX = x > maxX ? x : maxX;
			maxY = y > maxY ? y : maxY;
			maxZ = z > maxZ ? z : maxZ;
		}
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		List<GeoPoint> result = null;
		for (Intersectable geo : geometries) {
			var toAdd = geo.findGeoIntersections(ray, maxDistance);
			if (toAdd != null) {
				if (result == null)
					result = new LinkedList<>();
				result.addAll(toAdd);
			}
		}
		return result;
	}

}
