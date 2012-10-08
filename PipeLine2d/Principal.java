import processing.core.PApplet;

@SuppressWarnings("serial")
public class Principal extends PApplet {
	int numSegments = 3;
	float[] x = new float[numSegments];
	float[] y = new float[numSegments];
	float[] angle = new float[numSegments];
	float segLength = 50;
	float targetX, targetY;

	public void setup() {
		size(640, 360);
		strokeWeight(20);
		stroke(255, 100);
		x[x.length - 1] = width / 2; // Set base x-coordinate
		y[x.length - 1] = height / 2; // Set base y-coordinate
	}

	public void draw() {
		background(0);

		reachSegment(0, mouseX, mouseY);
		for (int i = 1; i < numSegments; i++) {
			reachSegment(i, targetX, targetY);
		}
		for (int i = x.length - 1; i >= 1; i--) {
			positionSegment(i, i - 1);
		}
		for (int i = 0; i < x.length; i++) {
			segment(x[i], y[i], angle[i], (i + 1) * 2);
		}
	}

	void positionSegment(int a, int b) {
		x[b] = x[a] + cos(angle[a]) * segLength;
		y[b] = y[a] + sin(angle[a]) * segLength;
	}

	void reachSegment(int i, float xin, float yin) {
		float dx = xin - x[i];
		float dy = yin - y[i];
		angle[i] = atan2(dy, dx);
		targetX = xin - cos(angle[i]) * segLength;
		targetY = yin - sin(angle[i]) * segLength;
	}

	void segment(float x, float y, float a, float sw) {
		strokeWeight(sw);
		pushMatrix();
		translate(x, y);
		rotate(a);
		line(0, 0, segLength, 0);
		popMatrix();
	}

}
