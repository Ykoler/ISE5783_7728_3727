package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Triangle Class is the basic class representing a Triangle (a polygon of three
 * vertices)of Euclidean geometry in Cartesian 3-Dimensional coordinate system.
 * 
 * @author Yahel and Ashi
 */
public class Triangle extends Polygon {
	/**
	 * Constructs a new Triangle object with the specified vertices. The vertices
	 * must be provided in a counter-clockwise order when viewed from the outside.
	 * 
	 * @param p1 The first vertex of the triangle.
	 * @param p2 The second vertex of the triangle.
	 * @param p3 The third vertex of the triangle.
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> res = plane.findIntersections(ray);
		// Only check point if the ray intersects the plane of the triangle.
		if (res == null) {
			return null;
		}
		Point q = res.get(0);
		// Then, use barycentric coordinates technique to check if the intersection
		// point is inside the triangle
		// calculate the area of the respective triangle for each of the edges
		Point a = vertices.get(0);
		Point b = vertices.get(1);
		Point c = vertices.get(2);

		Vector ab, bc, aq, bq, cq, ac;
		double area, alpha, beta, gamma;

		try {
			ab = b.subtract(a); // a->b
			bc = c.subtract(b); // b->c
			ac = c.subtract(a); // a->c
			aq = q.subtract(a); // a->q
			bq = q.subtract(b); // b->q
			cq = q.subtract(c); // c->q
			// Alpha, beta, and gamma are calculated by the ratio between the respective
			// triangles and the entire one.
			area = ab.crossProduct(ac).length();
			alpha = ab.crossProduct(aq).length() / area;
			beta = bc.crossProduct(bq).length() / area;
			gamma = ac.crossProduct(cq).length() / area;
			// Point is inside if all the coordinates are positive
			if (alignZero(alpha) > 0 && alignZero(beta) > 0 && alignZero(gamma) > 0 && isZero(gamma + beta + alpha - 1))
				return List.of(q);
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
