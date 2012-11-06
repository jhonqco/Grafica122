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
	float deltaU = (float) 0.001;

	public void setup() {
		size(640, 480, P3D);
		int nbKeyFrames = 4;
		scene = new Scene(this);
		scene.setAxisIsDrawn(false);
		scene.setGridIsDrawn(false);
		scene.setRadius(80);
		scene.showAll();
		scene.setFrameSelectionHintIsDrawn(true);

		// Buttons
		button1 = new ClickButton(scene, new PVector(width / 2, 10), "+", 32, true, this);
		button2 = new ClickButton(scene, new PVector((button1.position.x + button1.myWidth + 5),
				button1.position.y), "-", 32, false, this);

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
			if (keyFrames.get(i).grabsMouse()) {
				scene.drawAxis(30);
			} else
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

		for (float u = 0; u <= 1; u += deltaU) {
			PVector pu = new PVector(0, 0, 0);

			for (int k = 0; k <= n; k++) {
				PVector pk = keyFrames.get(k).position();
				pk.mult(BEZ(k, n, u));
				pu.add(pk);
			}

			this.drawPoint(pu, 255, 0, 0);
		}
	}

	public static float BEZ(int k, int n, float u) {
		if (k == n) {
			return (float) Math.pow(u, k);
		} else if (k == 0) {
			return (float) Math.pow((1 - u), (n));
		} else {
			float bez = ArithmeticUtils.binomialCoefficient(n, k);
			bez = (float) (bez * Math.pow(u, k));
			bez = (float) (bez * Math.pow((1 - u), (n - k)));
			return bez;
		}
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

		for (float u = 0; u <= 1; u += deltaU) {
			RealMatrix U = new Array2DRowRealMatrix(new double[][]
				{
					{ pow(u, 3), pow(u, 2), u, 1 } });

			RealMatrix pu = U.multiply(mBezMultiplyPoints);

			PVector puPosition = new PVector((float) pu.getEntry(0, 0), (float) pu.getEntry(0, 1),
					(float) pu.getEntry(0, 2));

			this.drawPoint(puPosition, 0, 255, 0);

		}
	}

	private void drawPoint(PVector puPosition, int red, int green, int blue) {
		pushStyle();
		stroke(red, green, blue);
		scene.parent.point(puPosition.x, puPosition.y, puPosition.z);
		popStyle();
	}

	public void addControlPoint() {
		InteractiveFrame newIFrame = new InteractiveFrame(scene);
		newIFrame.setPosition(scene.camera().unprojectedCoordinatesOf(
				new PVector(random(width / 2, width), random(height / 2, height), (float) 0.8)));

		keyFrames.add(newIFrame);
	}

	public void removeFirstControlPoint() {
		scene.mouseGrabberPool().remove(keyFrames.get(0));
		keyFrames.remove(0);
	}
}
