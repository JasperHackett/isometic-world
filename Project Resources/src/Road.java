import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Orly
 *
 */
public class Road extends Entity {
	
	public IsometricTile tile;
	/**
	 * @param tileList
	 */
	public Road(ArrayList<IsometricTile> tileList) {
		super(tileList);
		this.tile = tileList.get(0);
		tile.walkable = true;
	}
	
	private void updateRoadImage() {
		ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
		neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x-1, tile.isoPos.y)));
		neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x, tile.isoPos.y-1)));
		neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x+1, tile.isoPos.y)));
		neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x, tile.isoPos.y+1)));
		Boolean roadLeft = false;
		Boolean roadRight = false;
		Boolean roadUp = false;
		Boolean roadDown = false;
		if (!(neighbours.get(0).entityOnTile == null)) {
			roadLeft = neighbours.get(0).entityOnTile.type == EntityType.road;
		} 
		if (!(neighbours.get(1).entityOnTile == null)) {
			roadUp = neighbours.get(1).entityOnTile.type == EntityType.road;
		}
		if (!(neighbours.get(2).entityOnTile == null)) {
			roadRight = neighbours.get(2).entityOnTile.type == EntityType.road;
		} 
		if (!(neighbours.get(3).entityOnTile == null)) {
			roadDown = neighbours.get(3).entityOnTile.type == EntityType.road;
		} 
		// straight vertical road (also default for roads with no neighbours)
		if ((!roadLeft && !roadRight && !roadUp && !roadDown) || (roadUp && roadDown && !roadLeft && !roadRight) || (!roadLeft && !roadRight && roadUp && !roadDown) || (!roadLeft && !roadRight && !roadUp && roadDown)) {
			this.objectImage = "road1";
		} 
		// straight horizontal road
		else if ((roadLeft && roadRight && !roadUp && !roadDown) || (!roadLeft && roadRight && !roadUp && !roadDown) || (roadLeft && !roadRight && !roadUp && !roadDown)) {
			this.objectImage = "road0";
		}
		// corner left and up
		else if (roadLeft && !roadRight && roadUp && !roadDown) {
			this.objectImage = "road2";
		}
		// corner right and down
		else if (!roadLeft && roadRight && !roadUp && roadDown) {
			this.objectImage = "road3";
		} 
		// corner left and down
		else if (roadLeft && !roadRight && !roadUp && roadDown) {
			this.objectImage = "road4";
		} 
		// corner right and up
		else if (!roadLeft && roadRight && roadUp && !roadDown) {
			this.objectImage = "road5";
		} 
		// 3way left, down, right
		else if (roadLeft && roadRight && !roadUp && roadDown) {
			this.objectImage = "road6";
		} 
		// 3way down, right, up
		else if (!roadLeft && roadRight && roadUp && roadDown) {
			this.objectImage = "road7";
		} 
		// 3way down, left, up
		else if (roadLeft && !roadRight && roadUp && roadDown) {
			this.objectImage = "road8";
		}
		// 3way left, up, right
		else if (roadLeft && roadRight && roadUp && !roadDown) {
			this.objectImage = "road9";
		}
		// 4way
		else if (roadLeft && roadRight && roadUp && roadDown) {
			this.objectImage = "road10";
		}
	}
	@Override
	public void render(Graphics g) {
		updateRoadImage();
		super.render(g);
		if(currentlyHovered) {
			g.drawImage(Game.objectMap.getImage("hover"), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		}
	}
}
