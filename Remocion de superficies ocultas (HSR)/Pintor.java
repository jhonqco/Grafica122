import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import processing.core.PGraphics;
import processing.core.PVector;
import utilidades.Vector3Ds;

public class Pintor {

	public void dibujarPlanos(PGraphics canvas, ArrayList<Triangle3D> planos) {
		try {
			Collections.sort(planos, new zComparator());
			Collections.sort(planos, new ChairWeightComparator());
		} catch (IllegalArgumentException e) {
			// for(Triangle3D t:planos){
			// for(PVector point: t.getPoints()){
			// System.out.print(point+" ");
			// }
			// System.out.println();
			// }
			System.out.println(e.getMessage());
		}
		for (Triangle3D caja : planos) {
			caja.drawOn(canvas);
		}
	}

}

class zComparator implements Comparator<Triangle3D> {

	@Override
	public int compare(Triangle3D trasero, Triangle3D delantero) {
		// Prueba con z minimo
		return (int) (trasero.minZ()-delantero.minZ());
	}

}

class ChairWeightComparator implements Comparator<Triangle3D> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Triangle3D trasero, Triangle3D delantero) {
		// Uso de las pruebas del algoritmo del pintor
		// 1º Prueba
		if (traslape(trasero, delantero) == false) {
			System.out.println("1º no translape");
			return 0;
		}
		// 2º Prueba:
		else if (detrasDe(trasero, delantero) == trasero.getPoints().length) {
			System.out.println("2º A esta atras de B");
			return -1;
		}
		// 3º Prueba
		else if (enfreteDe(trasero, delantero) == true) {
			System.out.println("3º si esta al frente");
			return -1;
		}

		return 1;
	}

	/**
	 * 1º Traslape, prueba del algoritmo del pintos
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean traslape(Triangle3D a, Triangle3D b) {
		// Translape con respecto a X
		float intersectionMax = Math.min(a.maxX(), b.maxX());
		float intersectionMin = Math.max(a.minX(), b.minX());
		if (intersectionMax <= intersectionMin) {
			return false;
		}
		// Translape con respecto a Y
		intersectionMax = Math.min(a.maxY(), b.maxY());
		intersectionMin = Math.max(a.minY(), b.minY());
		if (intersectionMax <= intersectionMin) {
			return false;
		}
		return true;
	}

	/**
	 * 2º Prueba Numero de puntos de tA que estan detras de tB
	 * 
	 * @param tA
	 * @param tB
	 * @return
	 */
	private int detrasDe(Triangle3D tA, Triangle3D tB) {
		boolean negarNormal = false;
		if (tB.normal().z > 0) {
			negarNormal = true;
		}
		int n = 0;
		for (double angle : this.anglesFromNormal(tB, tA, negarNormal)) {
			if (angle < (Math.PI / 2)) {
				n++;
			}
		}
		return n;
	}

	private boolean enfreteDe(Triangle3D tA, Triangle3D tB) {
		if (this.detrasDe(tA, tB) == 0) {
			return false;
		}
		boolean negarNormal = false;
		if (tA.normal().z < 0) {
			negarNormal = true;
		}
		for (double angle : this.anglesFromNormal(tA, tB, negarNormal)) {
			if (angle > (Math.PI / 2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Ángulos entre la normal de A y los puntos de B
	 * 
	 * @param tA
	 * @param tB
	 * @param negarNormal
	 * @return
	 */
	private Double[] anglesFromNormal(Triangle3D tA, Triangle3D tB, boolean negarNormal) {
		ArrayList<Double> anglesList = new ArrayList<Double>(tA.getPoints().length);
		Vector3D normal = Vector3Ds.getVector3D(tA.normal());
		if (negarNormal) {
			normal = normal.negate();
		}
		for (PVector point : tB.getPoints()) {
			Vector3D vector = Vector3Ds.getVector3D(PVector.sub(point, tA.getPoints()[0]));
			if (vector.getNorm() != 0) {
				double angle = Vector3D.angle(normal, vector);
				anglesList.add(angle);
			}
		}
		Double[] anglesArray = new Double[anglesList.size()];
		anglesList.toArray(anglesArray);
		return anglesArray;
	}
}