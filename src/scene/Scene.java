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

	public Scene(String name) {
		this.name = name;
	}

	/**
	 * @param background the background to set
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * @param ambientLight the ambientLight to set
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * @param geometries the geometries to set
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

	/**
	 * sets the scene's lights
	 * 
	 * @param lights
	 * @return the scene
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = new LinkedList<>(lights);
		return this;
	}
}
