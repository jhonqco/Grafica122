import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.PGraphics;


public class Pintor {
	private ArrayList<Box> planos;

	public void setPlanos(ArrayList<Box> planos) {
		this.planos = planos;
	}
	
	public void dibujarPlanos(PGraphics canvas){
		Collections.sort(planos, new ChairWeightComparator());
		for(Box caja:planos){
			caja.draw2D(canvas);
		}
	}
	
}

class ChairWeightComparator implements Comparator<Box> {

	@Override
	public int compare(Box arg0, Box arg1) {
		
		return (int) (arg0.depth-arg1.depth);
	}
}