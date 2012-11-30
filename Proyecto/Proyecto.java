import processing.core.PApplet;
import processing.core.PGraphics;
import remixlab.proscene.Scene;
import remixlab.remixcam.core.Camera;
import remixlab.remixcam.core.InteractiveFrame;


@SuppressWarnings("serial")
public class Proyecto extends PApplet{
	private Scene escena;
	private InteractiveFrame itf;
	@Override
	public void setup() {
		size(640, 480, PGraphics.P2D);
		escena = new Scene(this);
		itf = new InteractiveFrame(escena);
	}
	@Override
	public void draw() {
		fill(255);
		background(0);
		pushMatrix();
		translate(itf.position().x(),itf.position().y());
		ellipse(0, 0, 50, 50);
		popMatrix();
	}
}
