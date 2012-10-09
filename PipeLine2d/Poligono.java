import java.util.ArrayList;

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

	@SuppressWarnings("static-access")
	public void dibujar(PGraphics canvas){
		canvas.beginShape();
		for(PVector vertice : vertices){
			canvas.vertex(vertice.x, vertice.y);
		}
		canvas.endShape(canvas.CLOSE);
	}

	/**
	 * @return the vertices
	 */
	public ArrayList<PVector> getVertices() {
		return vertices;
	}

	/**
	 * @param vertices the vertices to set
	 */
	public void setVertices(ArrayList<PVector> vertices) {
		this.vertices = vertices;
	}

}
