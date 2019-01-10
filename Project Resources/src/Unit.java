import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class Unit extends Structure{

	LinkedList<Point> currentPath;
	
	Unit(Point isoPos){
//		currentPath = new LinkedList<Point>(Game.gameWorld.getPathBetween(new Point(4,6), new Point(41,55)));
		this.structureOffset = 16;
		this.isoPoint = isoPos;
//		Game.objectMap.getTile(isoPos);
		this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
		this.worldDims = new Dimension(64,32);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
	}
	
	public void setDestination(Point destinationPos) {
		currentPath = new LinkedList<Point>(Game.gameWorld.getPathBetween(this.isoPoint, destinationPos));
	}
	
	@Override
	public void tickAction() {
		if(currentPath == null) {
			System.out.println("No path set");
			return;
		}
		if(!currentPath.isEmpty()) {
			this.isoPoint = currentPath.removeLast();
			this.worldPoint = Game.objectMap.getTile(isoPoint).worldPoint;
		}
	}
}
