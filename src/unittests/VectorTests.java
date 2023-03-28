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
		assertThrows(IllegalArgumentException.class, ()-> new Vector(0, 0, 0));
	}


	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(2, 3, 4);
		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v1 = new Vector(0, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(0, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v1 = new Vector(0, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		Vector v1 = new Vector(0, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		Vector v1 = new Vector(0, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v1 = new Vector(0, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: 

		// =============== Boundary Values Tests ==================
		
		// TC01:
		fail("Not yet implemented");
	}

}
