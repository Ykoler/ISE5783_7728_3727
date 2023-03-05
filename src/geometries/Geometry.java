package geometries;

import primitives.*;

/**
 * Geometry interface is the basic interface which all geometries would
 * implement.
 * 
 * @author Yahel and Ashi
 */
public interface Geometry {
	/**
	 * Returns the normal vector at a specified point on the geometry.
	 * 
	 * @param p0 The point to calculate the normal vector at.
	 * @return The normal vector at the specified point.
	 */
	public abstract Vector getNormal(Point p0);
}
