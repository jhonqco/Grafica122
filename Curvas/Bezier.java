import processing.core.PApplet;
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
	}

}
