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
public class WorldObject extends GameObject {
	
	protected Dimension worldDims;
	protected Point worldPoint;
	
	protected Point isoPoint;

	//Unsafe constructor
	public WorldObject() {
		super(ObjectType.WORLD);
	}
	
	public WorldObject(ObjectType type, Dimension worldDimsIn, Point worldPointIn) {
		super(ObjectType.WORLD);
		this.worldDims = worldDimsIn;
		this.worldPoint = worldPointIn;
		this.clickable = true;
	}
	public void setPosition(Point worldPointIn, Point displayPanelPoint) {
		this.coords.setLocation((displayPanelPoint.getX() + (this.worldPoint.getX() - worldPointIn.getX())), ((displayPanelPoint.getY() + (this.worldPoint.getY() - worldPointIn.getY()))));
		
	}
	
	public Pair<Dimension, Point> getWorldPosition() {
		return new Pair<Dimension, Point>(this.dim, worldPoint);
	}
	
	public void tickAction() {
		
	}

	/**
	 * @param world
	 */
//	public WorldObject(ObjectType world) {
//		this.type = WORLD
//	}
}
