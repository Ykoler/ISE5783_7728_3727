package geometries;

import java.util.LinkedList;

import primitives.Point;

/**
 * 
 * @author Yahel and Ashi
 */

public class Prism extends Geometries {
	public Prism(Point topLeftFront, Point topRightFront, Point bottomRightFront, Point bottomLeftFront,
			Point topLeftBack, Point topRightBack, Point bottomRightBack, Point bottomLeftBack) {
		// Generate triangles for the rectangular prism
		this.add(new Triangle(topLeftFront, topRightFront, bottomRightFront));
		this.add(new Triangle(topLeftFront, bottomRightFront, bottomLeftFront));

		this.add(new Triangle(topLeftBack, topRightBack, bottomRightBack));
		this.add(new Triangle(topLeftBack, bottomRightBack, bottomLeftBack));

		this.add(new Triangle(topLeftFront, topRightFront, topRightBack));
		this.add(new Triangle(topLeftFront, topRightBack, topLeftBack));

		this.add(new Triangle(bottomLeftFront, bottomRightFront, bottomRightBack));
		this.add(new Triangle(bottomLeftFront, bottomRightBack, bottomLeftBack));

		this.add(new Triangle(topLeftFront, bottomLeftFront, bottomLeftBack));
		this.add(new Triangle(topLeftFront, bottomLeftBack, topLeftBack));

		this.add(new Triangle(topRightFront, bottomRightFront, bottomRightBack));
		this.add(new Triangle(topRightFront, bottomRightBack, topRightBack));
	}
}
