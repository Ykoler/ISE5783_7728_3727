package regulargrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private HashMap<Double3, Voxel> grid;
	private Ray currentRay;
	private Set<Intersectable> scanned = new HashSet<Intersectable>();

	public Grid(Geometries geometries, int density) {
		// initiallizing map
		grid = new HashMap<Double3, Voxel>();

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
			Double3 index;
			int xV = (int) (xS - (xS - minX) % xSize);
			int yV = (int) (yS - (yS - minY) % ySize);
			int zV = (int) (zS - (zS - minZ) % zSize);
			for (int i = xV; i < xV + x * xSize; i += xSize) {
				for (int j = yV; j < yV + y * ySize; j += ySize) {
					for (int k = zV; k < zV + z * zSize; k += zSize) {
						index = new Double3(i, j, k);
						if (grid.containsKey(index)) {
							grid.get(index).geometries.add(geometry);
						} else {
							Voxel voxel = new Voxel(i, j, k);
							voxel.geometries.add(geometry);
							grid.put(index, voxel);
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

	public Geometries getOuterGeometries() {
		return outerGeometries;
	}

	public double cutsGrid(Ray ray) {
		Point entry = gridEntryPoint(ray);
		currentRay = new Ray(entry, ray.getDir());
		if (entry == null) {
			return Double.POSITIVE_INFINITY;
		}
		return ray.getP0().distance(entry);
	}

	public Geometries traverse() {
		return traverse(false);
	}

	public Geometries traverse(boolean multipleIntersection) {
		Geometries geometries = new Geometries();

		Point head = currentRay.getP0();
		Vector dir = currentRay.getDir();

		Double3 currentVoxelIndex = voxelOfPoint(head);

		// Find the voxel that the ray starts in
		int x = (int) currentVoxelIndex.getD1();
		int y = (int) currentVoxelIndex.getD2();
		int z = (int) currentVoxelIndex.getD3();

		double dirX = dir.getX();
		double dirY = dir.getY();
		double dirZ = dir.getZ();

		double headX = head.getX();
		double headY = head.getY();
		double headZ = head.getZ();

		// Determine the direction of the ray in each dimension
		int stepX = (int) Math.signum(dirX);
		int stepY = (int) Math.signum(dirY);
		int stepZ = (int) Math.signum(dirZ);

		// Calculate the distance that the ray must travel to cross a voxel boundary in
		// each dimension
		double tDeltaX = xSize / Math.abs(dirX);
		double tDeltaY = ySize / Math.abs(dirY);
		double tDeltaZ = zSize / Math.abs(dirZ);

		// Calculate the distance that the ray must travel to reach the next voxel
		// boundary in each dimension
		double tMaxX = (stepX > 0 ? (x + 1) * xSize + minX - headX : x * xSize + minX - headX) * tDeltaX;
		double tMaxY = (stepY > 0 ? (y + 1) * ySize + minY - headY : y * ySize + minY - headY) * tDeltaY;
		double tMaxZ = (stepZ > 0 ? (z + 1) * zSize + minZ - headZ : z * zSize + minZ - headZ) * tDeltaZ;

		// Calculate the index of the voxel that is just outside the grid in each
		// dimension
		int justOutX = (stepX > 0 ? x : -1);
		int justOutY = (stepY > 0 ? y : -1);
		int justOutZ = (stepZ > 0 ? z : -1);

		// Initialize the list of geometries that the ray intersects with to null
		Geometries list = null;

		// Loop until we find a voxel with a non-empty object list or we fall out of the
		// end of the grid
		do {
			// Determine which voxel boundary the ray will cross first
			if (tMaxX < tMaxY) {
				if (tMaxX < tMaxZ) {
					// The ray crosses a voxel boundary in the x direction
					x += stepX;
					if (x == justOutX)
						return null; // The ray is outside the grid
					tMaxX += tDeltaX;
				} else {
					// The ray crosses a voxel boundary in the z direction
					z += stepZ;
					if (z == justOutZ)
						return null; // The ray is outside the grid
					tMaxZ += tDeltaZ;
				}
			} else {
				if (tMaxY < tMaxZ) {
					// The ray crosses a voxel boundary in the y direction
					y += stepY;
					if (y == justOutY)
						return null; // The ray is outside the grid
					tMaxY += tDeltaY;
				} else {
					// The ray crosses a voxel boundary in the z direction
					z += stepZ;
					if (z == justOutZ)
						return null; // The ray is outside the grid
					tMaxZ += tDeltaZ;
				}
			}

			// Get the list of geometries in the current voxel
			for (Intersectable element : grid.get(currentVoxelIndex).geometries.getGeometries()) {
				if (!scanned.contains(element)) {
					geometries.add(element);
					scanned.add(element);
				}
			}
		} while (geometries.getGeometries().isEmpty());
		if (geometries.getGeometries().isEmpty())
			return null;

		// Return the list of geometries that the ray intersects with
		return geometries;
	}

	private Double3 voxelOfPoint(Point p) {
		double pX = p.getX(), pY = p.getY(), pZ = p.getZ();
		int x = (int) (pX - (pX - minX) % xSize);
		int y = (int) (pY - (pY - minY) % ySize);
		int z = (int) (pZ - (pZ - minZ) % zSize);
		return new Double3(x, y, z);
	}
}
