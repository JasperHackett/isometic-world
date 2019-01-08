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
public class City extends Structure {

	/**
	 * @param worldDimsIn
	 * @param worldPointIn
	 * @param tileList
	 */
	public City(ArrayList<Point> tileList, Point masterTile) {
		super(tileList, masterTile, StructureType.city);

	}

}
