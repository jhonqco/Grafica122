
import processing.core.*;
//import remixlab.proscene.*;
import remixlab.proscene.Scene;

public class HermiteSpline extends PApplet{


	int control=4;
	int x[]={0,50,100,150};
	int y[]={100,100,100,100};
	int z[]={0,0,0,0};
	boolean punto1=false;
	boolean punto2=false;
	boolean vector1=false;
	boolean vector2=false;
	private Scene scene;
	
	
	public void setup(){
		size(500,500,P3D);
		int nbKeyFrames = 4;
		scene = new Scene(this);
		scene.setAxisIsDrawn(false);
		scene.setGridIsDrawn(false);
		scene.setRadius(70);
		scene.showAll();
		scene.setFrameSelectionHintIsDrawn(true);
	}
	
	public void draw(){
		background(250);
		puntos();
		dibInd();
		curva();
	}
	
	
	/*metodo que dibuja en naranja los punto inicial y final
	  ademas de los vectores de direccion de la curva*/
	public void dibInd(){
		strokeWeight(5);
		stroke(250,120,0);
		for (int i=0;i<control;i++){
			point(x[i],y[i]);
		}
		
		strokeWeight(1);
		line(x[0],y[0],x[1],y[1]);
		line(x[3],y[3],x[2],y[2]);
	}

	//metodo que dibuja la curva en blanco, usando la interpolacion de hermite
	public void curva(){
		float dx=0;
		float dy=0;
		float dz=0;
		float a,b,c,d;
		strokeWeight(2);
		stroke(250,100,100);
		for(float u= (float) 0; u<=1; u=(float) (u+0.001)){
			a=((2*pow(u,3))-(3*pow(u,2))+1);
			b=((-2*pow(u,3))+(3*pow(u,2)));
			c=(pow(u,3)-(2*pow(u,2))+u);
			d=pow(u,3)-pow(u,2);
			dx=(x[0]*a)+(x[3]*b)+(x[1]*c)+(x[2]*d);
			dy=(y[0]*a)+(y[3]*b)+(y[1]*c)+(y[2]*d);
			dz=(z[0]*a)+(z[3]*b)+(z[1]*c)+(z[2]*d);
			point(dx,dy,dz);
		}
	}
	
	//metodo que asigna la posicion de los puntos de la curva
	public void puntos(){
		if(punto1){
			x[0]=mouseX;
			y[0]=mouseY;
		}
		if(punto2){
			x[3]=mouseX;
			y[3]=mouseY;
		}
		if(vector1){
			x[1]=mouseX;
			y[1]=mouseY;
		}
		if(vector2){
			x[2]=mouseX;
			y[2]=mouseY;
		}
	}

	//Metodo que permite manejar el movimiento de los puntos de la curva
	public void keyPressed() {
		  if (key == '1'){
			  punto1=!punto1;
			  punto2=false;
			  vector1=false;
			  vector2=false;
		  }
		  if ( key == '2')
		  {
			  punto1=false;
			  punto2=false;
			  vector1=!vector1;
			  vector2=false;
		  }
		  if ( key == '3')
		  {
			  punto1=false;
			  punto2=false;
			  vector1=false;
			  vector2=!vector2;
		  }
		  if ( key == '4')
		  {
			  punto1=false;
			  punto2=!punto2;
			  vector1=false;
			  vector2=false;
		  }
		}
}

