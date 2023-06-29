package regulargrid;

import java.util.HashMap;
import java.util.List;

import geometries.*;

/**
 * @author ashih
 *
 */

public class Grid {
	private double minX, maxX, minY, maxY, minZ, maxZ;
	private double xSize, ySize, zSize;
	private int density;
	private HashMap<Integer, HashMap<Integer, HashMap<Integer, Voxel>>> grid;

	public Grid(Geometries geometries, int density) {
        List<Double> edges = geometries.getEdges();
        minX = edges.get(0);
        minY = edges.get(1);
        minZ = edges.get(2);
        maxX = edges.get(3);
        maxY = edges.get(4);
        maxZ = edges.get(5);
        xSize = maxX - minX;
        ySize = maxY - minY;
        zSize = maxZ - minZ;
        this.density = density;

        for (Intersectable geometry : geometries.getGeometries()) {
            edges = geometry.getEdges();
            int x = (int) ((edges.get(0) - edges.get(3)) / xSize);
            int y = (int) ((edges.get(1) - edges.get(4)) / ySize);
            int z = (int) ((edges.get(2) - edges.get(5)) / zSize);
        }
    }

}
