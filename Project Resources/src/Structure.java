import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

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
	public Structure(ArrayList<IsometricTile> tileList, Point masterTile, StructureType type) {
		super();
		this.tileList = tileList;
		this.masterTile = masterTile;
		this.type = type;
		
//		for(Point tile : tileList) {
//			Game.gameWorld.enslaveTile(tile,this.isoPoint);
//		}
	}
	
	public Structure(Point masterTile, ArrayList<IsometricTile> tileList) {
		super();
		this.tileList = tileList;
		this.masterTile = masterTile;
		

	}
	public Structure(ArrayList<IsometricTile> tileList) {
		
		this.tileList = tileList;
		for(IsometricTile tile : tileList) {
			tile.setStructureOnTile(this);
		}

//		this.isoTileKey = (int) masterTile.getX() + ":" + (int) masterTile.getY();
		this.worldPoint = tileList.get(0).worldPoint;
		this.worldDims = new Dimension(128,64);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
		this.objectImage = "citytile0";
		this.clickTag = "city";
	}
	public void hoverAction() {
//		System.out.println("structure hovered");
		this.currentlyHovered = true;

	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		if(currentlyHovered || currentlyClicked) {
			g.drawImage(Game.objectMap.getImage("cityhover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		}
	}


}
