/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author Yahel Koler
 *
 */
public class PointLight extends Light implements LightSource {
	private final Point position;
	private double Kc = 1, Kl = 0, Kq = 0;

	/**
	 * @param intensity
	 */
	public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = position;
		this.Kc = kC;
		this.Kl = kL;
		this.Kq = kQ;
	}

	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	@Override
	public Color getIntensity(Point p) {

		return getIntensity().reduce(getReduction(p));
	}

	@Override
	public Vector getL(Point p) {
		return p.subtract(position).normalize();
	}

	/**
	 * @param kC the constant attenuation factor.
	 */
	public PointLight setKc(double Kc) {
		this.Kc = Kc;
		return this;
	}

	/**
	 * @param kL the linear attenuation factor.
	 */
	public PointLight setKl(double Kl) {
		this.Kl = Kl;
		return this;
	}

	/**
	 * @param kQ the quadratic attenuation factor.
	 */
	public PointLight setKq(double Kq) {
		this.Kq = Kq;
		return this;
	}

	protected double getReduction(Point p) {
		double dSquare = position.distanceSquared(p);
		return (Kc + Kl * Math.sqrt(dSquare) + Kq * dSquare);
	}

}
