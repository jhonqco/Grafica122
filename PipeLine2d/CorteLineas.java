import java.util.ArrayList;

import processing.core.PVector;

public class CorteLineas {
	int xIzq, yMin, xDer, yMax;
	
	PVector temp;

	public Poligono recorte(Poligono polygon, int xIzq, int yMin, int xDer, int yMax) {
		this.xIzq = xIzq;
		this.yMin = yMin;
		this.xDer = xDer;
		this.yMax = yMax;
		ArrayList<PVector> puntos = polygon.getVertices();
		Poligono nuevosPoligono = new Poligono();

		int pa = 1;
		for (PVector punto : puntos) {
			if (pa > 0) {
				temp = punto;
			} else {
				if (CohenSutherland(temp, punto)) {
					nuevosPoligono.getVertices().add(temp);
					nuevosPoligono.getVertices().add(punto);
					// line(temp.x, temp.y, punto.x, punto.y);
				}
			}
			pa = -pa;
		}

		return nuevosPoligono;
	}

	private int bits(PVector P) {
		int cod = 0;
		if (P.y < yMin)
			cod = cod + 8;
		else if (P.y > yMax)
			cod = cod + 4;
		if (P.x > xDer)
			cod = cod + 2;
		else if (P.x < xIzq)
			cod = cod + 1;
		return cod;
	}

	private boolean rechazar(int cod1, int cod2) {
		if ((cod1 & cod2) != 0)
			return true;
		return false;
	}

	private boolean aceptar(int cod1, int cod2) {
		if (cod1 == 0 && cod2 == 0)
			return true;
		return false;
	}

	private boolean CohenSutherland(PVector P0, PVector P1) {
		int Code0, Code1;
		while (true) {
			Code0 = bits(P0);
			Code1 = bits(P1);
			if (rechazar(Code0, Code1))
				return false;
			if (aceptar(Code0, Code1))
				return true;
			if (Code0 == 0) {
				int tempCoord;
				int tempCode;
				tempCoord = (int) P0.x;
				P0.x = P1.x;
				P1.x = tempCoord;
				tempCoord = (int) P0.y;
				P0.y = P1.y;
				P1.y = tempCoord;
				tempCode = Code0;
				Code0 = Code1;
				Code1 = tempCode;
			}
			if ((Code0 & 8) != 0) {
				P0.x += (P1.x - P0.x) * (yMin - P0.y) / (P1.y - P0.y);
				P0.y = yMin;
			} else if ((Code0 & 4) != 0) {
				P0.x += (P1.x - P0.x) * (yMax - P0.y) / (P1.y - P0.y);
				P0.y = yMax;
			} else if ((Code0 & 2) != 0) {
				P0.y += (P1.y - P0.y) * (xDer - P0.x) / (P1.x - P0.x);
				P0.x = xDer;
			} else if ((Code0 & 1) != 0) {
				P0.y += (P1.y - P0.y) * (xIzq - P0.x) / (P1.x - P0.x);
				P0.x = xIzq;
			}
		}
	}

}
