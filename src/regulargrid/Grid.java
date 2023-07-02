package regulargrid;

import java.util.HashMap;
import java.util.List;

import geometries.*;
import primitives.*;
import static primitives.Util.*;

/**
 * @author ashih
 *
 */

public class Grid {
	private double minX, maxX, minY, maxY, minZ, maxZ;
	// sizes of voxels
	private double xSize, ySize, zSize;
	private int density;
	private Geometries outerGeometries;
	private HashMap<Integer, HashMap<Integer, HashMap<Integer, Voxel>>> grid;
	// private HashMap<Voxel, Geometries> grid;

	public Grid(Geometries geometries, int density) {
		// initiallizing map
		grid = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Voxel>>>();

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
		this.density = density;

		for (Intersectable geometry : geometries.getGeometries()) {
			edges = geometry.getEdges();
			double xS = edges.get(0);
			if (xS == Double.POSITIVE_INFINITY) {
				outerGeometries.add(geometry);
				continue;
			}
			double yS = edges.get(1);
			double zS = edges.get(2);
			int x = (int) ((xS - edges.get(3)) / xSize);
			int y = (int) ((yS - edges.get(4)) / ySize);
			int z = (int) ((zS - edges.get(5)) / zSize);
			int xV = (int) (xS - (xS - minX) % xSize);
			int yV = (int) (yS - (yS - minY) % ySize);
			int zV = (int) (zS - (zS - minZ) % zSize);
			for (int i = xV; i < xV + x * xSize; i += xSize) {
				for (int j = yV; j < yV + y * ySize; j += ySize) {
					for (int k = zV; k < zV + z * zSize; k += zSize) {
						if (grid.containsKey(x) && grid.get(x).containsKey(y) && grid.get(x).get(y).containsKey(z)) {
							grid.get(x).get(y).get(z);
						} else {
							Voxel voxel = new Voxel(x, y, z);
							voxel.geometries.add(geometry);
							grid.get(x).get(y).put(z, voxel);
						}
					}
				}
			}
		}
	}

	/**
	 * Checks if point is in grid
	 * 
	 * @param p
	 * @return
	 */
	public boolean inGrid(Point p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

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
		// Initialize the minimum and maximum intersection distances for each axis
		double tmin = Double.NEGATIVE_INFINITY;
		double tmax = Double.POSITIVE_INFINITY;

		// Check intersection with each axis
		for (int i = 0; i < 3; i++) {
			headCoordinate = headCoordinates[i];
			dirCoordinate = dirCoordinates[i];
			double min = mins[i];
			double max = maxs[i];

			// Check if the ray is parallel to the axis
			if (isZero(dirCoordinate)) {
				// Check if the ray origin is outside the box's extent on this axis
				if (headCoordinate < min || headCoordinate > max) {
					// No intersection
					return null;
				}
			} else {
				// Compute the intersection distances on this axis
				double t1 = (min - headCoordinate) / dirCoordinate;
				double t2 = (max - headCoordinate) / dirCoordinate;

				// Ensure t1 <= t2
				if (t1 > t2) {
					double temp = t1;
					t1 = t2;
					t2 = temp;
				}

				// Update the minimum and maximum intersection distances
				tmin = Math.max(tmin, t1);
				tmax = Math.min(tmax, t2);

				// Check if the box is missed or completely behind the ray
				if (tmin > tmax) {
					// No intersection
					return null;
				}
			}
		}

		return ray.getPoint(tmin);
	}

	public Geometries travese(Ray ray) {
		return travese(ray, false);
	}

	public Geometries travese(Ray ray, boolean multipleIntersection) {
		if ()
	}
}
