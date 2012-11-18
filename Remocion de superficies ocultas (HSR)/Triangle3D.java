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
		return Math.max(points[0].y, Math.max(points[1].y, points[2].y));
	}

	public PVector[] getPoints() {
		return points;
	}

}
