import java.util.ArrayList;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Poligono {
	private ArrayList<PVector> vertices;

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
		if(vertices.size() == 0)
			return new PVector();
		float maxX = vertices.get(0).x;
		float minX = maxX;
		float maxY = vertices.get(0).y;
		float minY = maxY;
		for (PVector vertex : vertices) {
			// Actualizando valores en x
			if (vertex.x > maxX)
				maxX = vertex.x;
			else if (vertex.x < minX)
				minX = vertex.x;
			// Actualizando valores en Y
			if (vertex.y > maxY)
				maxY = vertex.y;
			else if (vertex.y < minY)
				minY = vertex.y;
		}
		return new PVector((maxX + minX) / 2, (maxY + minY) / 2);
	}

	/**
	 * @return the vertices
	 */
	public ArrayList<PVector> getVertices() {
		return vertices;
	}

	/**
	 * @param vertices
	 *            the vertices to set
	 */
	public void setVertices(ArrayList<PVector> vertices) {
		this.vertices = vertices;
	}

	public int getWidth() {
		float minX = 0, maxX = 0;
		for (int v = 0; v < vertices.size(); v++) {
			float xValue = vertices.get(v).x;
			if (v == 0) {
				minX = xValue;
				maxX = minX;
			} else {
				if (xValue > maxX)
					maxX = xValue;
				else if (xValue < minX)
					minX = xValue;
			}
		}
		return (int) Math.abs(maxX-minX);
	}

	public int getHeight() {
		float minY = 0, maxY = 0;
		for (int v = 0; v < vertices.size(); v++) {
			float xValue = vertices.get(v).y;
			if (v == 0) {
				minY = xValue;
				maxY = minY;
			} else {
				if (xValue > maxY)
					maxY = xValue;
				else if (xValue < minY)
					minY = xValue;
			}
		}
		return (int) Math.abs(maxY-minY);
	}

	public int getXmin() {
		float minX = 0, maxX = 0;
		for (int v = 0; v < vertices.size(); v++) {
			float xValue = vertices.get(v).x;
			if (v == 0) {
				minX = xValue;
				maxX = minX;
			} else {
				if (xValue > maxX)
					maxX = xValue;
				else if (xValue < minX)
					minX = xValue;
			}
		}
		return (int) minX;
	}

	public int getYmin() {
		float minY = 0, maxY = 0;
		for (int v = 0; v < vertices.size(); v++) {
			float xValue = vertices.get(v).y;
			if (v == 0) {
				minY = xValue;
				maxY = minY;
			} else {
				if (xValue > maxY)
					maxY = xValue;
				else if (xValue < minY)
					minY = xValue;
			}
		}
		return (int) minY;
	}

	public int getYmax() {
		float minY = 0, maxY = 0;
		for (int v = 0; v < vertices.size(); v++) {
			float xValue = vertices.get(v).y;
			if (v == 0) {
				minY = xValue;
				maxY = minY;
			} else {
				if (xValue > maxY)
					maxY = xValue;
				else if (xValue < minY)
					minY = xValue;
			}
		}
		return (int) maxY;
	}

	public int getXmax() {
		float minX = 0, maxX = 0;
		for (int v = 0; v < vertices.size(); v++) {
			float xValue = vertices.get(v).x;
			if (v == 0) {
				minX = xValue;
				maxX = minX;
			} else {
				if (xValue > maxX)
					maxX = xValue;
				else if (xValue < minX)
					minX = xValue;
			}
		}
		return (int) maxX;
	}

}
