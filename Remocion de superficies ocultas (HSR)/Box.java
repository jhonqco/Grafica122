import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Quaternion;
import remixlab.proscene.Scene;

public class Box {
	private ArrayList<PVector> vertices = new ArrayList<PVector>(8);

	InteractiveFrame iFrame;
	float width, height, depth;
	int color;
	private PGraphics canvas;
	private Scene scene;
	ArrayList<Triangle3D> triangles= new ArrayList<Triangle3D>(12);
	
	
	Box(Scene scene) {
		iFrame = new InteractiveFrame(scene);
		canvas = scene.renderer();
		this.scene = scene;

		// sets size randomly
		width = scene.parent.random(10, 40);
		height = scene.parent.random(10, 40);
		depth = scene.parent.random(10, 40);

		vertices.clear();
		vertices.add(new PVector(0, 0, 0));
		vertices.add(new PVector(0, height, 0));
		vertices.add(new PVector(width, 0, 0));
		
		//set box's triangles
		setTriangles();

		// sets color randomly
		color = scene.parent.color(scene.parent.random(0, 255), scene.parent.random(0, 255),
				scene.parent.random(0, 255));

		setPosition();
	}

	public void draw() {
		
		canvas.pushMatrix();
		canvas.pushStyle();
		// Multiply matrix to get in the frame coordinate system.
		// scene.parent.applyMatrix(iFrame.matrix()) is handy but inefficient
		iFrame.applyTransformation(); // optimum

		canvas.noStroke();
		if (iFrame.grabsMouse()) {
			canvas.fill(255, 0, 0);
			scene.drawAxis(PApplet.max(width, height, depth) * 1.3f);
		} else
			canvas.fill(getColor());
		// Draw a box
		// canvas.box(w, h, d);
		canvas.stroke(this.getColor());
		
		
		
		for (int i=0;i<triangles.size();i++) {
			this.triangles.get(i).drawOn(canvas);
		}
		
		canvas.popStyle();
		canvas.popMatrix();
	}

	public void setSize(float myW, float myH, float myD) {
		width = myW;
		height = myH;
		depth = myD;
	}

	public void setTriangles(){
		Triangle3D triangle;
		triangle=new Triangle3D(new PVector(0,0,0),new PVector(0,width,0),new PVector(0,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,0,0),new PVector(0,0,height),new PVector(0,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,0,height),new PVector(0,width,height),new PVector(depth,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,0,height),new PVector(depth,0,height),new PVector(depth,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,0,0),new PVector(0,0,height),new PVector(depth,0,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,0,0),new PVector(depth,0,height),new PVector(depth,0,0));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(depth,0,0),new PVector(depth,width,0),new PVector(depth,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(depth,0,0),new PVector(depth,0,height),new PVector(depth,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,0,0),new PVector(depth,0,0),new PVector(0,width,0));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(depth,width,0),new PVector(depth,0,0),new PVector(0,width,0));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,width,0),new PVector(depth,width,0),new PVector(depth,width,height));
		triangles.add(0,triangle);
		triangle=new Triangle3D(new PVector(0,width,0),new PVector(0,width,height),new PVector(depth,width,height));
		triangles.add(0,triangle);
	}



	public int getColor() {
		return color;
	}

	public void setColor(int myC) {
		color = myC;
	}

	public PVector getPosition() {
		return iFrame.position();
	}

	// sets position randomly
	public void setPosition() {
		float low = -100;
		float high = 100;
		iFrame.setPosition(new PVector(scene.parent.random(low, high), scene.parent.random(low,
				high), scene.parent.random(low, high)));
	}

	public void setPosition(PVector pos) {
		iFrame.setPosition(pos);
	}

	public Quaternion getOrientation() {
		return iFrame.orientation();
	}

	public void setOrientation(PVector v) {
		PVector to = PVector.sub(v, iFrame.position());
		iFrame.setOrientation(new Quaternion(new PVector(0, 1, 0), to));
	}

	public void draw2D(PGraphics canvas) {
		canvas.pushMatrix();
		canvas.pushStyle();
		canvas.fill(getColor());
		canvas.stroke(getColor());
		
		PVector position = scene.camera().projectedCoordinatesOf(iFrame.position());
		System.out.println(position);
		canvas.translate(position.x,position.y);
		
		canvas.stroke(this.getColor());
		canvas.beginShape();
	
		for(int i=0;i<this.triangles.size();i++){
			PVector cameraVertice = scene.camera().projectedCoordinatesOf(this.triangles.get(i).getPoint1());
			canvas.vertex(cameraVertice.x, cameraVertice.y);
			cameraVertice = scene.camera().projectedCoordinatesOf(this.triangles.get(i).getPoint2());
			canvas.vertex(cameraVertice.x, cameraVertice.y);
			cameraVertice = scene.camera().projectedCoordinatesOf(this.triangles.get(i).getPoint3());
			canvas.vertex(cameraVertice.x, cameraVertice.y);
		}
		
		canvas.endShape(PGraphics.CLOSE);
		
		canvas.popStyle();
		canvas.popMatrix();

	}
}