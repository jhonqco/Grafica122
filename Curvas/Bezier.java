import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import processing.core.PApplet;
import processing.core.PMatrix;
import processing.core.PMatrix3D;
import processing.core.PVector;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Scene;

@SuppressWarnings("serial")
public class Bezier extends PApplet {

	Scene scene;
	InteractiveFrame keyFrame[];
	int nbKeyFrames;

	public void setup() {
		size(640, 480, P3D);
		nbKeyFrames = 4;
		scene = new Scene(this);
		scene.setAxisIsDrawn(false);
		scene.setGridIsDrawn(false);
		scene.setRadius(70);
		scene.showAll();
		scene.setFrameSelectionHintIsDrawn(true);
		scene.setShortcut('f', Scene.KeyboardAction.DRAW_FRAME_SELECTION_HINT);

		// An array of interactive (key) frames.
		keyFrame = new InteractiveFrame[nbKeyFrames];
		// Create an initial path
		for (int i = 0; i < nbKeyFrames; i++) {
			keyFrame[i] = new InteractiveFrame(scene);
			keyFrame[i].setPosition(-100 + 200 * i / (nbKeyFrames - 1), 0, 0);
		}

	}

	public void draw() {
		background(0);

		for (int i = 0; i < nbKeyFrames; ++i) {
			pushMatrix();
			PVector position = keyFrame[i].position();
			translate(position.x, position.y, position.z);
			if (keyFrame[i].grabsMouse())
				scene.drawAxis(40);
			else
				scene.drawAxis(20);

			popMatrix();
		}
		//Dibujar curva de Bezier
		cubicBezier();
	}

	private void cubicBezier() {
		//Matriz caracteristica
		RealMatrix mBez = new Array2DRowRealMatrix(new double[][]
			{
				{ -1, 3, -3, 1 },
				{ 3, -6, 3, 0 },
				{ -3, 3, 0, 0 },
				{ 1, 0, 0, 0 } });
		
		// Puntos de control
		RealMatrix points = new Array2DRowRealMatrix(4, 3);
		for (int i = 0; i < nbKeyFrames; i++) {
			PVector position = keyFrame[i].position();
			points.setRow(i, new double[]
				{ position.x, position.y, position.z });
		}

		RealMatrix mBezMultiplyPoints = mBez.multiply(points);
		
		for (float u = 0; u <= 1; u += 0.01) {
			RealMatrix U = new Array2DRowRealMatrix(new double[][]
				{
					{ pow(u, 3), pow(u, 2), u, 1 } });
			
			RealMatrix pu = U.multiply(mBezMultiplyPoints);
			
			PVector puPosition = new PVector((int) pu.getEntry(0, 0), (int) pu.getEntry(0, 1),
					(int) pu.getEntry(0, 2));
			
			pushMatrix();
			translate(puPosition.x, puPosition.y, puPosition.z);
			pushStyle();
			fill(255, 0, 0);
			noStroke();
			scene.parent.sphere(1);
			popStyle();
			popMatrix();
		}
	}
}
