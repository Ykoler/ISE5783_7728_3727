package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * Unit test for primitives.Point class
 * 
 * @author Yahel and Ashi
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		Point p1 = new Point(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
		Point e1 = p1.add(v1);

		// TC01: Test that the new point is the right one
		assertEquals(e1, new Point(2, 4, 6), "add() wrong result");

		// =============== Boundary Values Tests ==================

	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		Point p1 = new Point(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Point p2 = new Point(2, 4, 6);
		Point e1 = p1.subtract(p2);

		// TC01: Test that the new point is the right one
		assertEquals(e1, new Vector(1, 2, 3), "subtract() wrong result");

		// =============== Boundary Values Tests ==================
		// TC01: Test that exception is thrown for zero vector
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
				"subtract() does not throw an exception for subtracting point from itself");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		Point p1 = new Point(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Point p2 = new Point(2, 4, 6);
		double e1 = p1.distanceSquared(p2);

		// TC01: Test that the new point is the right one
		assertEquals(e1, 3, 0.00001, "distanceSquared() wrong result");

		// =============== Boundary Values Tests ==================
		// TC01: Test that the new point is the right one
		assertEquals(e1, new Point(2, 4, 6), "add() wrong result");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		fail("Not yet implemented");
	}

}
