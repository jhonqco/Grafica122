import processing.core.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Principal extends PApplet {
	float fact =1;
	boolean moved =true;
	boolean swing =false;
	PGraphics canvasRight, canvasLeft;
	  int numSegments = 3; 
	  float[] x = new float[numSegments];
	  float[] y = new float[numSegments];
	  float[] angle = new float[numSegments];
	  float segLength = 50;
	  float targetX=0, targetY=0;

	public void mouseWheel(int delta) {
	  fact=fact+(float)(delta*0.1);
	  moved=true;
	}


	public void setup() {
	  size(1000, 500);
	  background(0);

	  canvasRight=createGraphics(width/2, height);
	  canvasLeft=createGraphics(width/2, height);

	  addMouseWheelListener(new MouseWheelListener() 
	  { 
	    public void mouseWheelMoved(MouseWheelEvent mwe) 
	    { 
	      mouseWheel(mwe.getWheelRotation());
	    }
	  }
	  );
	}


	public void draw() {
	  canvasRight.beginDraw();
	  canvasRight.background(250);
	  canvasRight.stroke(0);
	  dibMundo(canvasRight,mouseX,mouseY);
	  canvasRight.endDraw();
	  image(canvasRight, 0, 0, width/2, height);

	  canvasLeft.beginDraw();
	  canvasLeft.background(250);
	  canvasLeft.stroke(250);
	  canvasLeft.endDraw();
	  image(canvasLeft, 500, 0, width/2, height);
	}  



	public void dibMundo(PGraphics canvas, int a,int b) {
	  drawAxis(canvas);
	  dibujaFig(canvas,fact);
	  Snake(canvas);
	}






	public void dibujaFig(PGraphics canvas, float n) {

		Poligono ventana=new Poligono();
		ventana.getVertices().add(new PVector(0,0));
		ventana.getVertices().add(new PVector(0, 80));
		ventana.getVertices().add(new PVector(70, 80));
		ventana.getVertices().add(new PVector(70, 100));
		ventana.getVertices().add(new PVector(40, 100));
		ventana.getVertices().add(new PVector(100, 140));
		ventana.getVertices().add(new PVector(160, 100));
		ventana.getVertices().add(new PVector(130, 100));
		ventana.getVertices().add(new PVector(130, 80));
		ventana.getVertices().add(new PVector(200, 80));
		ventana.getVertices().add(new PVector(200, 0));
		ventana.getVertices().add(new PVector(0, 0));
	
		
		
		
		
	  canvas.fill(0,0,0,0);
	  canvas.stroke(0);
	  canvas.strokeWeight(2);
	  canvas.pushMatrix();
	  ventana=Transform2D.scale(ventana, n);
	  ventana=Transform2D.centerOn(ventana, mouseX, mouseY);
	 
	  ventana.dibujar(canvas);
	  canvas.popMatrix();
	}






	public void drawAxis(PGraphics canvas) {

	  //cuadros cada 50
	  canvas.strokeWeight(1);
	  for (int i=0;i<=10;i++) {
	    canvas.line(i*50, 0, i*50, 500);
	    canvas.line(0, i*50, 500, i*50);
	  }


	  // X-Axis
	  canvas.strokeWeight(2);
	  canvas.stroke(255, 0, 0);
	  canvas.line(250,0, 250, 500);
	  
	  // Y-Axis
	  canvas.stroke(0, 0, 255);
	  canvas.line(0, 250, 500, 250);
	 
	}

	public void Snake(PGraphics canvas){


	  canvas.stroke(0, 100);
	  x[x.length-1] = canvas.width/2;     // Set base x-coordinate
	  y[x.length-1] = canvas.height/2;  // Set base y-coordinate

	  reachSegment(0, mouseX, mouseY);
	  for(int i=1; i<numSegments; i++) {
	    reachSegment(i, targetX, targetY);
	  }
	  for(int i=x.length-1; i>=1; i--) {
	    positionSegment(i, i-1);  
	  } 
	  for(int i=0; i<x.length; i++) {
	    segment(x[i], y[i], angle[i], 5,canvas); 
	  }
	}

	public void positionSegment(int a, int b) {
	  x[b] = x[a] + cos(angle[a]) * segLength;
	  y[b] = y[a] + sin(angle[a]) * segLength; 
	}

	public void reachSegment(int i, float xin, float yin) {
	  float dx = xin - x[i];
	  float dy = yin - y[i];
	  angle[i] = atan2(dy, dx);  
	  targetX = xin - cos(angle[i]) * segLength;
	  targetY = yin - sin(angle[i]) * segLength;
	}

	public void segment(float x, float y, float a, float sw,PGraphics canvas) {
	  canvas.strokeWeight(sw);
	  canvas.pushMatrix();
	  canvas.translate(x, y);
	  canvas.rotate(a);
	  canvas.line(0, 0, segLength, 0);
	  canvas.popMatrix();
	}

}