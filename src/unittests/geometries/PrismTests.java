/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Prism;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * @author ashih
 *
 */
class PrismTests {

	Scene scene = new Scene("Prism Test");

//	Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//			.setVPSize(30, 30).setVPDistance(1000);

//	Camera camera = new Camera(new Point(0, 500, 1000), new Vector(0, -0.5, -1), new Vector(0, 1, -0.5)) //
//			.setVPSize(30, 30).setVPDistance(1000);

	Camera camera = new Camera(new Point(1000, 0, 1000), new Vector(-1, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(30, 30).setVPDistance(1000);

	@Test
	void testPrism() {
		scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

		scene.geometries
				.add(new Prism(new Point(0, 0, 0), new Point(10, 0, 0), new Point(10, 0, 10), new Point(0, 0, 10),
						new Point(0, 10, 0), new Point(10, 10, 0), new Point(10, 10, 10), new Point(0, 10, 10))
						.setMaterial(new Material().setKd(0.6).setKs(0.3).setShininess(3)).setEmission(Color.MAGENTA));
		scene.geometries.add(new Sphere(5, Point.ZERO)
				.setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(10)).setEmission(Color.MAGENTA));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("PrismTest", 800, 800);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

}
