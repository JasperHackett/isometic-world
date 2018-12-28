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
	public Structure(ArrayList<Point> tileList, Point masterTile) {
		super();
		this.tileList = tileList;
	}
}
