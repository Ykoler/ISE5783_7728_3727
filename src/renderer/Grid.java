package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static java.lang.Math.floor;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import static primitives.Util.*;

/**
 * a class that represents a grid of voxels (greatly improves performance)
 * 
 * @autor Yahel and Ashi
 */
public class Grid {
	private double minX, maxX, minY, maxY, minZ, maxZ;
	// sizes of voxels
	private double xSize, ySize, zSize;
	private Geometries outerGeometries;
	private HashMap<Double3, Geometries> grid;
	private final double EPS = 0.0000001;

	/**
	 * constructor for the grid, mapping the each geometry to the voxels it is in
	 * and filtering the outer geometries
	 * 
	 * @param geometries the geometries to be mapped
	 * @param density    the density of the grid along each axis
	 */
	public Grid(Geometries geometries, int density) {

		// initializing map
		grid = new HashMap<Double3, Geometries>();
		List<Double> edges = geometries.getEdges();
		minX = edges.get(0);
		minY = edges.get(1);
		minZ = edges.get(2);
		maxX = edges.get(3);
		maxY = edges.get(4);
		maxZ = edges.get(5);

		xSize = (maxX - minX) / density;
		ySize = (maxY - minY) / density;
		zSize = (maxZ - minZ) / density;

		outerGeometries = new Geometries();

		for (Intersectable geometry : geometries.getGeometries()) {
			edges = geometry.getEdges();
			double xS = edges.get(0);
			if (xS == Double.POSITIVE_INFINITY) {
				outerGeometries.add(geometry);
				continue;
			}
			double yS = edges.get(1);
			double zS = edges.get(2);
			Double3 target = coordinateToIndex(new Point(edges.get(3), edges.get(4), edges.get(5)));
			int xT = (int) target.getD1(), yT = (int) target.getD2(), zT = (int) target.getD3();
			Double3 index = coordinateToIndex(new Point(xS, yS, zS));
			int xV = (int) index.getD1(), yV = (int) index.getD2(), zV = (int) index.getD3();
			for (int i = xV - 1; i <= xT; i++) {
				for (int j = yV - 1; j <= yT; j++) {
					for (int k = zV - 1; k <= zT; k++) {
						index = new Double3(i, j, k);
						if (grid.containsKey(index)) {
							grid.get(index).add(geometry);
						} else {
							grid.put(index, new Geometries(geometry));
						}
					}
				}
			}
		}
	}

	/**
	 * maps a point to the coordinates of the voxel it is in
	 * 
	 * @param p the point to be mapped
	 * @return the coordinates of the voxel
	 */
	private Double3 coordinateToIndex(Point p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();
		int xI = (int) floor((x - (x - minX) % xSize) / xSize);
		int yI = (int) floor((y - (y - minY) % ySize) / ySize);
		int zI = (int) floor((z - (z - minZ) % zSize) / zSize);
		return new Double3(xI, yI, zI);
	}

	/**
	 * Checks if point is in grid
	 * 
	 * @param p the point to be checked
	 * @return true if the point is in the grid, false otherwise
	 */
	public boolean inGrid(Point p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	/**
	 * finds the entry point of a ray into the grid
	 * 
	 * @param ray the ray to be checked
	 * @return the entry point of the ray into the grid
	 */
	public Point gridEntryPoint(Ray ray) {
		Point p = ray.getP0();
		if (inGrid(p))
			return p;
		double[] headCoordinates = { p.getX(), p.getY(), p.getZ() };
		Vector dir = ray.getDir();
		double[] dirCoordinates = { dir.getX(), dir.getY(), dir.getZ() };

		double[] mins = { minX, minY, minZ };
		double[] maxs = { maxX, maxY, maxZ };

		double headCoordinate, dirCoordinate;

		double tmax, tmin;
		double[] ts = new double[3];

		// Check intersection with each axis
		for (int i = 0; i < 3; i++) {
			headCoordinate = headCoordinates[i];
			dirCoordinate = dirCoordinates[i];
			double min = mins[i];
			double max = maxs[i];

			// System.out.println(ray);
			// Check if the ray is parallel to the axis
			if (isZero(dirCoordinate)) {
				// Check if the ray origin is outside the box's extent on this axis
				if (headCoordinate < min || headCoordinate > max) {
					// No intersection
					return null;
				}
				ts[i] = Double.POSITIVE_INFINITY;
			} else {
				// Compute the intersection distances on this axis
				double t1 = (min - headCoordinate) / dirCoordinate;
				double t2 = (max - headCoordinate) / dirCoordinate;

				// Update the minimum and maximum intersection distances
				tmin = Math.min(t1, t2);
				tmax = Math.max(t1, t2);
				ts[i] = tmin;
				// Check if the box is missed or completely behind the ray
				if (tmin > tmax) {
					// No intersection
					return null;
				}
			}
		}
		for (int i = 0; i < ts.length - 1; i++) {
			for (int j = 0; j < ts.length - i - 1; j++) {
				if (Math.abs(ts[j]) > Math.abs(ts[j + 1])) {
					double temp = ts[j];
					ts[j] = ts[j + 1];
					ts[j + 1] = temp;
				}
			}
		}
		Point entry;
		for (int i = 0; i < 3; i++) {
			if (ts[i] == Double.POSITIVE_INFINITY)
				return null;
			entry = ray.getPoint(ts[i]);
			if (onEdge(entry))
				return entry;
		}
		return null;
	}

	/**
	 * getter for the outer geometries
	 * 
	 * @return the outer geometries
	 */
	public Geometries getOuterGeometries() {
		return outerGeometries;
	}

	/**
	 * finds the distance between the entry point of a ray into the grid and the
	 * origin of the ray
	 * 
	 * @param ray the ray to be checked
	 * @return the distance between the entry point of the ray and its origin
	 */
	public double cutsGrid(Ray ray) {
		Point entry = gridEntryPoint(ray);
		if (entry == null) {
			return Double.POSITIVE_INFINITY;
		}
		return ray.getP0().distance(entry);
	}

	/**
	 * traverses the grid and finds the intersections of the ray with the geometries
	 * 
	 * @param ray the ray to be checked
	 * @return the list of intersections
	 */
	public List<GeoPoint> traverse(Ray ray) {
		return traverse(ray, false);
	}

	/**
	 * traverses the grid and finds the intersections of the ray with the geometries
	 * 
	 * @param i_currentRay         the ray to be checked
	 * @param multipleIntersection whether to return multiple intersections or not
	 * @return the list of intersections
	 */
	public List<GeoPoint> traverse(Ray i_currentRay, boolean multipleIntersection) {
		Ray currentRay = new Ray(gridEntryPoint(i_currentRay), i_currentRay.getDir());
		List<GeoPoint> intersections = new ArrayList<>();
		Vector dir = currentRay.getDir();

		double dirX = dir.getX();
		double dirY = dir.getY();
		double dirZ = dir.getZ();

		// Determine the direction of the ray in each dimension
		int stepX = (int) Math.signum(dirX);
		int stepY = (int) Math.signum(dirY);
		int stepZ = (int) Math.signum(dirZ);

		boolean checkIfOut = false;
		// Loop until we find a voxel with a non-empty object list or we fall out of the
		// end of the grid
		do {
			// Calculate the distance that the ray must travel to cross a voxel boundary in
			// each dimension
			Point head = currentRay.getP0();

			// Find the voxel that the ray starts in
			double headX = head.getX();
			double headY = head.getY();
			double headZ = head.getZ();

			double dX = stepX > 0 ? headX + EPS : headX - EPS;
			double dY = stepY > 0 ? headY + EPS : headY - EPS;
			double dZ = stepZ > 0 ? headZ + EPS : headZ - EPS;

			double x = (dX - (dX - minX) % xSize);
			double y = (dY - (dY - minY) % ySize);
			double z = (dZ - (dZ - minZ) % zSize);

			// Calculate the distance that the ray must travel to reach the next voxel
			// boundary in each dimension
			double tDeltaX = stepX > 0 ? x + xSize - headX : headX - x;
			double tDeltaY = stepY > 0 ? y + ySize - headY : headY - y;
			double tDeltaZ = stepZ > 0 ? z + zSize - headZ : headZ - z;

			double tMaxX = Math.abs(tDeltaX / dirX);
			double tMaxY = Math.abs(tDeltaY / dirY);
			double tMaxZ = Math.abs(tDeltaZ / dirZ);

			double t;

			Double3 currentVoxelIndex = coordinateToIndex(new Point(dX, dY, dZ));
			// Get the list of geometries in the current voxel
			if (grid.containsKey(currentVoxelIndex)) {
				List<GeoPoint> geoIntersections = grid.get(currentVoxelIndex).findGeoIntersections(i_currentRay);
				if (geoIntersections != null)
					intersections.addAll(geoIntersections);
			}

			// Determine which voxel boundary the ray will cross first
			// System.out.println(currentRay);
			if (tMaxX < tMaxY && tMaxX < tMaxZ) {
				// The ray crosses a voxel boundary in the x direction
				checkIfOut = stepX > 0 ? headX + tDeltaX + EPS > maxX : headX - tDeltaX - EPS < minX;
				t = (stepX > 0 ? tDeltaX : -tDeltaX) / dirX;
			} else if (tMaxY < tMaxX && tMaxY < tMaxZ) {
				// The ray crosses a voxel boundary in the y direction
				checkIfOut = stepY > 0 ? headY + tDeltaY + EPS > maxY : headY - tDeltaY - EPS < minY;
				t = (stepY > 0 ? tDeltaY : -tDeltaY) / dirY;
			} else {
				// The ray crosses a voxel boundary in the z direction
				checkIfOut = stepZ > 0 ? headZ + tDeltaZ + EPS > maxZ : headZ - tDeltaZ - EPS < minZ;
				t = (stepZ > 0 ? tDeltaZ : -tDeltaZ) / dirZ;
			}
			currentRay = new Ray(currentRay.getPoint(t), dir);
		} while (!checkIfOut && (intersections.isEmpty() || multipleIntersection));

		// Return the list of geometries that the ray intersects with
		return intersections;
	}

	private boolean onEdge(Point p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();
		if (((alignZero(x - minX) == 0 || alignZero(x - maxX) == 0) && y >= minY && y <= maxY && z >= minZ && z <= maxZ)
				|| ((alignZero(y - minY) == 0 || alignZero(y - maxY) == 0) && x >= minX && x <= maxX && z >= minZ
						&& z <= maxZ)
				|| ((alignZero(z - minZ) == 0 || alignZero(z - maxZ) == 0) && y >= minY && y <= maxY && x >= minX
						&& x <= maxX)) {
			return true;
		}
		return false;
	}

}