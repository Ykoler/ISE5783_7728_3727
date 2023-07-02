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
	private int xSize, ySize, zSize;
	private int density;
	private Geometries outerGeometries;
	private HashMap<Double3, Voxel> grid;
	private Ray currentRay;
	private Set<Intersectable> scanned = new HashSet<Intersectable>();
	private final double EPS = 0.0000001;

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
		xSize = (int)(maxX - minX) / density;
		ySize = (int)(maxY - minY) / density;
		zSize = (int)(maxZ - minZ) / density;
		this.density = density;

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
			int x = (int) ((edges.get(3) - xS) / xSize);
			int y = (int) ((edges.get(4) - yS) / ySize);
			int z = (int) ((edges.get(5) - zS) / zSize);
			Double3 index;
			int xV = (int) (xS - (xS - minX) % xSize);
			int yV = (int) (yS - (yS - minY) % ySize);
			int zV = (int) (zS - (zS - minZ) % zSize);
			for (int i = xV; i <= xV + x * xSize; i += xSize) {
				for (int j = yV; j <= yV + y * ySize; j += ySize) {
					for (int k = zV; k <= zV + z * zSize; k += zSize) {
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
		Vector dir = currentRay.getDir().normalize();

		double dirX = dir.getX();
		double dirY = dir.getY();
		double dirZ = dir.getZ();

		// Determine the direction of the ray in each dimension
		int stepX = (int) Math.signum(dirX);
		int stepY = (int) Math.signum(dirY);
		int stepZ = (int) Math.signum(dirZ);

		boolean checkIfOut;
		// Loop until we find a voxel with a non-empty object list or we fall out of the
		// end of the grid
		do {
			// Calculate the distance that the ray must travel to cross a voxel boundary in
			// each dimension
			Point head = currentRay.getP0();
			if(head == null)
				return geometries;
			Double3 currentVoxelIndex = voxelOfPoint(head);

			// Find the voxel that the ray starts in
			int x = (int) currentVoxelIndex.getD1();
			int y = (int) currentVoxelIndex.getD2();
			int z = (int) currentVoxelIndex.getD3();

			double headX = head.getX();
			double headY = head.getY();
			double headZ = head.getZ();

			// Calculate the distance that the ray must travel to reach the next voxel
			// boundary in each dimension
			double tDeltaX = stepX > 0 ? x + xSize - headX : headX - x;
			double tDeltaY = stepY > 0 ? y + ySize - headY : headY - y;
			double tDeltaZ = stepZ > 0 ? z + zSize - headZ : headZ - z;

			double tMaxX = Math.abs(tDeltaX / dirX);
			double tMaxY = Math.abs(tDeltaY / dirY);
			double tMaxZ = Math.abs(tDeltaZ / dirZ);

			// Get the list of geometries in the current voxel
			if (grid.containsKey(currentVoxelIndex)) {
				for (Intersectable element : grid.get(currentVoxelIndex).geometries.getGeometries()) {
					/*
					 * if (!scanned.contains(element)) { geometries.add(element);
					 * scanned.add(element); }
					 */
					geometries.add(element);
				}
			}
			// Determine which voxel boundary the ray will cross first
			//System.out.println(currentRay);
			if (tMaxX < tMaxY && tMaxX < tMaxZ) {
				// The ray crosses a voxel boundary in the x direction
				checkIfOut = stepX > 0 ? headX + tDeltaX + EPS + tMaxX + EPS > maxX
						: headX + tDeltaX + EPS - tMaxX - EPS < minX;
			} else if (tMaxY < tMaxX && tMaxY < tMaxZ) {
				// The ray crosses a voxel boundary in the y direction
				checkIfOut = stepY > 0 ? headY + tDeltaY + EPS + tMaxY + EPS > maxY
						: headY + tDeltaY + EPS - tMaxY - EPS < minY;
			} else {
				// The ray crosses a voxel boundary in the z direction
				checkIfOut = stepZ > 0 ? headZ + tDeltaY + EPS + tMaxZ + EPS > maxZ
						: headZ + tDeltaY + EPS - tMaxZ - EPS < minZ;
			}
			headX += (stepX > 0 ? EPS + tDeltaX : -(EPS + tDeltaX));
			headY += (stepY > 0 ? EPS + tDeltaY : -(EPS + tDeltaY));
			headZ += (stepZ > 0 ? EPS + tDeltaZ : -(EPS + tDeltaZ));
			currentRay = new Ray(new Point(headX, headY, headZ), dir);
			if (checkIfOut)
				return geometries; // The ray is outside the grid
		} while (geometries.getGeometries().isEmpty());

		// Return the list of geometries that the ray intersects with
		return geometries;
	}

	private Double3 voxelOfPoint(Point p) {
		double pX = p.getX() - EPS, pY = p.getY() - EPS, pZ = p.getZ() - EPS;
		int x = (int) (pX - ((pX - minX) % xSize));
		int y = (int) (pY - ((pY - minY) % ySize));
		int z = (int) (pZ - ((pZ - minZ) % zSize));
		return new Double3(x, y, z);
	}
}
