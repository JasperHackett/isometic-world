import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

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
	public Point staticWorldPoint;
	
	World() {
		panelDims = new Dimension(Game.width-200,Game.height-200);
		panelPoint = new Point(100,100);
		worldPoint = new Point(50,50);
		worldDims = new Dimension(1400,700);
	}
	
	/**
	 *  Repositions every object in the main display. Called after the display is offset
	 */
	public void updateDisplay() {
		Game.objectMap.updateMainDisplayObjects();

		for(Map.Entry<String,WorldObject> obj : Game.objectMap.getMainDisplayObjects().entrySet()) {
			obj.getValue().setPosition(worldPoint,panelPoint);
		}
		
	}
	public Pair<Dimension,Point> getMainDisplayCoords() {
		return new Pair<Dimension,Point>(this.panelDims,this.panelPoint);
	}
	
	
	/**
	 * This function is called by InputHandler when the mouse is dragged, it moves the display view of the world. 
	 * 
	 * @param mousePressPos : the position the mouse was pressed in, used as the world anchor for offsetting display
	 * @param mousePos : the position of the mouse when function is called
	 */
	public void offsetDisplay(Point mousePressPos, Point mousePos) {
		if(this.staticWorldPoint == null) {
			this.staticWorldPoint = this.worldPoint.getLocation();
		}

		System.out.println(("STATIC WORLD POINT:" +  staticWorldPoint.y));
		this.worldPoint.y = staticWorldPoint.y + ((( mousePressPos.y - mousePos.y)) );
		this.worldPoint.x = staticWorldPoint.x + ((( mousePressPos.x - mousePos.x)) );

		updateDisplay();
		
	}

}
