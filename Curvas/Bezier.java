import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.ArithmeticUtils;

import processing.core.PApplet;
import processing.core.PVector;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Scene;

@SuppressWarnings("serial")
public class Bezier extends PApplet {

	Scene scene;
	ArrayList<InteractiveFrame> keyFrames;
	private ClickButton button1;
	private ClickButton button2;

	public void setup() {
		size(640, 480, P3D);
		int nbKeyFrames = 4;
		scene = new Scene(this);
		scene.setAxisIsDrawn(false);
		scene.setGridIsDrawn(false);
		scene.setRadius(70);
		scene.showAll();
		scene.setFrameSelectionHintIsDrawn(true);

		// Buttons
		button1 = new ClickButton(scene, new PVector(10, 10), "+", 32, true, this);
		button2 = new ClickButton(scene, new PVector((10 + button1.myWidth + 5), 10), "-", 32,
				false, this);

		// An array of interactive (key) frames.
		keyFrames = new ArrayList<InteractiveFrame>(nbKeyFrames);
		// Create an initial path
		for (int i = 0; i < nbKeyFrames; i++) {
			keyFrames.add(i, new InteractiveFrame(scene));
			keyFrames.get(i).setPosition(-100 + 200 * i / (nbKeyFrames - 1), 0, 0);
		}

	}

	public void draw() {
		background(0);
		button1.display();
		button2.display();

		for (int i = 0; i < keyFrames.size(); ++i) {
			pushMatrix();
			PVector position = keyFrames.get(i).position();
			translate(position.x, position.y, position.z);
			if (keyFrames.get(i).grabsMouse())
				scene.drawAxis(40);
			else
				scene.drawAxis(20);

			popMatrix();
		}
		// Draw Bezier curve
		bezier();
	}

	public void bezier() {
		if (keyFrames.size() == 4) {
			cubicBezier();
		} else {
			generalBezier();
		}
	}

	private void generalBezier() {
		int n = keyFrames.size() - 1;

		for (float u = 0; u <= 1; u += 0.01) {
			PVector pu = new PVector(0, 0, 0);
			
			for (int k = 0; k <= n; k++) {
				PVector pk = keyFrames.get(k).position();
				pk.mult(BEZ(k, n, u));
				pu.add(pk);
			}
			
			pushMatrix();
			translate(pu.x, pu.y, pu.z);
			pushStyle();
			fill(255, 0, 0);
			noStroke();
			scene.parent.sphere(1);
			popStyle();
			popMatrix();
		}
	}

	private float BEZ(int k, int n, float u) {
		float bez = ArithmeticUtils.binomialCoefficient(n, k);
		bez = (float) (bez * Math.pow(u, k));
		bez = (float) (bez * Math.pow((1 - u), (n - k)));

		return bez;
	}

	private void cubicBezier() {
		// Matriz caracteristica
		RealMatrix mBez = new Array2DRowRealMatrix(new double[][]
			{
				{ -1, 3, -3, 1 },
				{ 3, -6, 3, 0 },
				{ -3, 3, 0, 0 },
				{ 1, 0, 0, 0 } });

		// Puntos de control
		RealMatrix points = new Array2DRowRealMatrix(4, 3);
		for (int i = 0; i < 4; i++) {
			PVector position = keyFrames.get(i).position();
			points.setRow(i, new double[]
				{ position.x, position.y, position.z });
		}

		RealMatrix mBezMultiplyPoints = mBez.multiply(points);

		for (float u = 0; u <= 1; u += 0.006) {
			RealMatrix U = new Array2DRowRealMatrix(new double[][]
				{
					{ pow(u, 3), pow(u, 2), u, 1 } });

			RealMatrix pu = U.multiply(mBezMultiplyPoints);

			PVector puPosition = new PVector((int) pu.getEntry(0, 0), (int) pu.getEntry(0, 1),
					(int) pu.getEntry(0, 2));

			pushMatrix();
			translate(puPosition.x, puPosition.y, puPosition.z);
			pushStyle();
			fill(0, 255, 0);
			noStroke();
			scene.parent.sphere(1);
			popStyle();
			popMatrix();
		}
	}

	public void addControlPoint() {
		InteractiveFrame newIFrame = new InteractiveFrame(scene);
		newIFrame.setPosition(scene.camera().unprojectedCoordinatesOf(
				new PVector(mouseX, mouseY, (float) 0.8)));

		keyFrames.add(newIFrame);
	}

	public void removeFirstControlPoint() {
		scene.mouseGrabberPool().remove(keyFrames.get(0));
		keyFrames.remove(0);
	}
}
