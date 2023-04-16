/**
 * 
 */
package unittests;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import static primitives.Util.isZero;

/**
 * Unit test for Vector class
 * 
 * @author Yahel and Ashi
 */

class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 */
	@Test
	void testVectorDoubleDoubleDouble() {
		// ============ Equivalence Partitions Tests ==============

		// =============== Boundary Values Tests ==================
		// TC01: Tests that zero vector throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0));
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(-2, -4, -6);
		// TC01: Test that the new vector is the right one
		assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: Vector + Vector does not work correctly");
		// =============== Boundary Values Tests ==================
		// TC01: Test opposite direction vector throws exception
		assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector (-1, -2, -3)),
				"ERROR: does not throw exception for adding two vectors with opposite direction");

	}

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */

    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-2, -4, -6);
        // TC01: Test that the new vector is the right one   
        assertEquals(v1.subtract(v2), new Vector(3, 6, 9), "ERROR: Vector - Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC01 Test Vector - Itself = Zero Vector
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: does not throw exception for subtracting a vector from itself");
    }

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v1 = new Vector(1, 3, 5);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that the new vector is the right one
		assertEquals(v1.scale(2), new Vector(2, 6, 10), "ERROR: scale() does not work correctly");

		// =============== Boundary Values Tests ==================

		// TC01: Test scaling by zero throws exception
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
				"ERROR: scale() does not throw exception for scaling by zero");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test vectors in acute angle
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(4, 5, 6);
		assertEquals(v1.dotProduct(v2), 32, "ERROR: dotProduct() for acute angle does not work correctly");
		// TC02: Test vectors in obtuse angle
		Vector v3 = new Vector(-1, -1, -3);
		assertEquals(v1.dotProduct(v3), -12, "ERROR: dotProduct() for obtuse angle does not work correctly");
		// ================== Boundary Values Tests ==================
		// TC01: Test vectors in 90 degree angle
		Vector v4 = new Vector(1, 4, -3);
		assertEquals(v1.dotProduct(v4), 0, 0.00001, "ERROR: dotProduct() for 90 degree angle does not work correctly");
		// TC02: Test vectors in 180 degree angle
		assertEquals(v1.dotProduct(v1.scale(-1.5)), -21, 0.00001,
				"ERROR: dotProduct() for 180 degree angle does not work correctly");
        // TC03: Text dotProduct when one of the vectors is the unit vector
        Vector v5 = new Vector(1, 0, 0);
        assertEquals(v1.dotProduct(v5), 1, 0.00001, "ERROR: dotProduct() for unit vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
		// ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
		// TC01: Test that the length of the cross-prouct is proper
        //Check that the length of the resulting vector is proper
        assertEquals(vr.length(), v1.length() * v3.length(), 0.00001,
                "ERROR: crossProduct() wrong result length");
        //Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "ERROR: crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "ERROR: crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC01: Test zero vector from cross-product of co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "ERROR: does not throw exception for cross-product of co-lined vectors");

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test length squared
        assertEquals(v1.lengthSquared(), 14, 0.00001, "ERROR: lengthSquared() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		Vector v1 = new Vector(0, 3, 4);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test length
        assertEquals(v1.length(), 5, 0.00001, "ERROR: length() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
        // TC01: Test normalize
        assertEquals(v1.normalize().length(), 1, 0.00001, "ERROR: normalize() result is not a unit vector");

    }
}