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
