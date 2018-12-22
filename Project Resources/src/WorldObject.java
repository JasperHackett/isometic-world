import java.awt.Dimension;
import java.awt.Point;

/**
 *
 */

/**
 * @author Jasper
 *
 */
public class WorldObject extends GameObject {
	
	Dimension worldDims;
	Point worldPoint;

	public WorldObject(ObjectType type, Dimension worldDimsIn, Point worldPointIn) {
		super(ObjectType.WORLD);
		this.worldDims = worldDimsIn;
		this.worldPoint = worldPointIn;
	}
	public void setPosition(Point worldPointIn, Point displayPanelPoint) {
		this.coords.setLocation((displayPanelPoint.getX() + (this.coords.getX() - worldPointIn.getX())), ((displayPanelPoint.getY() + (this.coords.getY() - worldPointIn.getY()))));
//		this.coords.x =;
//		this.coords.y = ;
	}

	/**
	 * @param world
	 */
//	public WorldObject(ObjectType world) {
//		this.type = WORLD
//		// TODO Auto-generated constructor stub
//	}
}
