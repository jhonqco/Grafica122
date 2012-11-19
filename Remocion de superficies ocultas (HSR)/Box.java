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
		int maxSize=50;
		int minSize=10;
		width = scene.parent.random(minSize, maxSize);
		height = scene.parent.random(minSize, maxSize);
		depth = scene.parent.random(minSize, maxSize);
		
		// sets color randomly
				color = scene.parent.color(scene.parent.random(0, 255), scene.parent.random(0, 255),
						scene.parent.random(0, 255));

		vertices.add(new Triangle3D(new PVector(width, 0),new PVector(0, height, 0), color));

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

	public ArrayList<Triangle3D> getPlanesCameraCoord() {
		ArrayList<Triangle3D> planes = new ArrayList<Triangle3D>(vertices.size());
		PVector position = scene.camera().cameraCoordinatesOf(iFrame.position());
		for (Triangle3D vertice : vertices) {
			PVector point1 = scene.camera().projectedCoordinatesOf(vertice.getPoints()[0]);
			point1.add(position);
			PVector point2 = scene.camera().projectedCoordinatesOf(vertice.getPoints()[1]);
			point2.add(position);
			PVector point3 = scene.camera().projectedCoordinatesOf(vertice.getPoints()[2]);
			point3.add(position);
			planes.add(new Triangle3D(point1, point2, point3,color));
		}
		return planes;
	}
}