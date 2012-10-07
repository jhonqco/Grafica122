import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

@SuppressWarnings("serial")
public class Principal extends PApplet {
	private ArrayList<PVector> prueba= new ArrayList<PVector>();
	
	public void setup() {
		size(500, 500);
		background(0);
		stroke(255, 0, 0);
		line(0, 0, width, height);
		line(width, 0, 0, height);
		line(0, height / 2, width, height / 2);
		line(width / 2, 0, width / 2, height);
		stroke(0);
		
		// inicializar poligono de prueba
		int e=5;
		prueba.add(new PVector(10*e, 10*e));
		prueba.add(new PVector(20*e, 10*e));
		prueba.add(new PVector(20*e, 20*e));
		prueba.add(new PVector(15*e, 20*e));
		prueba.add(new PVector(15*e, 30*e));
		prueba.add(new PVector(10*e, 30*e));
		prueba.add(new PVector(10*e, 10*e));
		prueba.trimToSize();
		noFill();
		stroke(255);
		drawPolygon(prueba);
		
	}

	public void draw() {

	}
	public void mouseClicked(){
		stroke(0,255,0);
		drawPolygon(Transform2D.translate(prueba, -100, -150));
		stroke(0,0,255);
		drawPolygon(Transform2D.scale(prueba, (float) 2.5));
		stroke(255,255,0);
		drawPolygon(Transform2D.rotate(prueba, PI/2));
	}
	
	public void drawPolygon(ArrayList<PVector> polygon){
		pushMatrix();
		translate(width/2, height/2);
		beginShape();
		for(PVector point: polygon){
			vertex(point.x, point.y);
		}
		endShape();
		popMatrix();
	}
}
