/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static primitives.Util.isZero;
import geometries.Plane;
import primitives.*;

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
}
