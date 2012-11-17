import processing.core.PGraphics;
import processing.core.PVector;
import remixlab.proscene.Camera;


public class Triangle3D {
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

}
