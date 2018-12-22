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
	
	
	public Dimension panelDims;	// dimensions of the main display (the portion of the world currently being rendered)
	public Point panelPoint; // true location of the top left corner of the main display - (100, 100) relative to the ContentPane
	
	public Dimension worldDims;
	public Point worldPoint;
	public Point staticWorldPoint;
	

	World(Dimension worldDims)  {
		panelDims = new Dimension(Game.width-200,Game.height-200);
		panelPoint = new Point(100,100);
		worldPoint = new Point(50,50);
		this.worldDims = worldDims;
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

		this.worldPoint.y = staticWorldPoint.y + ((( mousePressPos.y - mousePos.y)) );
		this.worldPoint.x = staticWorldPoint.x + ((( mousePressPos.x - mousePos.x)) );
		if(!withinBounds(this.worldPoint)) {
			if(worldPoint.y < 0) {
				worldPoint.y = 0;
			}
			if(worldPoint.x < 0) {
				worldPoint.x = 0;
			}
			if(worldPoint.x + panelDims.width > worldDims.width) {
				worldPoint.x = worldDims.width-panelDims.width;
			}
			if(worldPoint.y + panelDims.height > worldDims.height) {
				worldPoint.y = worldDims.height-panelDims.height;
			}
					
				
		}

		updateDisplay();
		
	}
	
	public boolean withinBounds(Point worldPointIn) {
		if(worldPointIn.y > 0 && worldPointIn.x > 0 && worldPointIn.x+panelDims.width < worldDims.width && worldPointIn.y+panelDims.height < worldDims.height) {
			return true;
		}
		return false;
	}

}
