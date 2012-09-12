import processing.core.PApplet;

@SuppressWarnings("serial")
public class Elipses extends PApplet {
	int x1 = 0;
	int y1 = 0;
	int a, b;
	boolean ejeY = true;

	public void setup() {
		stroke(255, 0, 0);
		line(0, 0, width, height);
		line(width, 0, 0, height);
		line(0, height / 2, width, height / 2);
		line(width / 2, 0, width / 2, height);
		stroke(0);
		strokeWeight(2);
	}

	public void draw() {
	}

	public void mouseClicked() {
		pushMatrix();
		translate(width / 2, height / 2);
		x1 = mouseX - width / 2;
		y1 = mouseY - height / 2;

		if (ejeY) {
			b = abs(y1);
			ellipse(0, y1, 5, 5);
			ejeY=false;
			return;
		} else {
			ejeY=true;
			a = abs(x1);
			ellipse(x1, 0, 5, 5);
		}
		int d = (int) (sq(b) + sq(a) * (1 / 4 - b));
		x1 = 0;
		y1 = b;
		
		for (; y1 >= 0 && x1 <= a;) {
			point(x1, y1); 	// cuadrante 1
			point(-x1,y1);	// cuadrante 2
			point(-x1, -y1);// cuadrante 3
			point(x1, -y1);	// cuadrante 4
			
			// Region 1
			if(sq(a)*(y1-1/2) > sq(b)*(x1+1)){
				
				if(d<0){
					d+=sq(b)*(2*x1 +3);
				}else{
					y1--;
					d+=sq(b)*(2*x1 +3) + sq(a)*(2-2*y1);
				}
				x1++;
			}
			// Region 2
			else{
				if(d < 0){
					d+=sq(b)*(2*x1 +2) + sq(a)*(3-2*y1);
					x1++;
				}else{
					d+=sq(a)*(3-2*y1);
				}
				y1--;
			}
		}
		popMatrix();
	}
}
