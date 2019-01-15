import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

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
	public String roadImage;
	public String borderImage;
	public enum TILESET{
		grass,
		water,
		trees;
	}
	public enum OWNERSET{
		none,
		red,
		blue;
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
		if (this.tileset == TILESET.grass) {
			this.walkable = true;
		} else {
			this.walkable = false;
		}
		this.currentOwner = OWNERSET.none;
		roadImage = null;
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
		if (newOwner == OWNERSET.none) {
			borderImage = null;
		}
		ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
		if (Game.objectMap.tileExists(new Point(this.isoPos.x-1, this.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x-1, this.isoPos.y)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(this.isoPos.x, this.isoPos.y-1))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y-1)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(this.isoPos.x+1, this.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x+1, this.isoPos.y)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(this.isoPos.x, this.isoPos.y+1))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y+1)));} else {neighbours.add(null);}
		for (int i = 0; i < neighbours.size(); i++) {
			if (neighbours.get(i) != null) {
				if (neighbours.get(i).currentOwner != OWNERSET.none) {
					neighbours.get(i).updateBorderImage();
				}
			}
		}
		this.updateBorderImage();

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
			String toPrint = "Clicked a " + this.tileset + " tile";
			if (this.hasRoad()) {
				toPrint += " with a road";
			}
			if (this.currentOwner != OWNERSET.none) {
				toPrint += ". This tile is owned by " + this.currentOwner;
			}
			toPrint += ".";
			System.out.println(toPrint);
		}
	}

	public Boolean hasRoad() {
		if (roadImage == null) {
			return false;
		} else {
			return true;
		}
	}

	public void setRoad(Boolean b) {
		if (b == false) {
			roadImage = null;
		} else {
			roadImage = "road1";
			ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
			neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x-1, this.isoPos.y)));
			neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y-1)));
			neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x+1, this.isoPos.y)));
			neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y+1)));

			for (IsometricTile tile : neighbours) {
				if (tile.hasRoad()) {
					tile.updateRoadImage();
				}
			}
			this.updateRoadImage();
		}
	}

	private void updateRoadImage() {
		ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
		neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x-1, this.isoPos.y)));
		neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y-1)));
		neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x+1, this.isoPos.y)));
		neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y+1)));
		Boolean roadLeft = false;
		Boolean roadRight = false;
		Boolean roadUp = false;
		Boolean roadDown = false;
		if (neighbours.get(0).hasRoad()) {
			roadLeft = true;
		}
		if (neighbours.get(1).hasRoad()) {
			roadUp = true;
		}
		if (neighbours.get(2).hasRoad()) {
			roadRight = true;
		}
		if (neighbours.get(3).hasRoad()) {
			roadDown = true;
		}
		// straight vertical road (also default for roads with no neighbours)
		if ((!roadLeft && !roadRight && !roadUp && !roadDown) || (roadUp && roadDown && !roadLeft && !roadRight) || (!roadLeft && !roadRight && roadUp && !roadDown) || (!roadLeft && !roadRight && !roadUp && roadDown)) {
			this.roadImage = "road1";
		}
		// straight horizontal road
		else if ((roadLeft && roadRight && !roadUp && !roadDown) || (!roadLeft && roadRight && !roadUp && !roadDown) || (roadLeft && !roadRight && !roadUp && !roadDown)) {
			this.roadImage = "road0";
		}
		// corner left and up
		else if (roadLeft && !roadRight && roadUp && !roadDown) {
			this.roadImage = "road2";
		}
		// corner right and down
		else if (!roadLeft && roadRight && !roadUp && roadDown) {
			this.roadImage = "road3";
		}
		// corner left and down
		else if (roadLeft && !roadRight && !roadUp && roadDown) {
			this.roadImage = "road4";
		}
		// corner right and up
		else if (!roadLeft && roadRight && roadUp && !roadDown) {
			this.roadImage = "road5";
		}
		// 3way left, down, right
		else if (roadLeft && roadRight && !roadUp && roadDown) {
			this.roadImage = "road6";
		}
		// 3way down, right, up
		else if (!roadLeft && roadRight && roadUp && roadDown) {
			this.roadImage = "road7";
		}
		// 3way down, left, up
		else if (roadLeft && !roadRight && roadUp && roadDown) {
			this.roadImage = "road8";
		}
		// 3way left, up, right
		else if (roadLeft && roadRight && roadUp && !roadDown) {
			this.roadImage = "road9";
		}
		// 4way
		else if (roadLeft && roadRight && roadUp && roadDown) {
			this.roadImage = "road10";
		}
	}

	private void updateBorderImage() {
		ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
		if (Game.objectMap.tileExists(new Point(this.isoPos.x-1, this.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x-1, this.isoPos.y)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(this.isoPos.x, this.isoPos.y-1))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y-1)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(this.isoPos.x+1, this.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x+1, this.isoPos.y)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(this.isoPos.x, this.isoPos.y+1))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y+1)));} else {neighbours.add(null);}
		Boolean ownedLeft = false;
		Boolean ownedRight = false;
		Boolean ownedUp = false;
		Boolean ownedDown = false;
		if (neighbours.get(0) != null){ if (neighbours.get(0).currentOwner == this.currentOwner) {
			ownedLeft = true;
		} }
		if (neighbours.get(1) != null){ if (neighbours.get(1).currentOwner == this.currentOwner) {
			ownedUp = true;
		} }
		if (neighbours.get(2) != null){ if (neighbours.get(2).currentOwner == this.currentOwner) {
			ownedRight = true;
		} }
		if (neighbours.get(3) != null){ if (neighbours.get(3).currentOwner == this.currentOwner) {
			ownedDown = true;
		} }

		/*
		Determining which image to use based on neighbours ownership status
		*/
		// 1side down
		if (ownedLeft && ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned0";
		}
		// 1side right
		else if (ownedLeft && !ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned1";
		}
		// 1side left
		else if (!ownedLeft && ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned2";
		}
		// 1side up
		else if (ownedLeft && ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned3";
		}
		// 2side up and left
		else if (!ownedLeft && ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned4";
		}
		// 2side down and right
		else if (ownedLeft && !ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned5";
		}
		// 2side up and right
		else if (ownedLeft && !ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned6";
		}
		// 2side down and left
		else if (!ownedLeft && ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned7";
		}
		// 2side up and down
		else if (ownedLeft && ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned13";
		}
		// 2side left and right
		else if (!ownedLeft && !ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned14";
		}
		// 3side up, down, left
		else if (!ownedLeft && ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned8";
		}
		// 3side up, left, right
		else if (!ownedLeft && !ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned9";
		}
		// 3side down, left, right
		else if (!ownedLeft && !ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned10";
		}
		// 3side up, down, right
		else if (ownedLeft && !ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned11";
		}
		// 4side
		else if (!ownedLeft && !ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.toString() + "owned12";
		}
		// no sides
		else if (ownedLeft && ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.toString() + "owned15";
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
		if (hasRoad()) {
			g.drawImage(Game.objectMap.getImage(roadImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
		if (this.currentOwner != OWNERSET.none) {
			g.drawImage(Game.objectMap.getImage(borderImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
		if(currentlyHovered && structureOnTile == null) {
			g.drawImage(Game.objectMap.getImage("hover"), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
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
