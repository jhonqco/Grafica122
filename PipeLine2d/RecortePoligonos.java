	import java.util.ArrayList;
	import processing.core.PApplet;
	import processing.core.PVector;
	
public class RecortePoligonos extends PApplet {
	
	Poligono p;
	ArrayList<PVector> ventana = new ArrayList<PVector>();
	int delta = 20;
	
	public void setup() {
		size(500, 500);
		background(0);
		ventana.add(new PVector(delta , delta));
		ventana.add(new PVector(delta , height-delta));
		ventana.add(new PVector(width-delta , height-delta));
		ventana.add(new PVector(width-delta , delta));
		recorte(p);
	}
	
	
	public static  Poligono recorte(Poligono g){
		Poligono n = new Poligono();
		return n;
	}

}
