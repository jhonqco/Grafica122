import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import processing.core.PApplet;
import processing.core.PVector;

@SuppressWarnings("serial")
public class Poligonos extends PApplet {
	ArrayList<PVector> vertices = new ArrayList<PVector>();
	Hashtable<Integer, ArrayList<PVector>> edgeTable;
	ArrayList<PVector> activeEdgeTable = null;

	public void setup() {
		size(500, 500);
		smooth();
		background(0);
	}

	public void draw() {

	}

	public void mouseClicked() {
		if (mouseButton == LEFT) {
			// Añadir vertice
			vertices.add(new PVector(mouseX, mouseY));
			text("("+mouseX+", "+mouseY+")", mouseX, mouseY);

		} else if (mouseButton == RIGHT && !vertices.isEmpty()) {
			// Cerrar poligono
			vertices.add(vertices.get(0));
			buildEdgeTable();
			fillPolygon();
		}
		// Dibujar poligono
		stroke(255, 0, 0);
		noFill();
		beginShape();
		for (PVector vertice : vertices) {
			vertex(vertice.x, vertice.y);
		}
		endShape();
	}

	private void fillPolygon() {
		activeEdgeTable = new ArrayList<PVector>();
		int y=0;
		Enumeration<Integer> enumeration = edgeTable.keys();
		while(enumeration.hasMoreElements()){
			y = enumeration.nextElement();
			stroke(255);
			activeEdgeTable.addAll(edgeTable.get(y));
			for(int x=0; x<width;x++){
				point(x,y);
			}
		}
	}

	private void buildEdgeTable() {
		edgeTable = new Hashtable<Integer, ArrayList<PVector>>();
		for (int i = 0; i < vertices.size() - 1; i++) {
			PVector min = vertices.get(i);
			PVector max = vertices.get(i + 1);
			if(min.y == max.y) continue;
			float m = (max.y - min.y) / (max.x - min.x);
			PVector edge = new PVector(max.y, max.x, 1 / m);
			// Agregar la arista a la "edge Table"
			ArrayList<PVector> lista = edgeTable.get(min.y);
			if (lista == null) {
				lista = new ArrayList<PVector>();
				lista.add(edge);
			} else {
				// Ordenar las aristas por Xmin y 1/m
				for (PVector edgeInLista : lista) {
					if (edge.y < edgeInLista.y) {
						lista.add(lista.indexOf(edgeInLista), edge);
					} else if (edge.y == edgeInLista.y
							&& edge.z <= edgeInLista.z) {
						lista.add(lista.indexOf(edgeInLista), edge);
					}
				}
			}
			edgeTable.put((int) min.y, lista);
		}
		// Imprimir en consola edgeTable
		Enumeration<Integer> keys=edgeTable.keys();
		while(keys.hasMoreElements()){
			int key = keys.nextElement();
			System.out.print(key+" => ");
			for(PVector edge: edgeTable.get(key)){
				System.out.print(edge+" -> ");
			}
			System.out.println();
		}
	}

}
