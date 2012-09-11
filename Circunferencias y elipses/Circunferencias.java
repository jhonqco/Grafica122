import processing.core.PApplet;

@SuppressWarnings("serial")
public class Circunferencias extends PApplet {
	int x1 = 0;
	int y1 = 0;

	public void setup() {
		stroke(255,0,0);
		line(0, 0, width, height);
		line(width, 0, 0, height);
		line(0,height/2,width,height/2);
		line(width/2,0,width/2,height);
		stroke(0);
	}

	public void draw() {

	}

	public void mouseClicked() {
		ellipse(mouseX, mouseY, 5, 5);
		x1 = mouseX-width/2;
		y1 = mouseY-height/2;
		int r = (int) sqrt(sq(x1) + sq(y1));
		int d = 1 - r + (1 / 4);
		x1 = 0;
		y1 = r;
		point(x1++, y1);
		translate(width/2, height/2);
		for (;y1 / x1 >= 1;x1++) {
			point(y1,x1);	// octante 1
			point(x1, y1);	// octante 2
			
			point(-x1,y1);	// octante 3
			point(-y1,x1);	// octante 4
			
			point(-y1,-x1);	// octante 5
			point(-x1,-y1);	// octante 6
			
			point(x1,-y1);	// octante 7
			point(y1,-x1);	// octante 8			
			
			if (d < 0) {
				d = d + (2 * x1 + 3);
			} else {
				y1 = y1 - 1;
				d += (2 * x1) - (2 * y1) + 5;
			}
		}
	}
}
