import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import processing.core.PVector;

public class MatrixTransform2D {
	private RealMatrix matrixTransform;

	public MatrixTransform2D() {
		double[][] identity =
			{
				{ 1, 0, 0 },
				{ 0, 1, 0 },
				{ 0, 0, 1 } };
		matrixTransform = new Array2DRowRealMatrix(identity);
	}

	public void translate(int dx, int dy) {

		double[][] t =
			{
				{ 1, 0, dx },
				{ 0, 1, dy },
				{ 0, 0, 1 } };

		RealMatrix T = new Array2DRowRealMatrix(t);

		matrixTransform = matrixTransform.preMultiply(T);
	}

	public void scale(float s, PVector center) {
		s = Math.abs(s);
		// Transladar al origen
		translate((int) -center.x, (int) -center.y);
		// Escalar
		double[][] t =
			{
				{ s, 0, 0 },
				{ 0, s, 0 },
				{ 0, 0, 1 } };
		RealMatrix S = new Array2DRowRealMatrix(t);
		matrixTransform = matrixTransform.preMultiply(S);
		// Transladar al la ubicacion original
		translate((int) center.x, (int) center.y);

	}

	public void rotate(float angleInRadians, PVector pivot) {
		System.out.println(pivot);
		// Transladar al origen
		translate((int) -pivot.x, (int) -pivot.y);
		// Rotar el poligono
		double[][] t =
			{
				{ Math.cos(angleInRadians), -Math.sin(angleInRadians), 0 },
				{ Math.sin(angleInRadians), Math.cos(angleInRadians), 0 },
				{ 0, 0, 1 } };
		RealMatrix R = new Array2DRowRealMatrix(t);
		matrixTransform = matrixTransform.preMultiply(R);
		// Transladar al la ubicacion original
		translate((int) pivot.x, (int) pivot.y);
	}

	public Poligono applyOn(Poligono polygon) {
		Poligono transformedPolygon = new Poligono(polygon.getVertices().size());
		for (PVector vertice : polygon.getVertices()) {
			// Pasar de PVector a RealMatrix
			double[] puntoData =
				{ vertice.x, vertice.y, 1 };
			RealMatrix punto = new Array2DRowRealMatrix(puntoData);
			// Aplicar trasformaciones
			punto = punto.preMultiply(matrixTransform);
			// Pasar de RealMatrix a PVector
			float x = (float) punto.getEntry(0, 0);
			float y = (float) punto.getEntry(1, 0);
			PVector nuevoVertice = new PVector(x, y);
			// Guardar nuevo vertice
			transformedPolygon.getVertices().add(nuevoVertice);
		}

		return transformedPolygon;
	}

}
