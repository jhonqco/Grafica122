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
	
	public String toString(){
		String s= new String("El triangulo tiene los vertices: \nP1=("+points[0].x+","+points[0].y+","+points[0].z+")\nP2=("+points[1].x+","+points[1].y+","+points[1].z+")\nP3=("+points[2].x+","+points[2].y+","+points[2].z+")\n");
		return s;
	}
	
		
	

}
