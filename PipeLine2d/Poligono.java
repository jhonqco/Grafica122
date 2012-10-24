import java.util.ArrayList;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Poligono {
	private ArrayList<PVector> vertices;
	private int maxX = Integer.MIN_VALUE;
	private int minX = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int numOfVertices = 0;

	public Poligono() {
		vertices = new ArrayList<PVector>();
	}

	public Poligono(int size) {
		vertices = new ArrayList<PVector>(size);
	}

	public void dibujar(PGraphics canvas) {
		canvas.beginShape();
		for (PVector vertice : vertices) {
			canvas.vertex(vertice.x, vertice.y);
		}
		canvas.endShape(PConstants.CLOSE);
	}

	public PVector getCenter() {
		if (vertices.size() == 0)
			return new PVector(0, 0, 0);
		else if (numOfVertices != vertices.size()) {
			maxX = Integer.MIN_VALUE;
			minX = Integer.MAX_VALUE;
			maxY = Integer.MIN_VALUE;
			minY = Integer.MAX_VALUE;
			for (PVector vertex : vertices) {
				// Actualizando valores en x
				if (vertex.x > maxX)
					maxX = (int) vertex.x;
				else if (vertex.x < minX)
					minX = (int) vertex.x;
				// Actualizando valores en Y
				if (vertex.y > maxY)
					maxY = (int) vertex.y;
				else if (vertex.y < minY)
					minY = (int) vertex.y;
			}
			numOfVertices = vertices.size();
		}
		return new PVector((maxX + minX) / 2, (maxY + minY) / 2);
	}

	/**
	 * @return the vertices
	 */
	public ArrayList<PVector> getVertices() {
		return vertices;
	}

}
