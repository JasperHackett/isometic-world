import java.awt.Dimension;
import java.awt.Point;

import javafx.util.Pair;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class World {
	
	public Dimension panelDims;
	public Point panelPoint;
	
	public Dimension worldDims;
	public Point worldPoint;
	
	World() {
		panelDims = new Dimension(Game.width-200,Game.height-200);
		panelPoint = new Point(100,100);
		worldPoint = new Point(100,100);
		worldDims = new Dimension(1400,700);
	}
	
	public void updateDisplay() {
		Game.objectMap.updateMainDisplayObjects(worldDims, worldPoint);
//		offsetDisplay
//		Game.worldObjects.getMainDisplayObjects();
		
	}
	public Pair<Dimension,Point> getMainDisplayCoords() {
		return new Pair<Dimension,Point>(this.panelDims,this.panelPoint);
	}
	public void offsetDisplay(Point mousePressPos, Point mousePos) {
		if(this.worldPoint == null) {
			this.worldPoint = new Point(0,0);
		}
		this.worldPoint.x = this.worldPoint.x + ( mousePressPos.x - mousePos.x);
//		System.out.println(mousePos.x);
//		System.out.println(mousePressPos.x);
		this.worldPoint.y = this.worldPoint.y + ( mousePressPos.y - mousePos.y);
		System.out.println(this.worldPoint.x);
		System.out.println(this.worldPoint.x + ( mousePressPos.x - mousePos.x));
		
	}

}
