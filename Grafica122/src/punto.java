
public class punto {
	int x, y;
	punto(int a, int b){
		x = a;
		y = b;
	}
	int getx(){
		return x;
	}
	int gety(){
		return y;
	}
	void setx(int a){
		x = a;
	}
	void sety(int a){
		y = a;
	}
	int pend(punto a){
		return (gety()-a.gety())/(getx()-a.getx());
	}

}
