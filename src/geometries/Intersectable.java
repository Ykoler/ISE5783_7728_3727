/**
 * 
 */
package geometries;

import java.util.List;

import primitives.*;

/**
 * represents all objects that can be intersected with a ray.
 * @author Yahel and Ashi
 *
 */
public interface Intersectable {
	/**
	 * find intersections between a ray and a geometric shape. 
	 * @param ray
	 * @return list of intersection points
	 */
	List<Point> findIntersections(Ray ray);
}
