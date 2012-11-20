import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import processing.core.PGraphics;
import processing.core.PVector;

public class Triangle3D {
	private PVector[] points;
	private Box caja;

	public Triangle3D(PVector point1, PVector point2, PVector point3,Box caja) {
		points = new PVector[3];
		this.points[0] = point1;
		this.points[1] = point2;
		this.points[2] = point3;
		this.caja=caja;
	}

	public Triangle3D(PVector point2, PVector point3, Box caja) {
		this(new PVector(0, 0, 0), point2, point3,caja);
	}

	public void drawOn(PGraphics canvas) {
		canvas.pushStyle();
		canvas.fill(caja.getColor());
		canvas.beginShape();
		for (PVector point : points) {
			if (canvas.is2D()) {
				canvas.vertex(point.x, point.y);
			} else {
				canvas.vertex(point.x, point.y, point.z);
			}
		}
		canvas.endShape(PGraphics.CLOSE);
		canvas.popStyle();
	}
	
	public PVector normal(){
		PVector a = PVector.sub(points[1], points[0]);
		PVector b = PVector.sub(points[2], points[0]);
		Vector3D a2 = new Vector3D(a.x, a.y, a.z);
		Vector3D b2 = new Vector3D(b.x, b.y, b.z);
		Vector3D result = Vector3D.crossProduct(a2, b2);
		return new PVector((float)result.getX(),(float)result.getY(),(float)result.getZ());
	}
	
	public float maxX(){
		return Math.max(points[0].x, Math.max(points[1].x, points[2].x));
	}
	
	public float minX(){
		return Math.min(points[0].x, Math.min(points[1].x, points[2].x));
	}
	
	public float maxY(){
		return Math.max(points[0].y, Math.max(points[1].y, points[2].y));
	}
	
	public float minY(){
		return Math.min(points[0].y, Math.min(points[1].y, points[2].y));
	}

	public float maxZ(){
		return Math.max(points[0].z, Math.max(points[1].z, points[2].z));
	}
	
	public float minZ(){
		return Math.min(points[0].z, Math.min(points[1].z, points[2].z));
	}
	
	public PVector[] getPoints() {
		return points;
	}

}
