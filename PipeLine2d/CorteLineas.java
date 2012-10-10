import java.util.ArrayList;
import processing.core.PApplet;

public class CorteLineas extends PApplet{
	static int xIzq, yMin, xDer, yMay;
	int x0,y0,x1,y1;
	boolean borrar = false;

	ArrayList<punto> puntos = new ArrayList<punto>();

	class punto {
	int x,y;
	punto(int xi, int yi) {
		this.x=xi;
		this.y=yi;
		}
	}

	boolean press=false;
	int p=0,q=0;
	punto P0,temp;
	int p1,p2;

	public void setup() {
	size(300, 300);
	background(0,64,128);
	}

	public void draw() {
	if(q==1){
	  background(0,64,128);
	  fill(0,96,160);
	  rect(p1,p2,mouseX,mouseY);
	  int pa=1;
	  for (punto punto : puntos){  
	      if(pa > 0){
	        temp = punto;
	      }  
	      else{
	        line(temp.x,temp.y,punto.x,punto.y);
	      }
	      pa = -pa;
	  }  
	   rectMode(CORNERS);

	}

	if(q==2){
		background(0,64,128);
	        int pa=1;
	        for (punto punto : puntos){  
	            if(pa > 0){
	              temp = punto;
	            }  
	            else{
	              if(CohenSutherland(temp,punto))
	                  line(temp.x,temp.y,punto.x,punto.y);
	            }
	            pa = -pa;
	        }
	        q = 0;
	        puntos.clear();
	        borrar = true;
	 }
	}
	             

	public void mousePressed(){
	  press=true;
	  if (mouseButton == LEFT && p==0 && q == 0  && press==true) {
	          if(borrar){
	            background(0,64,128);
	            borrar = false;
	          }
	            
		  temp = new punto(mouseX,mouseY);
		  x0=temp.x; y0=temp.y;
	          puntos.add(temp);
		  p=1; press=false;
	  }
	  if (mouseButton == LEFT && p==1 && press==true) {
		  temp = new punto(mouseX,mouseY);
		  x1=temp.x; y1=temp.y;
	          puntos.add(temp);
		  p=0; press=false;
		  line(x0,y0,x1,y1);
	  }
	  if (mouseButton == RIGHT && q==0 && p==0 && press==true) {
	          p1=mouseX;
	          p2=mouseY; 
		  xIzq=mouseX;
		  yMin=mouseY;
		  press=false;
		  q=1;
	  }
	  if (mouseButton == RIGHT && q==1 && press==true) {
		  xDer=mouseX;
		  yMay=mouseY;
	          int tempi;
	          if(xIzq>xDer){
	            tempi = xIzq;
	            xIzq = xDer;
	            xDer = tempi;
	          }
	          if(yMin>yMay){
	            tempi = yMin;
	            yMin = yMay;
	            yMay = tempi;
	          }
		  press=false;
		  q=2;
	  }
	}

	private static int 	(punto P){
	int cod = 0;
	if(P.y < yMin) cod = cod + 8;
	else if(P.y > yMay) cod = cod + 4;
	if(P.x > xDer) cod = cod + 2;
	else if(P.x < xIzq) cod = cod + 1;
	return cod;
	}

	private static boolean rechazar(int cod1, int cod2){
	if ((cod1 & cod2) != 0 ) return true;
	return false; 
	} 


	private static boolean aceptar(int cod1, int cod2){
	if ( cod1==0 && cod2==0 ) return true;
	return false; 
	}


	static boolean CohenSutherland(punto P0, punto P1){
	int Code0,Code1; 
	while(true)	{
		Code0 = bits(P0);
		Code1 = bits(P1);
		if( rechazar(Code0,Code1) )return false;
		if( aceptar(Code0,Code1) ) return true;
		if(Code0 == 0){
			int tempCoord; int tempCode;
			tempCoord = P0.x; P0.x= P1.x; P1.x = tempCoord;
			tempCoord = P0.y; P0.y= P1.y; P1.y = tempCoord;
			tempCode = Code0; Code0 = Code1; Code1 = tempCode;
			} 
		if( (Code0 & 8) != 0 ){ 
			P0.x += (P1.x - P0.x)*(yMin - P0.y)/(P1.y - P0.y);
			P0.y = yMin;
			}
		else
			if( (Code0 & 4) != 0 ) { 
				P0.x += (P1.x - P0.x)*(yMay - P0.y)/(P1.y - P0.y);
				P0.y = yMay;
				}
			else
				if( (Code0 & 2) != 0 ) {
					P0.y += (P1.y - P0.y)*(xDer - P0.x)/(P1.x - P0.x);
					P0.x = xDer;
					}
				else
					if( (Code0 & 1) != 0 ) {
						P0.y += (P1.y - P0.y)*(xIzq - P0.x)/(P1.x - P0.x);
						P0.x = xIzq;
						}
		} 
	} 

}
