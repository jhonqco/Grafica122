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

class ChairWeightComparator implements Comparator<Triangle3D> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Triangle3D tA, Triangle3D tB) {
		
		if (traslape(tA, tB) == false) {
			System.out.println("1º no translape");
			return -1;
		}
		// 2º Prueba:
		else if(atrasDe(tA,tB) == true){
			System.out.println(tA.maxZ()+"=="+tB.maxZ());
			System.out.println("2º si esta atras");
			return -1;
		}
		// 3º Prueba
		else if(enfreteDe(tA,tB) == true){
			System.out.println("3º si esta al frente");
			return -1;
		}
		
		// Prueba con z promedio
		float zAProm = Math.abs((tA.maxZ() + tA.minZ()) / 2);
		float zBProm = Math.abs((tB.maxZ() + tA.minZ()) / 2);
		System.out.println(zAProm+" - "+zBProm);
		if(zAProm >= zBProm){
			return -1;
		}
		return 1;
	}

	private boolean enfreteDe(Triangle3D tA, Triangle3D tB) {
		Vector3D normal = Vector3Ds.getVector3D(tB.normal());
		for(PVector point: tA.getPoints()){
			double angle = Vector3D.angle(normal, Vector3Ds.getVector3D(PVector.sub(point, tB.getPoints()[0])));
			if(angle <= (Math.PI/2)){
				return false;
			}
		}
		return true;	}

	private boolean atrasDe(Triangle3D tA, Triangle3D tB) {
		Vector3D normal = Vector3Ds.getVector3D(tA.normal());
		for(PVector point: tB.getPoints()){
			double angle = Vector3D.angle(normal, Vector3Ds.getVector3D(PVector.sub(point, tA.getPoints()[0])));
			if(angle > (Math.PI/2)){
				return false;
			}
		}
		return true;
	}

	/** 1º Traslape, prueba del algoritmo del pintos 
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean traslape(Triangle3D a, Triangle3D b) {
		// Translape con respecto a X
		float intersectionMax = Math.min(a.maxX(), b.maxX());
		float intersectionMin = Math.max(a.minX(), b.minX());
		if (intersectionMax <= intersectionMin ) {
			return false;
		}
		// Translape con respecto a Y
		intersectionMax = Math.min(a.maxY(), b.maxY());
		intersectionMin = Math.max(a.minY(), b.minY());
		if (intersectionMax <= intersectionMin ) {
			return false;
		}
		return true;
	}
}