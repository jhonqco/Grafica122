import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PGraphicsOpenGL;
import remixlab.proscene.CameraProfile;
import remixlab.proscene.Scene;

/**
 * @author Sebastian
 * 
 */
@SuppressWarnings("serial")
public class PrincipalHSR extends PApplet {

	private Scene scene;
	private ArrayList<Box> boxes;
	private PGraphics leftCanvas;
	private PGraphics rightCanvas;
	private Pintor pintor;

	public void setup() {
		int numberOfBoxes = 50;

		size(640, 480, P3D);
		leftCanvas = createGraphics(width / 2, height, P3D);
		rightCanvas = createGraphics(width / 2, height);
		scene = new Scene(this, (PGraphicsOpenGL) leftCanvas);

		// add the click actions to all camera profiles
		CameraProfile[] camProfiles = scene.getCameraProfiles();
		for (int i = 0; i < camProfiles.length; i++) {
			// middle click will show all the scene:
			camProfiles[i].setClickBinding(Scene.Button.MIDDLE, Scene.ClickAction.SHOW_ALL);
			// right click will will set the arcball reference point:
			camProfiles[i].setClickBinding(Scene.Button.RIGHT, Scene.ClickAction.ARP_FROM_PIXEL);
			// double click with the middle button while pressing SHIFT will
			// reset the arcball reference point:
			camProfiles[i].setClickBinding(Scene.Modifier.SHIFT.ID, Scene.Button.MIDDLE, 2,
					Scene.ClickAction.RESET_ARP);
		}

		scene.setGridIsDrawn(false);
		scene.setAxisIsDrawn(false);
		scene.setRadius(200);
		scene.enableFrustumEquationsUpdate();
		scene.showAll();

		boxes = new ArrayList<Box>(numberOfBoxes);
		// create an array of boxes with random positions, sizes and colors
		for (int i = 0; i < numberOfBoxes; i++) {
			boxes.add(new Box(scene));

		}
		pintor = new Pintor();
		pintor.setPlanos(boxes);
	}

	public void draw() {
		leftCanvas.beginDraw();
		leftCanvas.hint(PGraphics.DISABLE_DEPTH_TEST);
		// Manejar eventos mouse segun posicion
		if (mouseX > leftCanvas.width) {
			scene.disableMouseHandling();
		} else {
			scene.enableMouseHandling();
		}

		scene.beginDraw();
		scene.renderer().background(0);
		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).draw();
			
		}
		scene.endDraw();
		leftCanvas.endDraw();
		image(leftCanvas, 0, 0);

		// Dibujar canvas derecho
		rightCanvas.beginDraw();
		rightCanvas.background(255);

		pintor.dibujarPlanos(rightCanvas);

		rightCanvas.endDraw();
		image(rightCanvas, width / 2, 0);
	}
}
