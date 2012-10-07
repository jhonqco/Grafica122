import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import processing.core.PVector;

public class Transform2D {
	public static ArrayList<PVector> translate(ArrayList<PVector> polygon, int dx, int dy) {
		ArrayList<PVector> translatedPolygon = new ArrayList<PVector>(polygon.size());

		double[][] t = { { dx }, { dy } };

		RealMatrix T = new Array2DRowRealMatrix(t);

		for (PVector vertice : polygon) {
			RealMatrix P = pVectorToMatrix(vertice);

			P = P.add(T);

			translatedPolygon.add(matrixToPVector(P));
		}
		return translatedPolygon;
	}

	public static ArrayList<PVector> scale(ArrayList<PVector> polygon, float s) {
		// Transladar al origen
		PVector center = centerOf(polygon);
		polygon = translate(polygon, (int) -center.x, (int) -center.y);
		// Escalar
		ArrayList<PVector> scaledPolygon = new ArrayList<PVector>(polygon.size());
		double[][] t = { { s, 0 }, { 0, s } };
		RealMatrix S = new Array2DRowRealMatrix(t);
		for (PVector vertice : polygon) {
			RealMatrix P = pVectorToMatrix(vertice);

			P = S.multiply(P);

			scaledPolygon.add(matrixToPVector(P));
		}
		// Transladar al la ubicacion original
		scaledPolygon = translate(scaledPolygon, (int) center.x, (int) center.y);
		return scaledPolygon;

	}

	public static ArrayList<PVector> rotate(ArrayList<PVector> polygon, float angle) {
		ArrayList<PVector> rotatedPolygon = new ArrayList<PVector>(polygon.size());
		// Transladar al origen
		PVector center = centerOf(polygon);
		polygon = translate(polygon, (int) -center.x, (int) -center.y);
		// Rotar el poligono
		double[][] t = { { Math.cos(angle), -Math.sin(angle) },
				{ Math.sin(angle), Math.cos(angle) } };
		RealMatrix R = new Array2DRowRealMatrix(t);
		for (PVector vertice : polygon) {
			RealMatrix P = pVectorToMatrix(vertice);

			P = R.multiply(P);

			rotatedPolygon.add(matrixToPVector(P));
		}
		// Transladar al la ubicacion original
		rotatedPolygon = translate(rotatedPolygon, (int) center.x, (int) center.y);
		return rotatedPolygon;
	}

	private static PVector centerOf(ArrayList<PVector> polygon) {
		float maxX = polygon.get(0).x, minX = maxX, maxY = polygon.get(0).y, minY = maxY;
		for (PVector vertex : polygon) {
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
		return new PVector((maxX + minY) / 2, (maxY + minY) / 2);
	}

	private static RealMatrix pVectorToMatrix(PVector vector) {
		RealMatrix matrix = new Array2DRowRealMatrix(2, 1);
		matrix.setEntry(0, 0, vector.x);
		matrix.setEntry(1, 0, vector.y);
		return matrix;
	}

	private static PVector matrixToPVector(RealMatrix matrix) {
		PVector vertice = new PVector();
		vertice.x = (float) matrix.getEntry(0, 0);
		vertice.y = (float) matrix.getEntry(1, 0);
		return vertice;
	}

}
