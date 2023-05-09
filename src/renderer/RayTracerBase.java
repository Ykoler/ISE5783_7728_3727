package renderer;

import java.awt.Color;

import primitives.*;
import scene.Scene;

/**
 * Class implementing a ray tracer for any scene.
 * 
 * @author Yahel and Ashi
 */
public abstract class RayTracerBase {
	protected Scene scene;

	/**
	 * Creates a ray tracer for a given scene.
	 * 
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Recieves ray and returns the color of the nearest intersection point on the
	 * ray.
	 * 
	 * @param ray
	 * @return color of the closest intersection point on the ray
	 */
	public abstract Color traceRay(Ray ray);
}
