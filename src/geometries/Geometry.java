package geometries;

import primitives.*;

/**
 * Geometry interface is the basic interface which all geometries would
 * implement.
 * 
 * @author Yahel and Ashi
 */
public abstract class Geometry extends Intersectable {

	protected Color emission = Color.BLACK;

	/**
	 * returns emission for the body
	 * 
	 * @return the emission's color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * @param emission the emission to set
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Returns the normal vector at a specified point on the surface of the
	 * geometry.
	 * 
	 * @param p0 The point to calculate the normal vector at.
	 * @return The normal vector at a specified point on the surface.
	 */
	public abstract Vector getNormal(Point p0);
}
