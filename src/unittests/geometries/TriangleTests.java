/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Triangle;
import static primitives.Util.isZero;

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
}
