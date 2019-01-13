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
	public boolean slave;
	public Point masterLocation;
	protected Entity entityOnTile = null;
	public enum TILESET{
		grass,
		water,
		trees,
		city,
		road;
	}
	public enum OWNERSET{
		none,
		red;
	}
	public TILESET tileset;
	public OWNERSET currentOwner;
	/**
	 * @param type
	 * @param worldDimsIn
	 * @param worldPointIn
	 */
	public IsometricTile(ObjectType type, Dimension worldDimsIn, Point worldPointIn, TILESET tileset, Point tilePos) {
		super(type, worldDimsIn, worldPointIn);
		this.tileset = tileset;
		this.isoPos = tilePos.getLocation();
		this.clickable = true;
		this.clickTag = "tile";
		if (this.tileset == TILESET.grass || this.tileset == TILESET.road) {
			this.walkable = true;
		} else {
			this.walkable = false;
		}
		this.currentOwner = OWNERSET.none;
	}
	public void setEntityOnTile(Entity entityOnTile) {
		if(entityOnTile != null) {
			this.walkable = false;
		}
		this.entityOnTile = entityOnTile;

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

	public void setOwner(OWNERSET newOwner) {
		currentOwner = newOwner;
	}

	public boolean isWalkable() {
		return walkable;
	}

	@Override
	public void hoverAction() {
//		System.out.println("tile hovered at:" + this.isoPos);

		if(entityOnTile != null) {
			entityOnTile.hoverAction();
		}else {
			this.currentlyHovered = true;
		}
	}

	@Override
	public void clickAction() {
		if(entityOnTile != null) {
			System.out.println("Clicked a entity containing tile");
			entityOnTile.clickAction();
		}else {
			System.out.println("Clicked a tile of type: " + this.tileset);
		}
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		if(currentlyHovered && entityOnTile == null) {
			g.drawImage(Game.objectMap.getImage("hover"), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
		if (this.currentOwner == OWNERSET.red) {
			g.drawImage(Game.objectMap.getImage("redOwnedTile"), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
	}

	@Override
	public void disableHover(){
		this.currentlyHovered = false;
		if(entityOnTile != null) {
			entityOnTile.disableHover();
		}
	}
	@Override
	public void disableClick(){
		System.out.println("Disabled click");
		this.currentlyClicked = false;
		if(entityOnTile != null) {
			entityOnTile.disableClick();
		}
	}

}
