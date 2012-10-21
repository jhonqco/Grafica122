import processing.core.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Principal extends PApplet {
	
	private MatrixTransform2D matrixOfTransforms;
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
		}
	}

	// SETUP
	public void setup() {
		size(1000, 500);
		background(0);

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
		// TODO Auto-generated method stub
		canvas.strokeWeight(3);
		canvas.line(0, 0, 0, height);
		canvas.strokeWeight(2);
		//CorteLineas cortador = new CorteLineas();
//		Poligono x = MatrixTransform2D.rotate(general, rot);
//		x = MatrixTransform2D.scale(x, 1 / fact);
//		x = cortador.recorte(x, ventana.getXmin(), ventana.getYmin(),
//				ventana.getXmax(), ventana.getYmax());
//		x = MatrixTransform2D.centerOn(x, canvas.width / 2, canvas.height / 2);
//		x.dibujar(canvas);
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
		dibujaFig(canvas, fact, m, n);
	}

	// metodo que dibuja la ventana que hará las transformaciones
	public void dibujaFig(PGraphics canvas, float n, int a, int b) {
		Poligono flecha = new Poligono();

		flecha.getVertices().clear();
		flecha.getVertices().add(new PVector(0, 0));
		flecha.getVertices().add(new PVector(0, 80));
		flecha.getVertices().add(new PVector(70, 80));
		flecha.getVertices().add(new PVector(70, 100));
		flecha.getVertices().add(new PVector(40, 100));
		flecha.getVertices().add(new PVector(100, 140));
		flecha.getVertices().add(new PVector(160, 100));
		flecha.getVertices().add(new PVector(130, 100));
		flecha.getVertices().add(new PVector(130, 80));
		flecha.getVertices().add(new PVector(200, 80));
		flecha.getVertices().add(new PVector(200, 0));
		flecha.getVertices().add(new PVector(0, 0));

		ventana.getVertices().clear();
		ventana.getVertices().add(new PVector(0, 0));
		ventana.getVertices().add(new PVector(0, 80));
		ventana.getVertices().add(new PVector(200, 80));
		ventana.getVertices().add(new PVector(200, 0));

		canvas.fill(0, 0, 0, 0);
		canvas.stroke(0);
		canvas.strokeWeight(2);

		MatrixTransform2D flechaMatrix = new MatrixTransform2D();
		flechaMatrix.scale(n, flecha.getCenter());
		flechaMatrix.translate((int)(a-flecha.getCenter().x), (int)(b-flecha.getCenter().y));
		flechaMatrix.rotate(rot, new PVector(a, b));
		flecha = flechaMatrix.applyOn(flecha);
		flecha.dibujar(canvas);

		matrixOfTransforms.scale(n, ventana.getCenter());
		matrixOfTransforms.translate((int)(a-ventana.getCenter().x), (int)(b-ventana.getCenter().y));
		matrixOfTransforms.rotate(rot, ventana.getCenter());
		ventana = matrixOfTransforms.applyOn(ventana);
		ventana.dibujar(canvas);

	}

	public void snake(PGraphics canvas) {
		canvas.strokeWeight(4);
		// primer segmento
		canvas.stroke(250, 0, 0);

		// segundo segmento
		canvas.stroke(0, 250, 0);

		// tercer segmento
		canvas.stroke(0, 0, 250);

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

}