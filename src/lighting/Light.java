
package lighting;

import primitives.*;

/**
 * Utility class to unite common aspects of lights
 * 
 * @author Yahel and Ashi
 */

abstract class Light {
	protected final Color intensity;

	/**
	 * Creates light source.
	 * 
	 * @param intensity the light's intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Returns the light source's intensity.
	 * 
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
