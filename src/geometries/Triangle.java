package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

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
	public List<Point> findIntersections(Ray ray){
		return null;
	}
}
