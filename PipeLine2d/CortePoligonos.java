import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;


public class CortePoligonos extends PApplet{
	ArrayList<PVector> poligono = new ArrayList<PVector>();
	ArrayList<PVector> ventana = new ArrayList<PVector>();
	int delta = 20;
	public void setup() {
		size(500, 500);
		background(0);
		poligono.add(new PVector(width/2, 0));
		poligono.add(new PVector(0, height/2));
		poligono.add(new PVector(width/2, height ));
		poligono.add(new PVector(width, height/2));
		ventana.add(new PVector(delta , delta));
		ventana.add(new PVector(delta , height-delta));
		ventana.add(new PVector(width-delta , height-delta));
		ventana.add(new PVector(width-delta , delta));
		recorte();
	}
	public void draw() {
		
	}
	public void recorte(){
		if(poligono.get(0).x){
			
		}
	}

}
