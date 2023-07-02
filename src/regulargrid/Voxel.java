package regulargrid;

import geometries.*;
import primitives.*;

public class Voxel {
	public Geometries geometries;
	public final double x, y, z;
	// private final Point center;

	/**
	 * recives a Geometries object and adds to the voxel's geometries all the
	 * geometries that are contained in the voxel
	 */

	public Voxel(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.geometries = new Geometries();
	}

	// public Voxel addGeometry(Intersectable geometry) {
	// geometries.add(geometry);
	// return this;
	// }
}
