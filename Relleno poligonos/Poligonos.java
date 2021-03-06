import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import processing.core.PApplet;
import processing.core.PVector;

@SuppressWarnings("serial")
public class Poligonos extends PApplet {
	ArrayList<ArrayList<PVector>> listPolygons = new ArrayList<ArrayList<PVector>>();
	ArrayList<PVector> vertices = new ArrayList<PVector>();
	TreeMap<Integer, ArrayList<PVector>> edgeTable;

	public void setup() {
		size(500, 500);
		background(0);
	}

	public void draw() {

	}

	public void mouseClicked() {
		if (mouseButton == LEFT) {
			// A�adir vertice
			vertices.add(new PVector(mouseX, mouseY));

		} else if (mouseButton == RIGHT && !vertices.isEmpty()) {
			// Cerrar poligono y a�adir a la lista
			vertices.add(vertices.get(0));
			vertices.trimToSize();
			listPolygons.add(vertices);
			vertices = new ArrayList<PVector>();
			background(0);
			// Parte importante ET y relleno
			buildEdgeTable();
			fillPolygon();
			
			for (ArrayList<PVector> vertices : listPolygons) {
				drawListOfVertices(vertices, false);
			}
		}
		// Dibujar bordes del poligono en construccion
		drawListOfVertices(vertices, true);
	}

	private void fillPolygon() {
		stroke(0, 0, 150);
		ArrayList<PVector> activeEdgeTable = new ArrayList<PVector>();
		Iterator<Integer> enumeration = edgeTable.keySet().iterator();
		int nextKey = enumeration.next();

		for (int y = nextKey; y <= nextKey; y++) {
			if (y == nextKey) {
				// Dejar aristas utilies de la "activeEdgeTable"
				ArrayList<PVector> aet = new ArrayList<PVector>();
				for (PVector edge : activeEdgeTable) {
					if (edge.x > y) {
						aet.add(edge);
					}
				}
				// A�adir aristas
				activeEdgeTable = aet;
				activeEdgeTable.addAll(edgeTable.get(y));
				sortByYZ(activeEdgeTable);
				System.out.print("AET ");
				this.printAET(activeEdgeTable);

				if (enumeration.hasNext()) {
					nextKey = enumeration.next();
				}
			}
			for (int v = 0; v < activeEdgeTable.size() - 1; v += 2) {
				int x = (int) activeEdgeTable.get(v).y;
				while (x < activeEdgeTable.get(v + 1).y) {
					point(x++, y);
				}
				activeEdgeTable.get(v).y += activeEdgeTable.get(v).z;
				activeEdgeTable.get(v + 1).y += activeEdgeTable.get(v + 1).z;
			}
		}
	}

	private void printAET(ArrayList<PVector> aet) {
		for (PVector edge : aet) {
			System.out.print(edge.toString() + " -> ");
		}
		System.out.println();
	}

	@SuppressWarnings("unchecked")
	private void buildEdgeTable() {
		edgeTable = new TreeMap<Integer, ArrayList<PVector>>();
		int maxY = 0;
		for (ArrayList<PVector> vertices : listPolygons) {
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
					// Ordenar las aristas por Xmin y 1/m
					newLista = (ArrayList<PVector>) lista.clone();
					newLista.add(edge);
					sortByYZ(newLista);
				}
				edgeTable.put((int) min.y, newLista);
				maxY = (int) max(max.y, maxY);
			}
			edgeTable.put(maxY, new ArrayList<PVector>(1));
		}
		// Imprimir en consola edgeTable
		System.out.println("ET");
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

	private void sortByYZ(ArrayList<PVector> list) {
		Collections.sort(list, new Comparator<PVector>() {
			public int compare(PVector a, PVector b) {
				if (a.y < b.y) {
					return -1;
				} else if (a.y == b.y && a.z < b.z) {
					return -1;
				} else if (a.y == b.y && a.z == b.z) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		list.trimToSize();
	}

	private void drawListOfVertices(ArrayList<PVector> vertices, boolean printCoordinates) {
		stroke(255, 0, 0);
		beginShape();
		for (PVector vertice : vertices) {
			if (printCoordinates) {
				fill(0, 255, 0);
				text("(" + vertice.x + ", " + vertice.y + ")", vertice.x, vertice.y);
				noFill();
			}
			ellipse(vertice.x, vertice.y, 5, 5);
			vertex(vertice.x, vertice.y);
		}
		endShape();
	}

}
