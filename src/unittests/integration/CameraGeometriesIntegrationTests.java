/**
 * 
 */
package unittests.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import renderer.*;
import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;

/**
 * Testing integration of Camera and geometries
 * 
 * @author Yahel and Ashi
 */
class CameraGeometriesIntegrationTests {
	static final Point ZERO_POINT = new Point(0, 0, 0);
	static final String wrongIntersection = "Wrong number of intersections";

	private int sendRays(Camera cam, Intersectable obj, int nX, int nY) {
		int intersectionCount = 0;
		List<Point> tmp = null;

		for (int x = 0; x < nX; x++) {
			for (int y = 0; y < nY; y++) {
				tmp = obj.findIntersections(cam.constructRay(nX, nY, y, x));
				if (tmp != null)
					intersectionCount += tmp.size();
			}
		}
		return intersectionCount;
	}

	@Test
	void cameraSphereIntersectionsTest() {
		Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3).setVPDistance(1);

		// TC01: Two intersection points
		assertEquals(2, sendRays(cam, new Sphere(1, new Point(0, 0, -3)), 3, 3), wrongIntersection);

		cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3)
				.setVPDistance(1);

		// TC02: Eighteen intersection points
		assertEquals(18, sendRays(cam, new Sphere(2.5, new Point(0, 0, -2.5)), 3, 3), wrongIntersection);

		// TC03: Ten intersection points
		assertEquals(10, sendRays(cam, new Sphere(2, new Point(0, 0, -2)), 3, 3), wrongIntersection);

		// TC04: Nine intersection points
		assertEquals(9, sendRays(cam, new Sphere(4, new Point(0, 0, 0)), 3, 3), wrongIntersection);

		// TC05: Zero intersection points
		assertEquals(0, sendRays(cam, new Sphere(0.5, new Point(0, 0, 1)), 3, 3), wrongIntersection);
	}

	@Test
	void cameraPlaneIntersectionsTest() {
		Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3).setVPDistance(1);
		// TC01: Nine intersection points
		assertEquals(9, sendRays(cam, new Plane(new Point(0, 0, -3), new Vector(0, 0, 1)), 3, 3), wrongIntersection);

		// TC02: Nine intersection points
		assertEquals(9, sendRays(cam, new Plane(new Point(0, 0, -3), new Vector(0, 0.2, -1)), 3, 3), wrongIntersection);

		// TC03: Six intersection points
		assertEquals(6, sendRays(cam, new Plane(new Point(0, 0, -3), new Vector(0, 1, -1)), 3, 3), wrongIntersection);
	}

	@Test
	void cameraTriangleIntersectionsTest() {
		Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3).setVPDistance(1);
		// TC01: One intersection points
		assertEquals(1,
				sendRays(cam, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3),
				wrongIntersection);

		// TC01: Two intersection points
		assertEquals(2,
				sendRays(cam, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3),
				wrongIntersection);

	}
}
