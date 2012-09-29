import java.awt.event.*;

float fact =1;
boolean moved =false;
PGraphics canvasUp,canvasDown;

 
void mouseWheel(int delta) {
  fact=fact+(delta*0.1);
  moved=true;
}


void setup() {
  size(500,500);
  background(0);
  
  addMouseWheelListener(new MouseWheelListener() 
  { 
    public void mouseWheelMoved(MouseWheelEvent mwe) 
    { 
      mouseWheel(mwe.getWheelRotation());
    }
  }); 
  canvasUp=createGraphics(500,250);
  canvasDown=createGraphics(500,250);
}

void draw(){
  canvasUp.beginDraw();
  canvasUp.background(250);
  canvasUp.stroke(0);
  if(moved){
    dibujaFigUp(fact);
  }
  canvasUp.endDraw();
  image(canvasUp,0,0, width, height/2);
  
  canvasDown.beginDraw();
  canvasDown.background(0);
  canvasUp.stroke(250);
  if(moved){
    dibujaFigDown(fact);
  }
  canvasDown.endDraw();
  image(canvasDown,0,250, width, height/2);

}


void dibujaFigUp(float n){
  
  canvasUp.scale(n);
  
  canvasUp.beginShape();
    canvasUp.vertex(0,0);
    canvasUp.vertex(0,80);
    canvasUp.vertex(70,80);
    canvasUp.vertex(70,100);
    canvasUp.vertex(40,100);
    canvasUp.vertex(100,140);
    canvasUp.vertex(160,100);
    canvasUp.vertex(130,100);
    canvasUp.vertex(130,80);
    canvasUp.vertex(200,80);
    canvasUp.vertex(200,0);
    canvasUp.vertex(0,0);
  canvasUp.endShape();
  
}

void dibujaFigDown(float n){
  
  canvasDown.scale(n);
  
  canvasDown.beginShape();
    canvasDown.vertex(0,0);
    canvasDown.vertex(0,80);
    canvasDown.vertex(70,80);
    canvasDown.vertex(70,100);
    canvasDown.vertex(40,100);
    canvasDown.vertex(100,140);
    canvasDown.vertex(160,100);
    canvasDown.vertex(130,100);
    canvasDown.vertex(130,80);
    canvasDown.vertex(200,80);
    canvasDown.vertex(200,0);
    canvasDown.vertex(0,0);
  canvasDown.endShape();
  
}
