package lighting;

import primitives.*;

/**
 * Implementation of directional light.
 * 
 * @author Yahel and Ashi
 */
public class DirectionalLight extends Light implements LightSource {
	private final Vector direction;

	/**
	 * @param intensity
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

}
