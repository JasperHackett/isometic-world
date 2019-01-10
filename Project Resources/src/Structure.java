
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

	public ArrayList<IsometricTile> tileList;
	public Point masterTile;
	public StructureType type;
	public String isoTileKey;
	public int structureOffset;
	/**
	 * @param type
	 * @param worldDimsIn
	 * @param worldPointIn
	 */
	public Structure() {

	}
//	public Structure(ArrayList<IsometricTile> tileList, Point masterTile, StructureType type) {
//		super();
//		this.tileList = tileList;
//		this.masterTile = masterTile;
//		this.type = type;
//
//	}
//
//	public Structure(Point masterTile, ArrayList<IsometricTile> tileList) {
//		super();
//		this.tileList = tileList;
//		this.masterTile = masterTile;
//
//
//	}
	public Structure(ArrayList<IsometricTile> tileList) {

		this.tileList = tileList;
		for(IsometricTile tile : tileList) {
			tile.setStructureOnTile(this);
		}
		this.worldPoint = tileList.get(0).worldPoint;

	}

	@Override
	public void clickAction() {
//		if(structureOnTile != null) {
//			System.out.println("Clicked a structure containing tile");
//		}else {
//			System.out.println("Clicked a tile of type: " + this.tileset + ". Walkable:"+this.walkable);
//		}

		if(this.currentlyClicked == false) {
			this.currentlyClicked = true;
		}else {
			this.currentlyClicked = false;
		}
	}
	public void hoverAction() {
//		System.out.println("structure hovered");
		this.currentlyHovered = true;

	}


}
