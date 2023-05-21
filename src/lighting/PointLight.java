package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Implementation of a point light source.
 * 
 * @author Yahel and Ashi
 */
public class PointLight extends Light implements LightSource {
	private final Point position;
	private double Kc = 1, Kl = 0, Kq = 0;

	/**
	 * Creates a point light.
	 * 
	 * @param intensity light's intensity
	 * @param position  light source's location
	 * @param kC        constant attenuation factor
	 * @param kL        linear attenuation factor
	 * @param kQ        quadratic attenuation factor
	 */
	public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = position;
		this.Kc = kC;
		this.Kl = kL;
		this.Kq = kQ;
	}

	/**
	 * Default constructor for a point light.
	 * 
	 * @param intensity light's intensity
	 * @param position  point light's position
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	@Override
	public Color getIntensity(Point p) {
		// Calculates lights intensity at a point given it's distance from the point
		// light
		double dSquare = position.distanceSquared(p);
		return getIntensity().reduce(Kc + Kl * Math.sqrt(dSquare) + Kq * dSquare);
	}

	@Override
	public Vector getL(Point p) {
		return p.subtract(position).normalize();
	}

	/**
	 * Sets the constant attenuation factor.
	 * 
	 * @param kC the constant attenuation factor.
	 */
	public PointLight setKc(double Kc) {
		this.Kc = Kc;
		return this;
	}

	/**
	 * Sets the linear attenuation factor.
	 * 
	 * @param kL the linear attenuation factor.
	 */
	public PointLight setKl(double Kl) {
		this.Kl = Kl;
		return this;
	}

	/**
	 * Sets the quadratic attenuation factor.
	 * 
	 * @param kQ the quadratic attenuation factor.
	 */
	public PointLight setKq(double Kq) {
		this.Kq = Kq;
		return this;
	}
}
