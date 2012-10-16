import processing.core.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Principal extends PApplet {
	
	float fact =1;
	boolean moved =true;
	PGraphics canvasRight, canvasLeft;
	boolean dibujar =false;
	boolean agregar = false;
	boolean estatico =true;
	Poligono general=new Poligono();
	int m,n;
	float rot=0;
	
	
	
	//metodo para guardar la posicion del mouse
	public void mList(){
		if(estatico){
			m=mouseX;
			n=mouseY;
		}
	}
	
	
	
	//metodo que permite obtener la cantidad de movimientos del scroll
	//para cambiar el factor de conversion de las figuras
	public void mouseWheel(int delta) {
	  fact=fact+(float)(delta*0.1);
	  moved=true;
	}
	
	//metodo que determina los cursos de accion segun la tecla presionada
	public void keyPressed(){
		if(key == 'd' || key == 'D'){
			dibujar = true;
			agregar = !agregar;
			estatico = !estatico;
		}
		else if(key == 'e' || key == 'E'){
			dibujar = false;
			general=new Poligono();
		}
		else if(key == 'r' || key == 'R'){
			rot=rot+(float)(3.14/4);
		}
	}


	//SETUP
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


	//DRAW
	public void draw() {
		
	//dibujo del canvas de la izquierda (mundo)
	  canvasRight.beginDraw();
	  canvasRight.background(250);
	  canvasRight.stroke(0);
	  dibMundo(canvasRight);
	  dibFigura(canvasRight);
	  canvasRight.endDraw();
	  image(canvasRight, 0, 0, width/2, height);

	//dibujo del canvas de la derecha (proyeccion del cuadro)
	  canvasLeft.beginDraw();
	  canvasLeft.background(250);
	  canvasLeft.stroke(0);
	  dibujaderecha(canvasLeft);
	  canvasLeft.endDraw();
	  image(canvasLeft, 500, 0, width/2, height);
	}  

	private void dibujaderecha(PGraphics canvas) {
		// TODO Auto-generated method stub
		Poligono x =new Poligono();
		//x = RecortePoligonos.recorte(ventana);
		x.dibujar(canvas);
		
	}



	//metodo que dibuja el poligono del mundo
	private void dibFigura(PGraphics canvas) {
	
		if(agregar){
			if(mousePressed){
				general.getVertices().add(new PVector(mouseX,mouseY));
			}
		}
		if(dibujar){
			general.dibujar(canvas);
		}
		
	}


	//metodo que dibuja el mundo
	public void dibMundo(PGraphics canvas) {
	  drawAxis(canvas);
	  mList();
	  dibujaFig(canvas,fact,m,n);
	}

	//metodo que dibuja la ventana que hará las transformaciones
	public void dibujaFig(PGraphics canvas, float n, int a, int b) {

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
	
	  ventana=Transform2D.scale(ventana, n);
	  ventana=Transform2D.centerOn(ventana, a, b);
	  ventana =Transform2D.rotate(ventana, rot);
	  ventana.dibujar(canvas);
	  
	}






	//metodo que dibuja los ejes
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

	
}