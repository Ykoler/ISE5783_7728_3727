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
	List<Intersectable> geometries;

	/**
	 * Constructs a new empty geometries object representing a composite of
	 * intersectable objects.
	 */
	public Geometries() {
		geometries = new LinkedList<>();
	}

	/**
	 * Constructs a new geometries object representing a composite of intersectable
	 * objects with the given objects.
	 */
	public Geometries(Intersectable... geometries) {
		this.geometries = new LinkedList<>(List.of(geometries));
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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> result = null;
		List<GeoPoint> toAdd = null;
		for (Intersectable geo : geometries) {
			toAdd = geo.findGeoIntersections(ray);
			if (toAdd != null) {
				if (result == null) {
					result = new LinkedList<>();
				}
				result.addAll(toAdd);
			}
		}
		return result;
	}
}
