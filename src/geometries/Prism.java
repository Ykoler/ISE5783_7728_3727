package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * 
 * @author Yahel and Ashi
 */

public class Prism extends Geometry {
	public List<Polygon> sides = new LinkedList<>();
	private final double EPS = 0.01;
	private final Point center;

	/**
	 * Creates a prism comprised of rectangular polygons given eight points.
	 * 
	 * @param bottomLeftBack
	 * @param bottomRightBack
	 * @param bottomRightFront
	 * @param bottomLeftFront
	 * @param topLeftBack
	 * @param topRightBack
	 * @param topRightFront
	 * @param topLeftFront
	 */
	public Prism(Point bottomLeftBack, Point bottomRightBack, Point bottomRightFront, Point bottomLeftFront,
			Point topLeftBack, Point topRightBack, Point topRightFront, Point topLeftFront) {
		// Generate triangles for the rectangular prism
		sides.add(new Polygon(topLeftFront, topRightFront, topRightBack, topLeftBack));
		sides.add(new Polygon(bottomLeftBack, bottomRightBack, topRightBack, topLeftBack));
		sides.add(new Polygon(bottomRightBack, bottomRightFront, topRightFront, topRightBack));
		sides.add(new Polygon(bottomLeftFront, bottomRightFront, topRightFront, topLeftFront));
		sides.add(new Polygon(bottomLeftBack, bottomLeftFront, topLeftFront, topLeftBack));
		sides.add(new Polygon(bottomLeftBack, bottomLeftFront, bottomRightFront, bottomRightBack));

		List<Point> points = List.of(bottomLeftBack, bottomRightBack, bottomRightFront, bottomLeftFront, topLeftBack,
				topRightBack, topRightFront, topLeftFront);
		Point zero = Point.ZERO;
		Point tmp = zero;
		for (Point p : points) {
			if (!p.equals(zero))
				tmp.add(p.subtract(zero));
		}
		center = tmp.divideSize(8);
	}

	public Vector getNormal(Point p) {
		Ray testSide = new Ray(center, p.subtract(center));
		for (Polygon side : sides)
			if (side.findIntersections(testSide) != null)
				return side.getNormal(p).scale(-1);
		return null;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		List<GeoPoint> points = new LinkedList<>();
		List<GeoPoint> tmp = new LinkedList<>();
		for (Polygon side : sides) {
			tmp = side.findGeoIntersections(ray);
			if (tmp != null)
				points.add(tmp.get(0));
		}
		if (points.isEmpty() || points == null)
			return null;
		return points;
	}

}
