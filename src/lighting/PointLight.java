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
	private double kC, kL, kQ;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getL(Point p) {
		// TODO Auto-generated method stub
		return null;
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
