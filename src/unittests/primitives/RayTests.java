/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Yahel and Ashi
 *
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
		Point p1 = new Point(1, 2, 3);
		Vector v1 = new Vector(1, 0, 0);
		Ray r = new Ray(p1, v1);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that the new point is the right one
		assertEquals(new Point(2, 2, 3), r.getPoint(1), "getPoint() wrong result");
		// ============ Boundary Values Tests ==============

		// TC02: Test that the new point is the right one
		assertEquals(p1, r.getPoint(0), "Didn't work for t=0");

	}

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(List<Point>)}.
	 */
	@Test
	void testFindClosestPoint() {
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 0));
		Point a = new Point(2, 2, 0);
		Point b = new Point(3, 3, 0);
		Point c = new Point(5, 5, 0);
		List<Point> points = List.of(b, a, c);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Closest point is in the middle of the list
		assertEquals(a, ray.findClosestPoint(points), "Returned wrong result");

		// ============ Boundary Values Tests ==============
		points = List.of();
		// TC02: list is empty (should return null)
		assertNull(ray.findClosestPoint(points), "Should have returned null");

		// TC03: closest point is at start of list
		points = List.of(a, b, c);
		assertEquals(a, ray.findClosestPoint(points), "Returned wrong result");

		// TC04: closest point is at end of list
		points = List.of(b, c, a);
		assertEquals(a, ray.findClosestPoint(points), "Returned wrong result");
	}

}
