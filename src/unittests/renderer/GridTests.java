package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import renderer.*;
import primitives.*;

public class GridTests {

	@Test
	void getEntryPointTest() {
		Grid grid = new Grid(new Geometries(new Sphere(100, new Point(0, 0, 0))), 2);
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
		assertEquals(new Point(0, 0, 0), grid.gridEntryPoint(ray), "getEntryPoint() wrong result");

		assertEquals(new Point(0, 60, 100), grid.gridEntryPoint(new Ray(new Point(0, 60, 200), new Vector(0, 0, -1))),
				"getEntryPoint() wrong result");

		assertEquals(new Point(0, 0, 100), grid.gridEntryPoint(new Ray(new Point(0, 0, 200), new Vector(0, 0, -1))),
				"getEntryPoint() wrong result");

		assertNull(grid.gridEntryPoint(new Ray(new Point(0, 0, 200), new Vector(0, 1, 0))),
				"getEntryPoint() wrong result");

		assertEquals(new Point(0, 50, 100), grid.gridEntryPoint(new Ray(new Point(0, 0, 200), new Vector(0, 0.5, -1))),
				"getEntryPoint() wrong result");

	}

	// @Test
	// void testTraversal() {
	// Grid grid = new Grid(new Geometries(new Sphere(100, new Point(0, 0, 0))),
	// 10);
	// Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, -1));
	// Point entry = grid.gridEntryPoint(ray);
	// System.out.println(grid.voxelTraversal(entry, ray.getPoint(100 +
	// ray.getP0().distance(entry))));
	// }

}
