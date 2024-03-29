package lighting;

import primitives.*;

/**
 * Implementation of a spotlight.
 * 
 * @author Yahel and Ashi
 */
public class SpotLight extends PointLight implements LightSource {
	private final Vector direction;

	// Sets the level of width for the light's ray
	private double narrowness = 1;

	/**
	 * Creates a spot light.
	 * 
	 * @param intensity light's intensity
	 * @param position  light source's location
	 * @param kC        constant attenuation factor
	 * @param kL        linear attenuation factor
	 * @param kQ        quadratic attenuation factor
	 * @param direction direction in which the light is pointing
	 */
	public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalize();
	}

	/**
	 * Default constructor for a spot light.
	 * 
	 * @param intensity light's intensity
	 * @param position  point light's position
	 * @param direction direction in which the light is pointing
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity(p).scale(Math.pow(Math.max(0, direction.dotProduct(super.getL(p))), narrowness));
	}

	/**
	 * Enables the creation of a more narrow spot light.
	 * 
	 * @param narrowness how narrow the light should be (increasing the parameter's
	 *                   size decreases width of ray)
	 * @return the light
	 */
	public SpotLight setNarrowBeam(double narrowness) {
		this.narrowness = narrowness;
		return this;
	}
}
