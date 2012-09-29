import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import processing.core.PApplet;
import processing.core.PVector;

@SuppressWarnings("serial")
public class Poligonos extends PApplet {
	ArrayList<PVector> vertices = new ArrayList<PVector>();
	TreeMap<Integer, ArrayList<PVector>> edgeTable;
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
			text("(" + vertice.x + ", " + vertice.y + ")", vertice.x, vertice.y);
			ellipse(vertice.x, vertice.y, 5, 5);
			vertex(vertice.x, vertice.y);
		}
		endShape();
	}

	private void fillPolygon() {
		stroke(255);
		activeEdgeTable = new ArrayList<PVector>();
		Iterator<Integer> enumeration = edgeTable.keySet().iterator();
		int nextKey = enumeration.next();
		int y = nextKey;

		while (enumeration.hasNext()) {
			if (y == nextKey) {
				// Dejar aristas utilies de la "activeEdgeTable"
				ArrayList<PVector> aet = new ArrayList<PVector>();
				for (PVector edge : activeEdgeTable) {
					if (edge.x > y) {
						aet.add(edge);
					}
				}
				activeEdgeTable = aet;
				// Añadir aristas
				nextKey = enumeration.next();
				activeEdgeTable.addAll(edgeTable.get(y));
			}
			while (y < nextKey) {
				for (int v = 0; v < activeEdgeTable.size() - 1; v++) {
					int x = (int) activeEdgeTable.get(v).y;
					while (x < activeEdgeTable.get((int) (v + 1.)).y) {
						point(x++, y);
					}
					activeEdgeTable.get(v).y += activeEdgeTable.get(v).z;
					activeEdgeTable.get(v + 1).y += activeEdgeTable.get(v + 1).z;
				}
				y++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void buildEdgeTable() {
		edgeTable = new TreeMap<Integer, ArrayList<PVector>>();
		int maxY = 0;

		for (int i = 0; i < vertices.size() - 1; i++) {
			PVector min, max;
			if (vertices.get(i).y < vertices.get(i + 1).y) {
				min = vertices.get(i);
				max = vertices.get(i + 1);
			} else if (vertices.get(i).y > vertices.get(i + 1).y) {
				min = vertices.get(i + 1);
				max = vertices.get(i);
			} else {
				continue;
			}
			float m = (max.y - min.y) / (max.x - min.x);
			PVector edge = new PVector(max.y, min.x, 1 / m);

			// Agregar la arista a la "edge Table"
			ArrayList<PVector> lista = edgeTable.get((int) min.y);
			ArrayList<PVector> newLista;
			if (lista == null) {
				newLista = new ArrayList<PVector>();
				newLista.add(edge);
			} else {
				newLista = (ArrayList<PVector>) lista.clone();

				// Ordenar las aristas por Xmin y 1/m
				for (PVector edgeInLista : lista) {
					if (edge.y < edgeInLista.y) {
						newLista.add(lista.indexOf(edgeInLista), edge);
					} else if (edge.y == edgeInLista.y
							&& edge.z <= edgeInLista.z) {
						newLista.add(lista.indexOf(edgeInLista), edge);
					}
				}
			}
			edgeTable.put((int) min.y, newLista);
			maxY = (int) max(max.y, maxY);
		}
		edgeTable.put(maxY, new ArrayList<PVector>(1));
		// Imprimir en consola edgeTable
		Iterator<Integer> keys = edgeTable.keySet().iterator();
		while (keys.hasNext()) {
			int key = keys.next();
			System.out.print(key + " => ");
			for (PVector edge : edgeTable.get(key)) {
				System.out.print(edge + " -> ");
			}
			System.out.println();
		}
	}

}
