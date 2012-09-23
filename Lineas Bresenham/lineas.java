import processing.core.PApplet;

@SuppressWarnings("serial")
public class lineas extends PApplet {

	boolean v = true;
	int x0 = 0;
	int y0 = 0;
	int x1 = 0;
	int y1 = 0;

	public void setup() {
		size(500, 500);
		background(255);
	}

	public void draw() {
	}

	public void mouseClicked() {
		ellipse(mouseX, mouseY, 5, 5);
		if (x0 == 0 && y0 == 0) {
			x0 = mouseX;
			y0 = mouseY;
			return;
		} else {
			x1 = mouseX;
			y1 = mouseY;
		}
		float m=(float)(y0-y1)/(x0-x1);
		System.out.println(m);
		if (x0 > x1) {
			int t = x0;
			x0 = x1;
			x1 = t;
			t = y0;
			y0 = y1;
			y1 = t;
		}

		int x, p;
		int y = y0;
		int dx = x1 - x0;
		int dy = y1 - y0;
		
		if(x0==x1){
			x=x0;
			for(y=y0;y<=y1;y=y+1){
				point(x,y);
			}
		}
		
		
		
		else if (m >= 0) {
			if (dy < dx) {
				p = dx - 2 * dy;
				for (x = x0; x <= x1; x = x + 1) {
					point(x, y);
					if (p < 0) {
						y = y + 1;
						p = p + 2 * dx - 2 * dy;
					} else {
						p = p - 2 * dy;
					}
				}
			} else {
				x = x0;
				p = dy - 2 * dx;
				for (y = y0; y <= y1; y = y + 1) {
					point(x, y);
					if (p < 0) {
						x = x + 1;
						p = p + 2 * dy - 2 * dx;
					} else {
						p = p - 2 * dx;
					}
				}
			}
		}
		

		else if (m < 0) {
			
			if (m>=-1) {
				p = -dx - 2 * dy;
				y=y0;
				for (x = x0; x <= x1; x = x + 1) {
					point(x,y);
					
					if (p > 0) {
						y = y - 1;
						p = p - 2 * dx - 2 * dy;
					} else {
						p = p - 2 * dy;
					}
				}
			}
			else{
				x = x0;
				dx=abs(dx);
				dy=abs(dy);
				p = -dx + 2 * dy;
				for (y = y0; y >= y1; y = y - 1) {
					point(x, y);
					if (p > 0) {
						x = x + 1;
						p = p - 2 * dy + 2 * dx;
					} else {
						p = p + 2 * dx;
					}
				}
			}
		}
		x0 = 0;
		y0 = 0;
	}

}
