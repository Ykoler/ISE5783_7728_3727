/**
 * 
 */
package lighting;

import primitives.*;

/**
 * @author Yahel Koler
 *
 */
public class SpotLight extends PointLight implements LightSource {
	private final Vector direction;
	
	/**
	 * @param intensity
	 */
	public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction;
	}

}
