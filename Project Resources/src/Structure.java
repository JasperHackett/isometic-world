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

	public enum StructureType {
		city;
	}
	
	public ArrayList<Point> tileList;
	public Point masterTile;
	public StructureType type;
	
	/**
	 * @param type
	 * @param worldDimsIn
	 * @param worldPointIn
	 */
	public Structure(ArrayList<Point> tileList, Point masterTile, StructureType type) {
		super();
		this.tileList = tileList;
		this.masterTile = masterTile;
		this.type = type;
	}
}
