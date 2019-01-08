import java.awt.Dimension;
import java.awt.Point;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class Unit extends Structure{

	Unit(Point isoPos){
		this.structureOffset = 16;
		this.isoPoint = isoPos;
//		Game.objectMap.getTile(isoPos);
		this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
		this.worldDims = new Dimension(64,32);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
	}
}
