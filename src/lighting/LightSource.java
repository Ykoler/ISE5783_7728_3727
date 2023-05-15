package lighting;

import primitives.*;

/**
 * Interface for light sources.
 * 
 * @author Yahel and Ashi
 */
public interface LightSource {
	/**
	 * Returns the intensity of the light from a certain light source at a certain
	 * point.
	 * 
	 * @param p the point at which to find the color
	 * @return the color at the point
	 */
	public Color getIntensity(Point p);

	/**
	 * Returns the direction from the light source to the point in question.
	 * 
	 * @param p the intersection point
	 * @return the vector from the light source to the point
	 */
	public Vector getL(Point p);
}
