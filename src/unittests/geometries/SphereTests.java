/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
		Sphere s1 = new Sphere(new Point(1, 2, 3), 1);
		// ============ Equivalence Partitions Tests ==============
		Vector e1 = s1.getNormal(new Point(2, 2, 3));
		// TC01: Test that the normal is the right one
		assertEquals(new Vector(1, 0, 0), e1, "getNormal() wrong result");
	}

}
