/**
 * 
 */
package lighting;

import primitives.*;

/**
 * @author Yahel and Ashi
 *
 */
public class SpotLight extends PointLight implements LightSource {
	private final Vector direction;

	/**
	 * @param intensity
	 */
	public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(super.getL(p))));
	}
}
