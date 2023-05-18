package lighting;

import primitives.*;

/**
 * 
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
		return getIntensity();
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

}
