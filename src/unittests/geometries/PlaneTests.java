/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static primitives.Util.isZero;

import java.util.List;

import geometries.Plane;
import geometries.Prism;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.RayTracerGrid;
import scene.Scene;

/**
 * @author Yahel and Ashi
 *
 */
class PlaneTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testPlanePointPointPoint() {
		// ============ Boundary Values Tests ==================
		// TC01: Test two points are the same
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 4)),
				"ERROR: does not throw exception for two points are the same");

		// TC02: Test three points are on the same line
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(3, 6, 9)),
				"ERROR: does not throw exception for three points are on the same line");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normal is the right one
		Plane p = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
		Vector result = p.getNormal(new Point(0, 0, 1));
		// test the length is 1
		assertEquals(1, result.length(), 0.00001, "ERROR: the length of the normal is not 1");
		// check the normal in orthogonal to the plane
		assertTrue(isZero(result.dotProduct(new Vector(0, -1, 1))));
		assertTrue(isZero(result.dotProduct(new Vector(-1, 1, 0))));
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Plane p = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray intersects the plane (the ray is not orthogonal or parallel to the
		// plane)
		List<Point> result = p.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, -1)));
		assertEquals(1, result.size(), "ERROR: findIntersections() did not return the right number of points");
		assertEquals(List.of(new Point(0.3, 0.1, 0.6)), result, "Incorrect intersection points");

		// TC02: Ray does not intersect the plane
		assertNull(p.findIntersections(new Ray(new Point(0, 0, 1.5), new Vector(1, 0, 1.5))),
				"ERROR: findIntersections() did not return null when the ray does not intersect the plane");

		// =============== Boundary Values Tests ==================
		// **** Group: the ray is parallel to the plane
		// TC03: Ray is parallel to the plane and included in the plane
		assertNull(p.findIntersections(new Ray(new Point(0.5, 0.25, 0.25), new Vector(-0.5, 0.2, 0.3))),
				"ERROR: findIntersections() did not return null when the ray is parallel to the plane and included in the plane");

		// TC04: ray is parallel to the plane and not included in the plane
		assertNull(p.findIntersections(new Ray(new Point(0.6, 0.25, 0.25), new Vector(-0.5, 0.2, 0.3))),
				"ERROR: findIntersections() did not return null when the ray is parallel to the plane and not included in the plane");

		// **** Group: the ray is orthogonal to the plane
		// TC05: Ray is orthogonal to the plane and begins before the plane
		result = p.findIntersections(new Ray(new Point(0.6, 0.25, 0.25), new Vector(-1, -1, -1)));
		assertEquals(1, result.size(),
				"ERROR: findIntersections() returned incorrect number of points when the ray is orthogonal to the plane and begins before the plane");

		// TC06: Ray is orthogonal to the plane and begins in the plane
		assertNull(p.findIntersections(new Ray(new Point(0.5, 0.25, 0.25), new Vector(-1, -1, -1))),
				"ERROR: findIntersections() did not return null when the ray is orthogonal to the plane and begins in the plane");

		// TC07: Ray is orthogonal to the plane and begins after the plane
		assertNull(p.findIntersections(new Ray(new Point(0.4, 0.25, 0.25), new Vector(-1, -1, -1))),
				"ERROR: findIntersections() did not return null when the ray is orthogonal to the plane and begins after the plane");

		// **** Group: the ray is neither orthogonal nor parallel to the plane
		// TC08: Ray begins in the plane
		assertNull(p.findIntersections(new Ray(new Point(0.5, 0.25, 0.25), new Vector(-3, 5, 2))),
				"ERROR: findIntersections() did not return null when the ray begins in the plane");

		// Ray is neither orthogonal nor parallel to the plane and begins in the same
		// point which appears as reference point in the plane
		assertNull(p.findIntersections(new Ray(p.getQ0(), new Vector(-3, 5, 2))),
				"ERROR: findIntersections() did not return null when the ray begins in the same point which appears as reference point in the plane");
	}

	@Test
	void planeRenderTest() {
		Scene scene = new Scene("Test scene");

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.setBackground(new Color(10, 150, 180));

		scene.geometries.add(

				new Plane(new Point(0, -20, 0), new Vector(0, 1, 0)).setMaterial(new Material().setKd(0.1))
						.setEmission(Color.ORANGE));

		scene.lights.add(new SpotLight(new Color(1500, 1300, 3000), new Point(600, 500, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("PlaneRenderTest", 700, 700);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerGrid(scene)) //
				.renderImage() //
				.writeToImage();
	}
}
