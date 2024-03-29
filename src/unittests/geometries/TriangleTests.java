/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;
import geometries.*;
import lighting.*;

import static primitives.Util.isZero;

import java.util.List;

/**
 * @author Yahel and Ashi
 *
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Triangle t = new Triangle(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
		// TC01: Test that the normal is the right one
		Vector result = t.getNormal(new Point(0, 0, 1));
		// test the length is 1
		assertEquals(1, result.length(), 0.00001, "ERROR: the length of the normal is not 1");
		// check the normal in orthogonal to all the edges
		assertTrue(isZero(t.getNormal(new Point(0, 0, 1)).dotProduct(new Vector(0, 1, -1))),
				"ERROR: the normal is not orthogonal to the 1st edge");
		assertTrue(isZero(t.getNormal(new Point(0, 0, 1)).dotProduct(new Vector(1, -1, 0))),
				"ERROR: the normal is not orthogonal to the 2nd edge");
		assertTrue(isZero(t.getNormal(new Point(0, 0, 1)).dotProduct(new Vector(-1, 0, 1))),
				"ERROR: the normal is not orthogonal to the 3rd edge");
	}

	/**
	 * Test method for
	 * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		// all tests assume a point on the plane in which the triangle is on and check
		// if the function identifies whether the point is inside the triangle or not
		Triangle t = new Triangle(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray intersects the triangle
		List<Point> result = t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, -1)));
		assertEquals(1, result.size(), "ERROR: findIntersections() did not return the right number of points");
		assertEquals(List.of(new Point(0.3, 0.1, 0.6)), result, "Incorrect intersection points");

		// TC02: Ray outside against edge
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-2, -0.5, -1))),
				"ERROR: findIntersections() did not return null");

		// TC03: Ray outside against vertex
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(1, -0.5, -1))),
				"ERROR: findIntersections() did not return null");

		// =============== Boundary Values Tests ==================
		// TC04: Ray on edge
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -0.1, -0.4))),
				"ERROR: findIntersections() did not return null");

		// TC05: Ray on vertex
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, 0.5, -1))),
				"ERROR: findIntersections() did not return null");

		// TC06: Ray on edge's continuation
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, 0.5))),
				"ERROR: findIntersections() did not return null");
	}

	@Test
	void triangleRenderTest() {
		Scene scene = new Scene("Test scene");

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.setBackground(new Color(10, 150, 180));

		scene.geometries.add(new Triangle(new Point(-100, 0, -100), new Point(100, 0, -100), new Point(0, 100, -100)) //
				.setEmission(new Color(java.awt.Color.BLUE)),
				new Triangle(new Point(-100, -100, -100), new Point(100, -100, -100), new Point(0, 100, -100)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)));

		scene.lights.add(new SpotLight(new Color(1500, 1300, 3000), new Point(600, 500, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new PointLight(new Color(500, 250, 250), new Point(-200, -200, 0)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("TriangleRenderTest", 800, 800);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerGrid(scene, 2)) //
				.renderImage() //
				.writeToImage();
	}
}
