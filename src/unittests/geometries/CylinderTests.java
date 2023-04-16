/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Cylinder;

/**
 * Unit test for Cylinder class
 * 
 * @author Yahel and Ashi
 */
class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Cylinder c1 = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 1, 0)), 1, 5);

		// ============ Equivalence Partitions Tests ==============
		Vector e1 = c1.getNormal(new Point(1, 3, 2));
		Vector e2 = c1.getNormal(new Point(1, 1, 1.5));
		Vector e3 = c1.getNormal(new Point(1, 6, 1.5));

		// TC01: Test that the normal is the right one
		assertEquals(new Vector(0, 0, 1), e1, "getNormal() wrong result");

		// TC02: Test that getNormal works for the bottom base
		assertEquals(new Vector(0, -1, 0), e2, "getNormal() didn't work properly for the bottom base");

		// TC03: Test that getNormal works for the top base
		assertEquals(new Vector(0, 1, 0), e3, "getNormal() didn't work properly for the top base");

		// =============== Boundary Values Tests ==================
		Vector b1 = c1.getNormal(new Point(1, 1, 1));
		Vector b2 = c1.getNormal(new Point(1, 6, 1));

		// TC01: Test that getNormal works for the center point of the bottom base
		assertEquals(new Vector(0, -1, 0), b1,
				"getNormal() didn't work properly for the center point of the bottom base");

		// TC02: Test that getNormal works for the center point of the top base
		assertEquals(new Vector(0, 1, 0), b2, "getNormal() didn't work properly for the center point of the top base");
	}

}
