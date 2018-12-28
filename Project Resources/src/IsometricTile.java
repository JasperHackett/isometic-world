import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class IsometricTile extends WorldObject{
	public Point isoPos;
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
		this.isoPos = tilePos;
		this.clickable = true;
		this.clickTag = "tile";
	}
	
	public Point getIsoPoint() {
		return this.isoPos;
	}
	
	public void changeTileset(TILESET tilesetIn) {
		this.tileset = tilesetIn;
		if(tilesetIn == TILESET.water) {
			this.objectImage = "watertile0";
		}
		if(tilesetIn == TILESET.grass) {
			this.objectImage = "grasstile0";
		}
		if(tilesetIn == TILESET.trees) {
			this.objectImage = "treestile0";
		}
	}
	@Override
	public void hoverAction() {
		this.currentlyHovered = true;
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x, coords.y+23, null);
		if(currentlyHovered) {
			g.drawImage(Game.objectMap.getImage("hover"), coords.x, coords.y+23, null);
		}
	}
}
