/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

//import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.TargetArea;

/**
 * @author Yahel and Ashi 
 *
 */
class TargetAreaTests {

	TargetArea targetArea = new TargetArea(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 200);

	//@Test
	void testConstructRayBeamGrid() {
		List<Ray> result = targetArea.constructRayBeamGrid();
		for (Ray ray : result) {
			System.out.println(ray);
		}
		assertEquals(new Ray(new Point(0, 0, 0), new Vector(1, 0.5, -0.5).normalize()), result.get(0), "ERROR: findIntersections() did not return the right number of rays");
		
		assertEquals(9, result.size(), "ERROR: findIntersections() did not return the right number of rays");

		result = targetArea.constructRayBeamGrid().stream().filter(r -> r.getDir().dotProduct(new Vector(0, 1, 0)) <= 0)
				.collect(Collectors.toList());
		assertEquals(6, result.size(), "ERROR: findIntersections() did not return the right number of reflected rays");

		result = targetArea.constructRayBeamGrid().stream().filter(r -> r.getDir().dotProduct(new Vector(0, 1, 0)) > 0)
				.collect(Collectors.toList());
		assertEquals(3, result.size(), "ERROR: findIntersections() did not return the right number of refracted rays");
		
		
	}

}
