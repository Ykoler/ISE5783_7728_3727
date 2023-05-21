package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import lighting.LightSource;

/**
 * Contains elements of scene
 * 
 * @author Yahel and Ashi
 */

public class Scene {
	public final String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();
	public List<LightSource> lights = new LinkedList<>();

	/**
	 * Creates and names a scene
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * Sets the scene's background
	 * 
	 * @param background the background to set
	 * @return the scene
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * Sets the scene's ambient light
	 * 
	 * @param ambientLight the ambient light to set
	 * @return the scene
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * Sets the scene's geometries
	 * 
	 * @param geometries the geometries to set
	 * @return the scene
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

	/**
	 * Sets the scene's lights
	 * 
	 * @param lights list of the lights
	 * @return the scene
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = new LinkedList<>(lights);
		return this;
	}
}
