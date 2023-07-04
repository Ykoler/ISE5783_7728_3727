package regulargrid;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.lang.Math.floor;
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
	private boolean finished = false;
	private Set<Intersectable> scanned = new HashSet<Intersectable>();
	private final double EPS = 0.0000001;
	private Geometries geometries;

	public Grid(Geometries geometries, int density) {

		this.geometries = geometries;

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
			int x = (int) Math.ceil(((edges.get(3) - xS) / xSize));// > 0 ? (int) floor(((edges.get(3) - xS) / xSize)) :
																	// 1;
			int y = (int) Math.ceil(((edges.get(4) - yS) / ySize));// > 0 ? (int) floor(((edges.get(4) - yS) / ySize)) :
																	// 1;
			int z = (int) Math.ceil(((edges.get(5) - zS) / zSize));// > 0 ? (int) floor(((edges.get(5) - zS) / zSize)) :
																	// 1;
			Double3 index = coordinateToIndex(new Point(xS, yS, zS));
			int xV = (int) index.getD1(), yV = (int) index.getD2(), zV = (int) index.getD3();
			double xC = (xS - (xS - minX) % xSize);
			double yC = (yS - (yS - minY) % ySize);
			double zC = (zS - (zS - minZ) % zSize);
			for (int i = xV; i < xV + x; i++) {
				for (int j = yV; j < yV + y; j++) {
					for (int k = zV; k < zV + z; k++) {
						index = new Double3(i, j, k);
						if (grid.containsKey(index)) {
							grid.get(index).geometries.add(geometry);
						} else {
							Voxel voxel = new Voxel(xC + (i - xV) * xSize, yC + (j - yV) * ySize,
									zC + (k - zV) * zSize);
							voxel.geometries.add(geometry);
							grid.put(index, voxel);
						}
					}
				}
			}
		}
	}

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
	 * @param p
	 * @return
	 */
	public boolean inGrid(Point p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	public Geometries getGeomet() {
		return geometries;
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

			// System.out.println(ray);
			// Check if the ray is parallel to the axis
			if (isZero(dirCoordinate)) {
				// Check if the ray origin is outside the box's extent on this axis
				if (headCoordinate < min || headCoordinate > max) {
					// No intersection
					// System.out.println("aaa");
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
					// System.out.println("bbb");
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
			// System.out.println("entry");
			// System.out.println(entry);
			if (onEdge(entry))
				return entry;
		}
		return null;
	}

	public Geometries getOuterGeometries() {
		return outerGeometries;
	}

	public double cutsGrid(Ray ray) {
		finished = false;
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
		Vector dir = currentRay.getDir();

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
			if (head == null || finished)
				return geometries;

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

			Double3 currentVoxelIndex = coordinateToIndex(new Point(dX, dY, dZ));

			// Get the list of geometries in the current voxel
			if (grid.containsKey(currentVoxelIndex)) {
				for (Intersectable element : grid.get(currentVoxelIndex).geometries.getGeometries()) {
					/*
					 * if (!scanned.contains(element)) { geometries.add(element);
					 * scanned.add(element); }
					 */
					if (element.findIntersections(currentRay) != null)
						geometries.add(element);
				}
			}
			// Determine which voxel boundary the ray will cross first
			// System.out.println(currentRay);
			if (tMaxX < tMaxY && tMaxX < tMaxZ) {
				// The ray crosses a voxel boundary in the x direction
				checkIfOut = stepX > 0 ? headX + tDeltaX + EPS > maxX : headX - tDeltaX - EPS < minX;
				currentRay = new Ray(currentRay.getPoint((stepX > 0 ? tDeltaX : -tDeltaX) / dirX), dir);
			} else if (tMaxY < tMaxX && tMaxY < tMaxZ) {
				// The ray crosses a voxel boundary in the y direction
				checkIfOut = stepY > 0 ? headY + tDeltaY + EPS > maxY : headY - tDeltaY - EPS < minY;
				currentRay = new Ray(currentRay.getPoint((stepY > 0 ? tDeltaY : -tDeltaY) / dirY), dir);
			} else {
				// The ray crosses a voxel boundary in the z direction
				checkIfOut = stepZ > 0 ? headZ + tDeltaZ + EPS > maxZ : headZ - tDeltaZ - EPS < minZ;
				currentRay = new Ray(currentRay.getPoint((stepZ > 0 ? tDeltaZ : -tDeltaZ) / dirZ), dir);
			}
			if (checkIfOut) {
				finished = true;
				return geometries; // The ray is outside the grid
			}
		} while (geometries.getGeometries().isEmpty() || multipleIntersection);

		// Return the list of geometries that the ray intersects with
		return geometries;
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
