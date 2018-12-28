import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Orly
 *
 */
public class Structure extends WorldObject {

	public ArrayList<Point> tileList;
	
	/**
	 * @param type
	 * @param worldDimsIn
	 * @param worldPointIn
	 */
	public Structure(Dimension worldDimsIn, Point worldPointIn, ArrayList<Point> tileList) {
		super(ObjectType.WORLD);
		this.tileList = tileList;
	}
}
