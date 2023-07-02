package regulargrid;

import java.util.HashMap;
import java.util.List;

import geometries.*;
import primitives.*;

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

	public Geometries travesal(Ray ray) {
		return null;
	}
}
