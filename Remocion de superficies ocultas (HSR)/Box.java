import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Quaternion;
import remixlab.proscene.Scene;

public class Box {
	private ArrayList<Triangle3D> vertices = new ArrayList<Triangle3D>();

	private InteractiveFrame iFrame;
	private float width, height, depth;
	private int color;
	private PGraphics canvas;
	private Scene scene;

	Box(Scene scene) {
		iFrame = new InteractiveFrame(scene);
		canvas = scene.renderer();
		this.scene = scene;

		// sets size randomly
		width = scene.parent.random(10, 40);
		height = scene.parent.random(10, 40);
		depth = scene.parent.random(10, 40);

		vertices.add(new Triangle3D(new PVector(width, 0),new PVector(0, height, 0)));

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
		for (Triangle3D vertice : vertices) {
			vertice.drawOn(canvas);
		}

		canvas.popStyle();
		canvas.popMatrix();
	}

	public void setSize(float myW, float myH, float myD) {
		width = myW;
		height = myH;
		depth = myD;
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
		
		PVector position = scene.camera().cameraCoordinatesOf(iFrame.position());
		canvas.translate(position.x,position.y);
		
		canvas.beginShape();
		for (Triangle3D vertice : vertices) {
			for(PVector point: vertice.getPoints()){
			PVector cameraVertice = iFrame.coordinatesOf(point); 
			cameraVertice = scene.camera().projectedCoordinatesOf(cameraVertice);
			canvas.vertex(cameraVertice.array());
			}
		}
		canvas.endShape(PGraphics.CLOSE);

		canvas.popStyle();
		canvas.popMatrix();

	}
}