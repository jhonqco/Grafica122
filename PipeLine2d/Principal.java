import processing.core.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Principal extends PApplet {

	float fact = 1;
	boolean moved = true;
	PGraphics canvasRight, canvasLeft;
	boolean dibujar = false;
	boolean agregar = false;
	boolean estatico = true;
	Poligono general = new Poligono();
	int m, n;
	float rot = 0;
	Poligono ventana = new Poligono();
	private MatrixTransform2D matrixOfTransforms;
	
	/*VARIABLES PARA HACER EL BRAZO*/

	boolean moverBrazo=false;
	boolean f=false;
	boolean s=false;
	boolean t=false;
	int numSeg=3;
	int segLenght=80;
	float angle[]={0,1,1};
	float x[]={0,segLenght,segLenght*2};
	float y[]={0,0,0};
	int ox=250;
	int oy=250;

	// metodo para guardar la posicion del mouse
	public void mList() {
		if (estatico) {
			m = mouseX;
			n = mouseY;
		}
	}

	// metodo que permite obtener la cantidad de movimientos del scroll
	// para cambiar el factor de conversion de las figuras
	public void mouseWheel(int delta) {
		fact = fact + (float) (delta * 0.1);
		moved = true;
	}

/*cAMBIAR EL METODO DE LAS TECLAS PARA LA INTERACCION CON EL BRAZO*/
	
	// metodo que determina los cursos de accion segun la tecla presionada
	public void keyPressed() {
		if (key == 'd' || key == 'D') {
			dibujar = true;
			agregar = !agregar;
			estatico = !estatico;
		} 
		else if (key == 'e' || key == 'E') {
			dibujar = false;
			general = new Poligono();
		}
		else if (key == 'r' || key == 'R') {
			rot = rot + (float) (3.14 / 8);
		}
		else if(key == 'f' || key == 'F'){
			f=!f;
			s=false;
			t=false;
		}
		else if(key == 's' || key == 'S'){
			s=!s;
			f=false;
			t=false;
		}
		else if(key == 't' || key == 'T'){
			t=!t;
			s=false;
			f=false;
		}
		else if(key == 'm' || key == 'M'){
			moverBrazo= !moverBrazo;
		}
		
	}

	// SETUP
	public void setup() {
		size(1000, 500);
		background(0);
		
		ventana.getVertices().add(new PVector(0, 0));
		ventana.getVertices().add(new PVector(0, 80));
		ventana.getVertices().add(new PVector(200, 80));
		ventana.getVertices().add(new PVector(200, 0));
		
		canvasRight = createGraphics(width / 2, height);
		canvasLeft = createGraphics(width / 2, height);
		matrixOfTransforms = new MatrixTransform2D();

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				mouseWheel(mwe.getWheelRotation());
			}
		});
	}

	// DRAW
	public void draw() {

		// dibujo del canvas de la izquierda (mundo)
		canvasRight.beginDraw();
		canvasRight.background(250);
		canvasRight.stroke(0);
		dibMundo(canvasRight);
		dibFigura(canvasRight);
		canvasRight.endDraw();
		image(canvasRight, 0, 0, width / 2, height);

		// dibujo del canvas de la derecha (proyeccion del cuadro)
		canvasLeft.beginDraw();
		canvasLeft.background(250);
		canvasLeft.stroke(0);
		dibujaderecha(canvasLeft);
		canvasLeft.endDraw();
		image(canvasLeft, 500, 0, width / 2, height);
	}

	private void dibujaderecha(PGraphics canvas) {
		canvas.strokeWeight(3);
		canvas.line(0, 0, 0, height);
		canvas.strokeWeight(2);
		//CorteLineas cortador = new CorteLineas();
		
		Poligono x = matrixOfTransforms.applyInverseOn(general);
		MatrixTransform2D xMOT = new MatrixTransform2D();
		xMOT.translate((int)(canvas.width/2-x.getCenter().x), (int)(canvas.height/2-x.getCenter().y));
		x = xMOT.applyOn(x);
		x.dibujar(canvas);
	}

	// metodo que dibuja el poligono del mundo
	private void dibFigura(PGraphics canvas) {

		if (agregar) {
			if (mousePressed) {
				general.getVertices().add(new PVector(mouseX, mouseY));
			}
		}
		if (dibujar) {
			canvas.stroke(0, 200, 0);
			general.dibujar(canvas);
		}

	}

	// metodo que dibuja el mundo
	public void dibMundo(PGraphics canvas) {
		drawAxis(canvas);
		mList();
		moveArm();
		snake(canvas);
		dibujaFig(canvas, fact, m, n);
	}

	// metodo que dibuja la ventana que hará las transformaciones
	public void dibujaFig(PGraphics canvas, float n, int a, int b) {

		canvas.fill(0, 0, 0, 0);
		canvas.stroke(0);
		canvas.strokeWeight(2);

		//matrixOfTransforms.scale(n, ventana.getCenter());
		matrixOfTransforms.translate((int)(-ventana.getCenter().x), (int)(-ventana.getCenter().y));
		//matrixOfTransforms.rotate(rot, ventana.getCenter());
		ventana = matrixOfTransforms.applyOn(ventana);
		System.out.println(ventana.getCenter());
		ventana.dibujar(canvas);

	}

	/*METODO SNAKE REFORMADO*/
	public void snake(PGraphics canvas) {
		
		
		movimiento();
		inicioSeg(0,1);
		inicioSeg(1,2);
		
		
		//DIBUJO DEL BRAZO
		canvas.strokeWeight(10);
		//primersegmento
		canvas.stroke(250, 0, 0);
		segment(x[0],y[0],angle[0],canvas);
		// segundo segmento
		canvas.stroke(0, 250, 0);
		segment(x[1],y[1],angle[1],canvas);

		// tercer segmento
		canvas.stroke(0, 0, 250);
		segment(x[2],y[2],angle[2],canvas);
	}

	// metodo que dibuja los ejes
	public void drawAxis(PGraphics canvas) {

		// cuadros cada 50
		canvas.strokeWeight(1);
		for (int i = 0; i <= 10; i++) {
			canvas.line(i * 50, 0, i * 50, 500);
			canvas.line(0, i * 50, 500, i * 50);
		}

		// X-Axis
		canvas.strokeWeight(2);
		canvas.stroke(255, 0, 0);
		canvas.line(250, 0, 250, 500);

		// Y-Axis
		canvas.stroke(0, 0, 255);
		canvas.line(0, 250, 500, 250);

	}
	
	/*METODOS A AGREGAR*/

	//METODO para mover el brazo desde su posicion inicial
	private void moveArm() {
		if(moverBrazo){
			x[0]=mouseX;
			y[0]=mouseY;
		}
		
	}
	

	//METODO PARA ASOCIAR EL MOVIMIENTO DE CADA SEGMENTO
	void movimiento(){
		if(f){
			reachSegment(0);
		}
		if(s){
			reachSegment(1);
		}
		if(t){
			reachSegment(2);
		}
	}

	//metodo para calcular el angulo del segmento dado
	void reachSegment(int i) {
		  float dx = mouseX - x[i];
		  float dy = mouseY - y[i];
		  angle[i] = atan2(dy, dx);
	 }

	//METODO PARA CALCULAR EL PUNTO INICIAL DE CADA SEGMENTO	
	public void inicioSeg(int a, int b){
		x[b]=x[a]+(cos(angle[a])*segLenght);
		y[b]=y[a]+(sin(angle[a])*segLenght);
	}
	

	/*metodo encargado de dibujar un segmento de recta, recibiendo la posicion inicial
	  el angulo, y el canvas para dibujarlo*/
	public void segment(float x, float y, float a,PGraphics canvas) {
	  canvas.pushMatrix();
	  canvas.translate(x, y);
	  canvas.rotate(a);
	  canvas.line(0, 0, segLenght, 0);
	  canvas.popMatrix();
	}


}