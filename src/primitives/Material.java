package primitives;

/**
 * Implementation of a material as a defining factor of a geometry, enabling the
 * creation of changes in geometries' colors and attributes.
 * 
 * @author Yahel and Ashi
 */
public class Material {
	public Double3 Kd = Double3.ZERO, Ks = Double3.ZERO;
	public int nShininess = 0;

	/**
	 * Sets diffusive attenuation factor.
	 * 
	 * @param Kd the diffusive attenuation factor.
	 * @return the scene
	 */
	public Material setKd(double Kd) {
		this.Kd = new Double3(Kd);
		return this;
	}

	/**
	 * Sets diffusive attenuation factor.
	 * 
	 * @param Kd the diffusive attenuation factor.
	 * @return the scene
	 */
	public Material setKd(Double3 Kd) {
		this.Kd = Kd;
		return this;
	}

	/**
	 * Sets specular attenuation factor.
	 * 
	 * @param Ks the specular attenuation factor
	 * @return the scene
	 */
	public Material setKs(double Ks) {
		this.Ks = new Double3(Ks);
		return this;
	}

	/**
	 * Sets specular attenuation factor.
	 * 
	 * @param Ks the specular attenuation factor
	 * @return the scene
	 */
	public Material setKs(Double3 Ks) {
		this.Ks = Ks;
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
