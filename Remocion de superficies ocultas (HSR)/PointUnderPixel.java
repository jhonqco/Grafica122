import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;
import remixlab.proscene.CameraProfile;
import remixlab.proscene.Scene;


@SuppressWarnings("serial")
public class PointUnderPixel extends PApplet{
	/**
	 * Point Under Pixel.
	 * by Jean Pierre Charalambos.
	 * 
	 * This example illustrates 3D world point picking.
	 * 
	 * The click bindings below define some per-pixel user interactions,
	 * such as "zoom on pixel" or "set the arcball reference point". 
	 * 
	 * Press 'h' to display the global shortcuts in the console.
	 * Press 'H' to display the current camera profile keyboard shortcuts
	 * and mouse bindings in the console.
	 */

	Scene scene;
	Box [] boxes;
	private PGraphics canvas;
	private PGraphics derecho;

	public void setup() {
	  size(640, 480, P3D);
	  hint(DISABLE_DEPTH_TEST);
	  canvas = createGraphics(width/2, height,P3D);
	  derecho = createGraphics(width/2, height);
	  scene = new Scene(this,(PGraphicsOpenGL) canvas);
	  scene.setShortcut('f', Scene.KeyboardAction.DRAW_FRAME_SELECTION_HINT);
	  scene.setShortcut('z', Scene.KeyboardAction.ARP_FROM_PIXEL);
	  //add the click actions to all camera profiles
	  CameraProfile [] camProfiles = scene.getCameraProfiles();
	  for (int i=0; i<camProfiles.length; i++) {
	    // left click will zoom on pixel:
	    camProfiles[i].setClickBinding( Scene.Button.LEFT, Scene.ClickAction.ZOOM_ON_PIXEL );
	    // middle click will show all the scene:
	    camProfiles[i].setClickBinding( Scene.Button.MIDDLE, Scene.ClickAction.SHOW_ALL);
	    // right click will will set the arcball reference point:
	    camProfiles[i].setClickBinding( Scene.Button.RIGHT, Scene.ClickAction.ARP_FROM_PIXEL );
	    // double click with the middle button while pressing SHIFT will reset the arcball reference point:
	    camProfiles[i].setClickBinding( Scene.Modifier.SHIFT.ID, Scene.Button.MIDDLE, 2, Scene.ClickAction.RESET_ARP );
	  }	  

	  scene.setGridIsDrawn(false);
	  scene.setAxisIsDrawn(false);
	  scene.setRadius(200);
	  scene.enableFrustumEquationsUpdate();
	  scene.addDrawHandler(this, "mainDrawing");
	  scene.showAll();
	  boxes = new Box[50];
	  // create an array of boxes with random positions, sizes and colors
	  for (int i = 0; i < boxes.length; i++)
	    boxes[i] = new Box(scene);
	  boxes[0].setPosition(new PVector(width/2, height/2, boxes[0].getPosition().z));
	}

	public void mainDrawing(Scene s){
		PGraphicsOpenGL p = s.renderer();
		p.background(0);
		  for (int i = 0; i < boxes.length; i++){    
		    boxes[i].draw();
		  }
	}
	
	public void draw() {
		
		if(mouseX > width/2){
			scene.disableMouseHandling();
		}else{
			scene.enableMouseHandling();
		}
		
	  canvas.beginDraw();
	  scene.beginDraw();
	  scene.endDraw();
	  canvas.endDraw();
	  image(canvas, 0, 0);
	  
	  System.out.print(boxes[0].getPosition()+"\t");
	  PVector window = scene.camera().projectedCoordinatesOf(boxes[0].getPosition());
	  System.out.println(window);
	  
	  derecho.beginDraw();
	  derecho.background(0);
	  derecho.stroke(255,0,0);
	  derecho.strokeWeight(5);
	  derecho.ellipse(window.x, window.y, 5, 5);
	  derecho.endDraw();
	  
	  image(derecho,width/2,0,width,height);
	}
}
