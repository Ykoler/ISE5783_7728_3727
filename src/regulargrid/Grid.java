package regulargrid;

import java.util.Arrays;
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
		xSize = (int) (maxX - minX) / density;
		ySize = (int) (maxY - minY) / density;
		zSize = (int) (maxZ - minZ) / density;
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

		double tmax, tmin;
		double[] ts = new double[3];

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

				// Update the minimum and maximum intersection distances
				tmin = Math.max(t1, t2);
				tmax = Math.min(t1, t2);
				ts[i] = tmin;
				// Check if the box is missed or completely behind the ray
				if (tmin > tmax) {
					// No intersection
					return null;
				}
			}
		}
		Arrays.sort(ts);
		Point entry;
		for (int i = 0; i < 3; i++) {
			entry = ray.getPoint(ts[i]);
			if (onEdge(entry))
				return entry;
		}
		return null;
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
			if (head == null)
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
			// System.out.println(currentRay);
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

	private boolean onEdge(Point p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();
		if (alignZero(x - minX) == 0 || alignZero(y - minY) == 0 || alignZero(z - minZ) == 0 || alignZero(x - maxX) == 0
				|| alignZero(y - maxY) == 0 || alignZero(z - maxZ) == 0)
			return true;
		return false;
	}

	public Geometries voxelTraversal(Point rayStart, Point rayEnd) {
		Geometries geometries = new Geometries();

		// This id of the first/current voxel hit by the ray.
		// Using floor (round down) is actually very important,
		// the implicit int-casting will round up for negative numbers.
		Point currentVoxel = new Point((int) Math.floor(rayStart.getX() / xSize),
				(int) Math.floor(rayStart.getY() / ySize), (int) Math.floor(rayStart.getZ() / zSize));

		// The id of the last voxel hit by the ray.
		// TODO: what happens if the end point is on a border?
		Point lastVoxel = new Point((int) Math.floor(rayEnd.getX() / xSize), (int) Math.floor(rayEnd.getY() / ySize),
				(int) Math.floor(rayEnd.getZ() / zSize));

		// Compute normalized ray direction.
		Vector ray = rayEnd.subtract(rayStart).normalize();
		// ray.normalize();

		// In which direction the voxel ids are incremented.
		double x = ray.getX();
		int stepX = (x >= 0) ? 1 : -1;
		double y = ray.getY();
		int stepY = (y >= 0) ? 1 : -1;
		double z = ray.getZ();
		int stepZ = (z >= 0) ? 1 : -1;

		// Distance along the ray to the next voxel border from the current position
		// (tMaxX, tMaxY, tMaxZ).
		double nextVoxelBoundaryX = (currentVoxel.getX() + stepX) * xSize;
		double nextVoxelBoundaryY = (currentVoxel.getY() + stepY) * ySize;
		double nextVoxelBoundaryZ = (currentVoxel.getZ() + stepZ) * zSize;

		// tMaxX, tMaxY, tMaxZ --
		// distance until next intersection with voxel-border
		// the value of t at which the ray crosses the first vertical voxel boundary
		double tMaxX = (x != 0) ? (nextVoxelBoundaryX - rayStart.getX()) / x : Double.MAX_VALUE; //
		double tMaxY = (y != 0) ? (nextVoxelBoundaryY - rayStart.getY()) / y : Double.MAX_VALUE; //
		double tMaxZ = (z != 0) ? (nextVoxelBoundaryZ - rayStart.getZ()) / z : Double.MAX_VALUE; //

		// tDeltaX, tDeltaY, tDeltaZ --
		// how far along the ray we must move for the horizontal component to equal the
		// width of a voxel
		// the direction in which we traverse the grid
		// can only be Double.MAX_VALUE if we never go in that direction
		double tDeltaX = (x != 0) ? xSize / x * stepX : Double.MAX_VALUE;
		double tDeltaY = (y != 0) ? ySize / y * stepY : Double.MAX_VALUE;
		double tDeltaZ = (z != 0) ? zSize / z * stepZ : Double.MAX_VALUE;

		Point diff = new Point(0, 0, 0);
		boolean negRay = false;
		if (currentVoxel.getX() != lastVoxel.getX() && x < 0) {
			diff = diff.add(new Vector(-1, 0, 0));
			negRay = true;
		}
		if (currentVoxel.getY() != lastVoxel.getY() && y < 0) {
			diff = diff.add(new Vector(0, -1, 0));
			negRay = true;
		}
		if (currentVoxel.getZ() != lastVoxel.getZ() && z < 0) {
			diff = diff.add(new Vector(0, 0, -1));
			negRay = true;
		}
		Double3 index = new Double3(currentVoxel.getX(), currentVoxel.getY(), currentVoxel.getZ());
		if (grid.containsKey(index)) {
			for (Intersectable element : grid.get(index).geometries.getGeometries()) {
				if (!geometries.getGeometries().contains(element))
					geometries.add(element);
			}
		}
		if (negRay) {
			currentVoxel = currentVoxel.add(diff.subtract(Point.ZERO));
			if (grid.containsKey(index)) {
				for (Intersectable element : grid.get(index).geometries.getGeometries()) {
					if (!geometries.getGeometries().contains(element))
						geometries.add(element);
				}
			}
		}

		while (!lastVoxel.equals(currentVoxel)) {
			if (tMaxX < tMaxY) {
				if (tMaxX < tMaxZ) {
					currentVoxel = currentVoxel.add(new Vector(stepX, 0, 0));
					tMaxX += tDeltaX;
				} else {
					currentVoxel = currentVoxel.add(new Vector(0, 0, stepZ));
					tMaxZ += tDeltaZ;
				}
			} else {
				if (tMaxY < tMaxZ) {
					currentVoxel = currentVoxel.add(new Vector(0, stepY, 0));
					tMaxY += tDeltaY;
				} else {
					currentVoxel = currentVoxel.add(new Vector(0, 0, stepZ));
					tMaxZ += tDeltaZ;
				}
			}
			if (grid.containsKey(index)) {
				for (Intersectable element : grid.get(index).geometries.getGeometries()) {
					if (!geometries.getGeometries().contains(element))
						geometries.add(element);
				}
			}
		}
		return geometries;
	}

	public double getOutBounds() {
		// calculates the maximun length of the diagonal of the grid
		return Math
				.sqrt(Math.pow((xSize * density), 2) + Math.pow((ySize * density), 2) + Math.pow((zSize * density), 2));

	}
}
