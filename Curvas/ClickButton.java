import processing.core.PVector;
import remixlab.proscene.Camera;
import remixlab.proscene.Scene;

public class ClickButton extends Button2D {
  boolean addBox;
private Bezier bezier;

  public ClickButton(Scene scn, PVector p, String t, int fontSize, boolean addB,Bezier bezier) {
    super(scn, p, t, fontSize);
    addBox = addB;
    this.bezier = bezier; 
  }

  public void mouseClicked(Scene.Button button, int numberOfClicks, Camera camera) {
    if(numberOfClicks == 1) {
      if(addBox)
        bezier.addControlPoint();
      else
        bezier.removeFirstControlPoint();
    }
  }
}