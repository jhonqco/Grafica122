import processing.core.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Principal extends PApplet {

	PGraphics canvasIzquierdo, canvasDerecho;
	boolean dibujar = false;
	boolean agregar = false;
	boolean estatico = true;
	Poligono general = new Poligono();
	int m, n;
	float rot = 0;
	private Poligono ventana, tv;
	private MatrixTransform2D matrixOfTransforms;

	/* VARIABLES PARA HACER EL BRAZO */

	boolean moverBrazo = false;
	boolean f = false;
	boolean s = false;
	boolean t = false;
	int numSeg = 3;
	int segLenght = 80;
	float angle[] =
		{ 0, 1, 1 };
	float x[] =
		{ 0, segLenght, segLenght * 2 };
	float y[] =
		{ 0, 0, 0 };
	int ox = 250;
	int oy = 250;

	// SETUP
	public void setup() {
		size(1000, 500);
		background(0);

		canvasIzquierdo = createGraphics(width / 2, height);
		canvasDerecho = createGraphics(width / 2, height);

		ventana = new Poligono();
		ventana.getVertices().add(new PVector(0, 0));
		ventana.getVertices().add(new PVector(0, canvasDerecho.height));
		ventana.getVertices().add(new PVector(canvasDerecho.width, canvasDerecho.height));
		ventana.getVertices().add(new PVector(canvasDerecho.width, 0));
		ventana.getVertices().trimToSize();

		matrixOfTransforms = new MatrixTransform2D();
		PVector centroVentana = ventana.getCenter();
		matrixOfTransforms.translate((int) -centroVentana.x, (int) -centroVentana.y);

		frameRate(30);

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				mouseWheel(mwe.getWheelRotation());
			}
		});
	}

	// DRAW
	public void draw() {

		// dibujo del canvas de la izquierda (mundo)
		canvasIzquierdo.beginDraw();
		canvasIzquierdo.background(250);
		canvasIzquierdo.stroke(0);
		dibMundo(canvasIzquierdo);
		dibFigura(canvasIzquierdo);
		canvasIzquierdo.endDraw();
		image(canvasIzquierdo, 0, 0, width / 2, height);

		// dibujo del canvas de la derecha (proyeccion del cuadro)
		canvasDerecho.beginDraw();
		canvasDerecho.background(250);
		canvasDerecho.stroke(0);
		dibujaderecha(canvasDerecho);
		canvasDerecho.endDraw();
		image(canvasDerecho, 500, 0, width / 2, height);

	}

	// metodo que permite obtener la cantidad de movimientos del scroll
	// para cambiar el factor de conversion de las figuras
	public void mouseWheel(int delta) {
		float fact = (float) (1 + (delta * 0.1));
		matrixOfTransforms.scale(fact, tv.getCenter());
	}

	public void mouseMoved() {
		if (estatico) {
			matrixOfTransforms.translate(mouseX - pmouseX, mouseY - pmouseY);
		}
	}

	/* cAMBIAR EL METODO DE LAS TECLAS PARA LA INTERACCION CON EL BRAZO */

	// metodo que determina los cursos de accion segun la tecla presionada
	public void keyPressed() {
		if (key == 'd' || key == 'D') {
			dibujar = true;
			agregar = !agregar;
			estatico = !estatico;
		} else if (key == 'e' || key == 'E') {
			dibujar = false;
			general = new Poligono();
		} else if (key == 'r' || key == 'R') {
			rot = rot + (float) (3.14 / 8);
			matrixOfTransforms.rotate(rot, tv.getCenter());
		} else if (key == 'f' || key == 'F') {
			f = !f;
			s = false;
			t = false;
		} else if (key == 's' || key == 'S') {
			s = !s;
			f = false;
			t = false;
		} else if (key == 't' || key == 'T') {
			t = !t;
			s = false;
			f = false;
		} else if (key == 'm' || key == 'M') {
			moverBrazo = !moverBrazo;
		}

	}

	private void dibujaderecha(PGraphics canvas) {
		canvas.strokeWeight(3);
		canvas.line(0, 0, 0, height);
		canvas.strokeWeight(2);
		canvas.rect(0, 0, canvas.width, canvas.height);
		// CorteLineas cortador = new CorteLineas();

		Poligono x = matrixOfTransforms.applyInverseOn(general);
		canvas.fill(0, 0);
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
		moveArm();
		snake(canvas);

		canvas.fill(0, 0, 0, 0);
		canvas.stroke(0);
		canvas.strokeWeight(2);
		
		tv = matrixOfTransforms.applyOn(ventana);
		tv.dibujar(canvas);
	}

	/* METODO SNAKE REFORMADO */
	public void snake(PGraphics canvas) {

		movimiento();
		inicioSeg(0, 1);
		inicioSeg(1, 2);

		// DIBUJO DEL BRAZO
		canvas.strokeWeight(10);
		// primersegmento
		canvas.stroke(250, 0, 0);
		segment(x[0], y[0], angle[0], canvas);
		// segundo segmento
		canvas.stroke(0, 250, 0);
		segment(x[1], y[1], angle[1], canvas);

		// tercer segmento
		canvas.stroke(0, 0, 250);
		segment(x[2], y[2], angle[2], canvas);
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

	/* METODOS A AGREGAR */

	// METODO para mover el brazo desde su posicion inicial
	private void moveArm() {
		if (moverBrazo) {
			x[0] = mouseX;
			y[0] = mouseY;
		}

	}

	// METODO PARA ASOCIAR EL MOVIMIENTO DE CADA SEGMENTO
	void movimiento() {
		if (f) {
			reachSegment(0);
		}
		if (s) {
			reachSegment(1);
		}
		if (t) {
			reachSegment(2);
		}
	}

	// metodo para calcular el angulo del segmento dado
	void reachSegment(int i) {
		float dx = mouseX - x[i];
		float dy = mouseY - y[i];
		angle[i] = atan2(dy, dx);
	}

	// METODO PARA CALCULAR EL PUNTO INICIAL DE CADA SEGMENTO
	public void inicioSeg(int a, int b) {
		x[b] = x[a] + (cos(angle[a]) * segLenght);
		y[b] = y[a] + (sin(angle[a]) * segLenght);
	}

	/*
	 * metodo encargado de dibujar un segmento de recta, recibiendo la posicion
	 * inicial el angulo, y el canvas para dibujarlo
	 */
	public void segment(float x, float y, float a, PGraphics canvas) {
		canvas.pushMatrix();
		canvas.translate(x, y);
		canvas.rotate(a);
		canvas.line(0, 0, segLenght, 0);
		canvas.popMatrix();
	}

}