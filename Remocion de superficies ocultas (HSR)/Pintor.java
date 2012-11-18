import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.PGraphics;
import processing.core.PVector;

public class Pintor {

	public void dibujarPlanos(PGraphics canvas, ArrayList<Triangle3D> planos) {
		Collections.sort(planos, new ChairWeightComparator());
		for (Triangle3D caja : planos) {
			caja.drawOn(canvas);
		}
	}

}

class ChairWeightComparator implements Comparator<Triangle3D> {

	@Override
	public int compare(Triangle3D atras, Triangle3D adelante) {
		// 1º Prueba de Traslape
		if(Translape(atras, adelante) == false){
			return 0;
		}
		
		// Prueba con z promedio
		float zPromAtras = 0;
		for (PVector point : atras.getPoints()) {
			zPromAtras += point.z;
		}
		zPromAtras = zPromAtras / 3;
		float zPromAdelante = 0;
		for (PVector point : adelante.getPoints()) {
			zPromAdelante += point.z;
		}
		zPromAdelante = zPromAdelante / 3;
		return (int) (zPromAtras - zPromAdelante);
	}
	
	private boolean Translape(Triangle3D a, Triangle3D b){
		// Translape con respecto a X
		if(b.minX() <= a.maxX() && a.maxX() <= b.maxX()){
			return true;
		}else if(b.minX() <= a.minX() && a.minX() <= b.maxX()){
			return true;
		}
		// Translape con respecto a Y
		if(b.minY() <= a.minY() && a.minY() <= b.maxY()){
			return true;
		}else if(b.minY() <= a.minY() && a.minY() <= b.maxY()){
			return true;
		}
		return false;
	}
}