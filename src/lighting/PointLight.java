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
	private double kC = 1, kL = 0, kQ = 0;

	/**
	 * @param intensity
	 */
	public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = position;
		this.kC = kC;
		this.kL = kL;
		this.kQ = kQ;
	}

	@Override
	public Color getIntensity(Point p) {
		double dSquare = position.distanceSquared(p);
		return getIntensity().reduce(kC + kL * Math.sqrt(dSquare) + kQ * dSquare);
	}

	@Override
	public Vector getL(Point p) {
		return position.subtract(p).normalize();
	}

	/**
	 * @param kC the constant attenuation factor.
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * @param kL the linear attenuation factor.
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * @param kQ the quadratic attenuation factor.
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

}
