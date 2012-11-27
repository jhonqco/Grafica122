import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import processing.core.PGraphics;
import processing.core.PVector;
import utilidades.Vector3Ds;

/**
 * @author Sebastian
 * 
 */
public class Pintor {
	public static int NO_PRUEBAS = 0;
	public static int Z_COMPARATOR = 1;
	public static int ESPACIOS_PRUEBAS = 2;
	public static int TODAS_PRUEBAS = 3;

	private Pintor() {
	};

	public static void dibujarPlanos(PGraphics canvas, ArrayList<Triangle3D> planos, int pruebas) {
		try {
			if (pruebas == Z_COMPARATOR || pruebas == TODAS_PRUEBAS) {
				Collections.sort(planos, new zComparator());
			} else if (pruebas == ESPACIOS_PRUEBAS  || pruebas == TODAS_PRUEBAS) {
				Collections.sort(planos, new ChairWeightComparator());
			}
		} catch (IllegalArgumentException e) {
			// for(Triangle3D t:planos){
			// for(PVector point: t.getPoints()){
			// System.out.print(point+" ");
			// }
			// System.out.println();
			// }
			System.out.println(e.getMessage());
		}
		System.out.println("FIN ORDENAR");
		for (Triangle3D caja : planos) {
			caja.drawOn(canvas);
		}
	}

}

class zComparator implements Comparator<Triangle3D> {

	@Override
	public int compare(Triangle3D trasero, Triangle3D delantero) {
		// Ordenar del mayor al menor valor de z
		 System.out.println(trasero.name+" z max "+trasero.maxZ());
		 System.out.println(delantero.name+" z max "+delantero.maxZ());
		int result = (int) Math.signum(delantero.maxZ() - trasero.maxZ());
		if(result == 0){
			result = -1;
		}
		 System.out.println("result = "+result);
		return result;
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
			System.out.println(trasero.name + " 1º NO TRASLAPE " + delantero.name);
			return 0;
		}
		// 2º Prueba:
		else if (detrasDe(trasero, delantero) == trasero.getPoints().length) {
			System.out.println("2° " + trasero.name + " esta ATRAS de " + delantero.name);
			return -1;
		}
		// 3º Prueba
		else if (enfreteDe(trasero, delantero) == true) {
			System.out.println(trasero.name + " 3º esta al FRENTE de " + delantero.name);
			return -1;
		}
		System.out
				.println("Fallan pruebas => " + delantero.name + " esta atras de " + trasero.name);
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
		if (intersectionMin < intersectionMax) {
			return true;
		}
		// Translape con respecto a Y
		intersectionMax = Math.min(a.maxY(), b.maxY());
		intersectionMin = Math.max(a.minY(), b.minY());
		if (intersectionMin < intersectionMax) {
			return true;
		}
		return false;
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
		if (tB.getNormal().z < 0) {
			negarNormal = true;
		}
		int n = 0;
		Float[] angles = this.anglesFromNormal(tB, tA, negarNormal);
		for (float angle : angles) {
			System.out.print("angulo: " + (float) Math.toDegrees(angle) + " - ");
			if ((float) Math.toDegrees(angle) <= 90) {
				n++;
			}
		}
		System.out.println();
		if (n == angles.length) {
			n = tB.getPoints().length;
		}
		return n;
	}

	private boolean enfreteDe(Triangle3D tA, Triangle3D tB) {
		System.out.println(tA.name + " " + detrasDe(tA, tB) + " puntos atras de " + tB.name);
		if (this.detrasDe(tA, tB) == 0) {
			return false;
		}
		boolean negarNormal = false;
		if (tB.getNormal().z < 0) {
			negarNormal = true;
		}
		for (float angle : this.anglesFromNormal(tB, tA, negarNormal)) {
			System.out.print("angulo: " + (float) Math.toDegrees(angle) + " - ");
			if ((float)Math.toDegrees(angle) > 90) {
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
	private Float[] anglesFromNormal(Triangle3D tA, Triangle3D tB, boolean negarNormal) {
		Float[] anglesArray = new Float[tB.getPoints().length];
		Vector3D normal = Vector3Ds.getVector3D(tA.getNormal());
		if (negarNormal) {
			normal = normal.negate();
		}
		for (int i = 0; i < anglesArray.length; i++) {
			PVector point = tB.getPoints()[i];
			Vector3D vector = Vector3Ds.getVector3D(PVector.sub(point, tA.getPoints()[0]));
			float angle;
			if (vector.getNorm() == 0) {
				// 90° por que esta en el plano
				angle = (float) Math.toRadians(90);
			} else {
				angle = (float) Vector3D.angle(normal, vector);

			}
			anglesArray[i] = angle;
		}
		return anglesArray;
	}
}