
package renderer;

import java.awt.Color;

import primitives.Ray;
import scene.Scene;

/**
 * 
 * Basic ray tracer.
 * 
 * @author Yahel and Ashi
 */

public class RayTracerBasic extends RayTracerBase {

	/**
	 * Creates basic ray tracer
	 * 
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		return null;
	}

}
