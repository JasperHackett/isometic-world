import java.awt.Dimension;
import java.awt.Point;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class MainDisplay {
	
	Dimension panelDims;
	Point panelPoint;
	
	Dimension worldDims;
	Point worldPoint;
	
	void MainDisplay() {
		panelDims = new Dimension(Game.width-200,Game.height-200);
		panelPoint = new Point(100,100);
	}
	
	public void updateDisplay() {
		Game.worldObjects.updateMainDisplayObjects();
	}

}
