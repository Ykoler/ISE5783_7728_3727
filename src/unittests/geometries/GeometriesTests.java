/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Yahel Koler
 *
 */
class GeometriesTests {
	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Geometries geos = new Geometries(new Sphere(1d, new Point(6, 2, 0)),
				new Plane(new Point(2, 2, 1), new Vector(-1, 0, 0)),
				new Triangle(new Point(73, 12, 26), new Point(72, 15, 30), new Point(80, 18, 28)));

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line intersects with some geometries but not all (3 points)
		List<Point> result = geos.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, 0, 0)));
		assertEquals(3, result.size(), "There should be three intersections");

		// =============== Boundary Values Tests ==================
		// TC02: Ray does not intersect with any geometries (0 points)
		assertNull(geos.findIntersections(new Ray(new Point(0, 2, 0), new Vector(-1, 0, 0))),
				"There shouldn't be any intersections");

		// TC03: Ray only intersects one geometry (1 point)
		result = geos.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, -2, 0)));
		assertEquals(1, result.size(), "There should be one intersection");

		// TC04: No geometries exist (0 points)
		geos = new Geometries();
		assertNull(geos.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, 0, 2))),
				"There shouldn't be any intersections");

		// TC05: Ray's line intersects with all geometries (4 points)
		geos = new Geometries(new Sphere(1d, new Point(6, 2, 0)), new Plane(new Point(2, 2, 1), new Vector(-1, 0, 0)),
				new Triangle(new Point(19, -20, 26), new Point(20, 20, 1), new Point(18, -18, -28)));
		result = geos.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, 0, 0)));
		assertEquals(4, result.size(), "There should be four intersections");
	}

}
