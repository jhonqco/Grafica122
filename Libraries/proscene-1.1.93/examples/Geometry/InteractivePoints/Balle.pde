public class Balle {
  InteractiveFrame iFrame;
  float r;
  int c;

  Balle(PVector vecteurpos, color cc) {
    iFrame = new InteractiveFrame(scene);
    r = 3;
    c =cc;

    iFrame.setPosition(vecteurpos);
  }
    
  public void draw() {
    draw(true);
  }

  public void draw(boolean drawAxis) {
    pushMatrix();
    pushStyle();
    iFrame.applyTransformation();
    if (drawAxis) scene.drawAxis(r*1.3f);
    noStroke();
    if (iFrame.grabsMouse())
      fill(255, 0, 0);
    else
      fill(c);

    sphere(r);
    popStyle();
    popMatrix();
  }
  
  public int getColor() {
    return c;
  }
  
  public void setColor(int myC) {
    c = myC;
  }

  public PVector getPosition() {
    return iFrame.position();
  }

  public void setPosition(PVector pos) {
    iFrame.setPosition(pos);
  }

  public Quaternion getOrientation() {
    return iFrame.orientation();
  }

  public void setOrientation(PVector v) {
    PVector to = PVector.sub(v, iFrame.position());
    iFrame.setOrientation(new Quaternion(new PVector(0, 1, 0), to));
  }

  /*****************************************************
   plan_rec
   dessine un rectangle dont un des cotés a pour support la
   droite (pa, pb) . Le coté parallèle passe par la balle instanciée
   ******************************************************/
  public void plan_rec(PVector pa, PVector pb, color col) {
    PVector dir1=PVector.sub(pb, pa);
    dir1.normalize();
    PVector dir2=PVector.sub(this.getPosition(), pa);
    // dir2.normalize();
    PVector diro=dir1.cross(dir1.cross(dir2));
    diro.normalize();
    float c=30;
    float cc=diro.dot(dir2);
    fill(col);
    stroke(200);
    beginShape();
    vertex(pa.x-dir1.x*c, pa.y-dir1.y*c, pa.z-dir1.z*c);
    vertex(pb.x+dir1.x*c, pb.y+dir1.y*c, pb.z+dir1.z*c);
    vertex(pb.x+dir1.x*c+diro.x*cc, 
    pb.y+dir1.y*c+diro.y*cc, 
    pb.z+dir1.z*c+diro.z*cc);
    vertex(pa.x-dir1.x*c+diro.x*cc, 
    pa.y-dir1.y*c+diro.y*cc, 
    pa.z-dir1.z*c+diro.z*cc);
    endShape(CLOSE);
  }
}
