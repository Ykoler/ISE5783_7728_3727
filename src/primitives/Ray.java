package primitives;

public class Ray {
	final Point p0;
	final Vector dir;
	
	public Ray(Point p0, Vector dir) {
		this.p0 = new Point(p0.xyz);
		this.dir = new Vector(dir.xyz).normalize();
	}
	
	public Point getP0() {
		return p0;
	}
	
	public Vector getDir() {
		return dir;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ray other)
			return p0.equals(other.p0)&&dir.equals(other.dir);
		return false;
	}
	
	@Override
	public String toString() {
		return "Origin point: "+p0.toString()+"\nDirectional vector: "+dir.toString();
	}
	
	
}
