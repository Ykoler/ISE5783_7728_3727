package lighting;

import primitives.*;

/**
 * @author Yahel and Ashi
 *
 */
public class SpotLight extends PointLight implements LightSource {
	private final Vector direction;
	private double width = 1;

	/**
	 * @param intensity
	 */
	public SpotLight(Color intensity, Point position, double Kc, double Kl, double Kq, Vector direction) {
		super(intensity, position, Kc, Kl, Kq);
		this.direction = direction.normalize();
	}

	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity().scale(Math.pow(Math.max(0, direction.dotProduct(super.getL(p))), width));
	}

	public SpotLight setNarrowBeam(double narrowness) {
		this.width = narrowness;
		return this;
	}
}
