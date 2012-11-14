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
		canvas.beginShape();
		for (PVector vertice : vertices) {
			canvas.vertex(vertice.x, vertice.y, vertice.z);
		}
		canvas.endShape(PGraphics.CLOSE);

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
		
		PVector position = scene.camera().projectedCoordinatesOf(iFrame.position());
		System.out.println(position);
		canvas.translate(position.x,position.y);
		
		canvas.stroke(this.getColor());
		canvas.beginShape();
		for (PVector vertice : vertices) {
			PVector cameraVertice = scene.camera().projectedCoordinatesOf(vertice);
			canvas.vertex(cameraVertice.x, cameraVertice.y);
		}
		canvas.endShape(PGraphics.CLOSE);

		canvas.popStyle();
		canvas.popMatrix();

	}
}