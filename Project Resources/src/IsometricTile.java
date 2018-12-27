import java.awt.Dimension;
import java.awt.Point;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class IsometricTile extends WorldObject{
	public Point tilePos;

	public boolean walkable;
	public enum TILESET{
		grass,
		water,
		trees,
		
	}
	public TILESET tileset;
	/**
	 * @param type
	 * @param worldDimsIn
	 * @param worldPointIn
	 */
	public IsometricTile(ObjectType type, Dimension worldDimsIn, Point worldPointIn, TILESET tileset, Point tilePos) {
		super(type, worldDimsIn, worldPointIn);
		this.tileset = tileset;
		this.tilePos = tilePos;
		this.clickTag = "tile";
	}
}
