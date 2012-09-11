import processing.core.PApplet;

@SuppressWarnings("serial")
public class Circunferencias extends PApplet {
	int x0 = -1;
	int y0 = -1;
	int x1 = 0;
	int y1 = 0;

	public void setup() {
		
	}

	public void draw() {

	}

	public void mouseClicked() {
		ellipse(mouseX, mouseY, 5, 5);
		if (x0 == -1 && y0 == -1) {
			x0 = mouseX;
			y0 = mouseY;
			return;
		} else {
			x1 = mouseX;
			y1 = mouseY;
		}
		int r = (int) sqrt(sq(x0) + sq(y0));
		int d = 1 - r + (1 / 4);
		x0 = 0;
		y0 = r;
		point(x0++, y0);
		translate(width/2, height/2);
		while (y0 / x0 > 1) {
			point(x0++, y0);
			if (d < 0) {
				d = d + (2 * x0 + 3);
			} else {
				y0 = y0 - 1;
				d += (2 * x0) - (2 * y0) + 5;
			}
		}
	}
}
