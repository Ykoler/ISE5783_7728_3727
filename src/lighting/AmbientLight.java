package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient lighting for scene
 * 
 * @author Yahel and Ashi
 */
public class AmbientLight {
	/** Black background */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	private final Color intensity;

	/**
	 * constructs Ambient light object with a given intensity and attenuation factor
	 * 
	 * @param iA intensity
	 * @param kA attenuation factor
	 */
	public AmbientLight(Color iA, Double3 kA) {
		this.intensity = iA.scale(kA);
	}

	/**
	 * constructs Ambient light object with a given intensity and attenuation factor
	 * 
	 * @param iA intensity
	 * @param kA attenuation factor
	 */
	public AmbientLight(Color iA, double kA) {
		this.intensity = iA.scale(kA);
	}

	/**
	 * returns the intensity of the light
	 * 
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
}
