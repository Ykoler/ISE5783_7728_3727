package primitives;

/**
 * Implementation of a material as a defining factor of a geometry, enabling the
 * creation of changes in geometries' colors and attributes.
 * 
 * @author Yahel and Ashi
 */
public class Material {
	public Double3 kD = Double3.ZERO, /** diffusive attenuation factor */
			kS = Double3.ZERO, /** specular attenuation factor */
			kT = Double3.ZERO, /** transparency attenuation factor */
			kR = Double3.ZERO;
	public double kG = 0, kB = 0;
	/** reflective attenuation factor */

	public int nShininess = 0;

	/**
	 * Sets diffusive attenuation factor.
	 * 
	 * @param Kd the diffusive attenuation factor.
	 * @return the scene
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * Sets diffusive attenuation factor.
	 * 
	 * @param kD the diffusive attenuation factor.
	 * @return the scene
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets specular attenuation factor.
	 * 
	 * @param Ks the specular attenuation factor
	 * @return the scene
	 */
	public Material setKs(double Ks) {
		this.kS = new Double3(Ks);
		return this;
	}

	/**
	 * Sets specular attenuation factor.
	 * 
	 * @param Ks the specular attenuation factor
	 * @return the scene
	 */
	public Material setKs(Double3 Ks) {
		this.kS = Ks;
		return this;
	}

	/**
	 * Sets transparency attenuation factor.
	 * 
	 * @param kT the transparency attenuation factor.
	 * @return the scene
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}

	/**
	 * Sets transparency attenuation factor.
	 * 
	 * @param kT the transparency attenuation factor.
	 * @return the scene
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Sets reflective attenuation factor.
	 * 
	 * @param kR the reflective attenuation factor.
	 * @return the scene
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}

	/**
	 * Sets reflective attenuation factor.
	 * 
	 * @param kR the reflective attenuation factor.
	 * @return the scene
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * Sets Glossy attenuation factor.
	 * 
	 * @param kR the Glossy attenuation factor.
	 * @return the material
	 */
	public Material setKg(double kG) {
		this.kG = kG;
		return this;
	}

	/**
	 * Sets Blur attenuation factor.
	 * 
	 * @param kR the Blur attenuation factor.
	 * @return the material
	 */
	public Material setKb(double kB) {
		this.kB = kB;
		return this;
	}

	/**
	 * Sets the level of shininess.
	 * 
	 * @param nShininess the shininess to set
	 * @return the scene
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
