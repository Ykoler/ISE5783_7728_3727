package geometries;

import primitives.*;

/**
 * Geometry interface is the basic interface which all geometries would
 * implement.
 * 
 * @author Yahel and Ashi
 */
public abstract class Geometry extends Intersectable {
	private Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * Returns emission for the body
	 * 
	 * @return the emission's color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Returns geometry's material.
	 * 
	 * @return the geometry's material.
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets a geometry's emission.
	 * 
	 * @param emission the emission to set
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Sets a geometry's material.
	 * 
	 * @param material the material to set
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
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
