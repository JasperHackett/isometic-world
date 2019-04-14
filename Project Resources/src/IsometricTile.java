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
		trees,
		city;
	}
	public enum OWNERSET{
		none,
		red,
		blue,
		pink;
	}
	public TILESET tileset;
	public City currentOwner;
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
		this.currentOwner = null;
		roadImage = null;
	}
	public void setEntityOnTile(Entity entityOnTile) {
		if(entityOnTile != null) {
			this.walkable = false;
		}
		this.entityOnTile = entityOnTile;

	}
	
	public boolean hasEntityOnTile() {
		if (entityOnTile == null) {
			return false;
		}
		return true;
	}
	
	public Entity getEntityOnTile() {
		return entityOnTile;
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

	public void setOwner(City newOwner) {

		if (newOwner == null) {
			borderImage = null;
		}else {
			currentOwner = newOwner;
		
			ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
			if (Game.objectMap.tileExists(new Point(this.isoPos.x-1, this.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x-1, this.isoPos.y)));} else {neighbours.add(null);}
			if (Game.objectMap.tileExists(new Point(this.isoPos.x, this.isoPos.y-1))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y-1)));} else {neighbours.add(null);}
			if (Game.objectMap.tileExists(new Point(this.isoPos.x+1, this.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x+1, this.isoPos.y)));} else {neighbours.add(null);}
			if (Game.objectMap.tileExists(new Point(this.isoPos.x, this.isoPos.y+1))) {neighbours.add(Game.objectMap.getTile(new Point(this.isoPos.x, this.isoPos.y+1)));} else {neighbours.add(null);}
			for (int i = 0; i < neighbours.size(); i++) {
				if (neighbours.get(i) != null) {
					if (neighbours.get(i).currentOwner != null) {
						neighbours.get(i).updateBorderImage();
					}
				}
			}
			this.updateBorderImage();
		}

	}

	public boolean isWalkable() {
		return walkable;
	}

	@Override
	public void hoverAction() {
//		System.out.println("tile hovered at:" + this.isoPos);

		if(entityOnTile != null ) {
			entityOnTile.hoverAction();
		}else {
			this.currentlyHovered = true;
		}
	}

	@Override
	public void clickAction() {
			String toPrint = "Clicked a " + this.tileset + " tile";
			if (this.hasRoad()) {
				toPrint += " with a road";
			}
			if (this.currentOwner != null) {
				toPrint += ". This tile is owned by " + this.currentOwner.name;
			}
			toPrint += ".";
			System.out.println(toPrint);
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
			this.borderImage = currentOwner.borderColour + "owned0";
		}
		// 1side right
		else if (ownedLeft && !ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned1";
		}
		// 1side left
		else if (!ownedLeft && ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned2";
		}
		// 1side up
		else if (ownedLeft && ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned3";
		}
		// 2side up and left
		else if (!ownedLeft && ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned4";
		}
		// 2side down and right
		else if (ownedLeft && !ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned5";
		}
		// 2side up and right
		else if (ownedLeft && !ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned6";
		}
		// 2side down and left
		else if (!ownedLeft && ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned7";
		}
		// 2side up and down
		else if (ownedLeft && ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned13";
		}
		// 2side left and right
		else if (!ownedLeft && !ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned14";
		}
		// 3side up, down, left
		else if (!ownedLeft && ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned8";
		}
		// 3side up, left, right
		else if (!ownedLeft && !ownedRight && !ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned9";
		}
		// 3side down, left, right
		else if (!ownedLeft && !ownedRight && ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned10";
		}
		// 3side up, down, right
		else if (ownedLeft && !ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned11";
		}
		// 4side
		else if (!ownedLeft && !ownedRight && !ownedUp && !ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned12";
		}
		// no sides
		else if (ownedLeft && ownedRight && ownedUp && ownedDown) {
			this.borderImage = currentOwner.borderColour + "owned15";
		}
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		if(currentlyHovered && entityOnTile == null) {
			g.drawImage(Game.objectMap.getImage("hover"), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
//		if (this.currentOwner == OWNERSET.red) {
//			g.drawImage(Game.objectMap.getImage("redOwnedTile"), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
//		}
		if (hasRoad()) {
			g.drawImage(Game.objectMap.getImage(roadImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
		if (this.currentOwner != null) {
			g.drawImage(Game.objectMap.getImage(borderImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
		if(currentlyHovered && entityOnTile == null) {
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
