import processing.core.PApplet;

@SuppressWarnings("serial")
public class linea extends PApplet {
	private int esc=2;
	
	public void setup() {
		size(500, 500);
		background(0);
		stroke(255);
	}

	public void draw() {
	}

	public void mouseClicked() {
		punto p2 = new punto(mouseX, mouseY);
		punto p1 = new punto(0, 0);
		point(p2.getx(), p2.gety());
		int m = p1.pend(p2);
		int dx = p2.getx() - p1.getx();
		int dy = p2.gety() - p1.gety();
		int dx1 = 2 * (dx);
		int dy1 = 2 * (dy);
		int d = 2 * (dy) - (dx);
		if (m >= 0 && m <= 1) {
			while (p1.x < p2.x) {
				p1.setx(p1.getx() + esc);
				if (d <= 0) {
					d = d + dy1;
				}
				else {
					p1.sety(p1.gety() + esc);
					d = d + dy1 - dx1;
				}
				rect(p1.getx(), p1.gety(),esc, esc);
			}
		}
	}

}
