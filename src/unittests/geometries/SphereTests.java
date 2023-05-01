/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import primitives.*;
import geometries.Sphere;

/**
 * Unit test for Sphere class
 * 
 * @author Yahel and Ashi
 */

class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Sphere s1 = new Sphere(1, new Point(1, 2, 3));
		// ============ Equivalence Partitions Tests ==============
		Vector e1 = s1.getNormal(new Point(2, 2, 3));
		// TC01: Test that the normal is the right one
		assertEquals(new Vector(1, 0, 0), e1, "getNormal() wrong result");
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
				"There shouldn't be any intersections");

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
		List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals(2, result.size(), "There should be two intersections");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Incorrect intersection points");

		// TC03: Ray starts inside the sphere (1 point)
		p1 = new Point(1, 1, 0);
		result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 2, 0)));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p1), result, "Incorrect intersection point");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0.5, 0), new Vector(1, 0, 0))),
				"There shouldn't be any intersections");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC05: Ray starts at sphere and goes inside (1 points)
		p1 = new Point(1, 1, 0);
		result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 1, 0)));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p1), result, "Incorrect intersection point");

		// TC06: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, 0, 2))),
				"There shouldn't be any intersections");

		// **** Group: Ray's line goes through the center
		// TC07: Ray starts before the sphere (2 points)
		p1 = new Point(0, 0, 0);
		p2 = new Point(2, 0, 0);
		result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
		assertEquals(2, result.size(), "There should be two intersections");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Incorrect intersection points");

		// TC08: Ray starts at sphere and goes inside (1 points)
		p1 = new Point(1, 1, 0);
		result = sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0)));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p1), result, "Incorrect intersection point");

		// TC09: Ray starts inside (1 points)
		p1 = new Point(2, 0, 0);
		result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p1), result, "Incorrect intersection point");

		// TC10: Ray starts at the center (1 points)
		p1 = new Point(1, 1, 0);
		result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p1), result, "Incorrect intersection point");

		// TC11: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, 1))),
				"There shouldn't be any intersections");

		// TC12: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
				"There shouldn't be any intersections");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC13: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))),
				"There shouldn't be any intersections");

		// TC24: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
				"There shouldn't be any intersections");

		// TC25: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0))),
				"There shouldn't be any intersections");

		// **** Group: Special cases
		// TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(1, 0, 0))),
				"There shouldn't be any intersections");
	}
}
