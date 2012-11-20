<<<<<<< HEAD
import processing.core.*;


public class Triangle3D{
	private PVector[] points;
	

	public Triangle3D(PVector point1, PVector point2, PVector point3) {
		points = new PVector[3];
		this.points[0] = point1;
		this.points[1] = point2;
		this.points[2] = point3;
	}
	

	public Triangle3D(PVector point2, PVector point3) {
		this(new PVector(0, 0, 0), point2,point3);
	}

	public void drawOn(PGraphics canvas) {
		canvas.beginShape();
		for(PVector point: points){
			canvas.vertex(point.x,point.y,point.z);
		}
		canvas.endShape(PGraphics.CLOSE);
	}

	public PVector[] getPoints(){
		return points;
	}
	
	public PVector getPoint1(){
		return points[0];
	}
	
	public PVector getPoint2(){
		return points[1];
	}
	public PVector getPoint3(){
		return points[2];
	}
	public String toString(){
		String s= new String("El triangulo tiene los vertices: \nP1=("+points[0].x+","+points[0].y+","+points[0].z+")\nP2=("+points[1].x+","+points[1].y+","+points[1].z+")\nP3=("+points[2].x+","+points[2].y+","+points[2].z+")\n");
		return s;
	}
	
		
	
=======
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import processing.core.PGraphics;
import processing.core.PVector;

public class Triangle3D {
	private PVector[] points;
	private int color;

	public Triangle3D(PVector point1, PVector point2, PVector point3, int color) {
		points = new PVector[3];
		this.points[0] = point1;
		this.points[1] = point2;
		this.points[2] = point3;
		this.color=color;
	}

	public Triangle3D(PVector point2, PVector point3,int color) {
		this(new PVector(0, 0, 0), point2, point3,color);
	}

	public void drawOn(PGraphics canvas) {
		canvas.fill(color);
		canvas.beginShape();
		for (PVector point : points) {
			if (canvas.is2D()) {
				canvas.vertex(point.x, point.y);
			} else {
				canvas.vertex(point.x, point.y, point.z);
			}
		}
		canvas.endShape(PGraphics.CLOSE);
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
>>>>>>> refs/remotes/origin/myHSR

}
