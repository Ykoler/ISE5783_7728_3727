/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import geometries.Prism;
import geometries.Sphere;
import geometries.Triangle;
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

	Prism prism = new Prism(new Point(0, 0, 0), new Point(10, 0, 0), new Point(10, 0, 10), new Point(0, 0, 10),
			new Point(0, 10, 0), new Point(10, 10, 0), new Point(10, 10, 10), new Point(0, 10, 10));

	@Test
	void getNormalTest() {
		// ============ Equivalence Partitions Tests ==============
		Vector e1 = prism.getNormal(new Point(5, 10, 5));
		// TC01: Test that the normal is the right one
		assertEquals(new Vector(0, 1, 0), e1, "getNormal() wrong result");
	}

	@Test
	void findIntersectionsTest() {

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray intersects the prism
		List<Point> result = prism.findIntersections(new Ray(new Point(5, -2, 5), new Vector(0, 1, 0)));
		assertEquals(2, result.size(), "There should be two intersections");

		// TC02: Ray outside prism
		assertNull(prism.findIntersections(new Ray(new Point(-0.5, 0, 0), new Vector(-1, 0, 0))),
				"ERROR: findIntersections() did not return null");

		// ============ Equivalence Partitions Tests ==============
		// TC03: Ray from inside it intersects the prism
		result = prism.findIntersections(new Ray(new Point(5, 5, 5), new Vector(0, 1, 0)));
		assertEquals(1, result.size(), "There should be two intersections");

		// =============== Boundary Values Tests ==================
		// TC04: Ray on edge

	}

	Scene scene = new Scene("Prism Test");

//	Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//			.setVPSize(30, 30).setVPDistance(1000);

	Camera camera = new Camera(new Point(500, 500, 1000), new Vector(-0.5, -0.5, -1), new Vector(0.3, 0.7, -0.5)) //
			.setVPSize(30, 30).setVPDistance(1000);

//	Camera camera = new Camera(new Point(1000, 0, 500), new Vector(-1, 0, -1), new Vector(0, 1, 0)) //
//			.setVPSize(30, 30).setVPDistance(1000);

	@Test
	void testPrism() {
		scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

		scene.setBackground(Color.CYAN);
		scene.geometries
				.add(new Prism(new Point(0, 0, 0), new Point(10, 0, 0), new Point(10, 0, 10), new Point(0, 0, 10),
						new Point(0, 10, 0), new Point(10, 10, 0), new Point(10, 10, 10), new Point(0, 10, 10))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(7).setKt(0.4))
						.setEmission(Color.RED));
		scene.geometries.add(new Sphere(5, Point.ZERO)
				.setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(10)).setEmission(Color.MAGENTA));

		scene.lights.add(new SpotLight(new Color(70000, 40000, 40000), new Point(15, 60, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));
		

//
//		Ray ray = new Ray(new Point(5, 5, 5), new Vector(0, 1, 0));
//		tracer.traceRay(ray)
		
		ImageWriter imageWriter = new ImageWriter("PrismTest", 800, 800);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

}
