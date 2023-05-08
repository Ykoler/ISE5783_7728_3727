/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

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

}
