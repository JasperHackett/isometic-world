import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class Entity extends WorldObject{

	public enum EntityType {
		city,
		road;
	}

	public ArrayList<IsometricTile> tileList;
	public Point masterTile;
	public EntityType type;
	public String isoTileKey;
	public int structureOffset;
//	protected HashMap<GameObject, Pair<Integer, Integer>> children;

	
	public Entity() {
		
	}
	
	public Entity(ArrayList<IsometricTile> tileList) {

		this.tileList = tileList;
		for (IsometricTile tile : tileList) {
			tile.setEntityOnTile(this);
		}
		this.worldPoint = tileList.get(0).worldPoint;
//		this.children = new HashMap<GameObject, Pair<Integer, Integer>>();
	}

//	public void addChild(GameObject child, Pair<Integer, Integer> positionOffset) {
//		this.children.put(child, positionOffset);
//
//	}
	
	
	/**
	 * returns an Iso Point corresponding to a neighbour of this entity
	 * that is closest to the given Iso Point
	 */
	public Point getClosestNeighbour(Point IsoPoint) {
		ArrayList<IsometricTile> entityNeighbours = new ArrayList<IsometricTile>();
		for (IsometricTile entityTile : tileList) {
			ArrayList<IsometricTile> tileNeighbours = Game.gameWorld.getNeighbours(entityTile);
			for (IsometricTile tile : tileNeighbours) {
				if (tile.isWalkable() && !entityNeighbours.contains(tile) && !tileList.contains(tile)) {
					entityNeighbours.add(tile);
				}
			}
		}
		int fromX = isoPoint.x;
		int fromY = isoPoint.y;
		int closestDistance = Integer.MAX_VALUE;
		Point output = null;
		for (IsometricTile tile : entityNeighbours) {
			int toX = tile.getIsoPoint().x;
			int toY = tile.getIsoPoint().y;
			int differenceX = Math.abs(toX - fromX);
			int differenceY = Math.abs(toY - fromY);
			int difference = differenceX + differenceY;
			if (difference < closestDistance) {
				closestDistance = difference;
				output = new Point(toX, toY);
			}
		}
		return output;
	}
	
	@Override
	public void setPosition(Point worldPointIn, Point displayPanelPoint) {
		this.coords.setLocation((displayPanelPoint.getX() + (this.worldPoint.getX() - worldPointIn.getX())),
				((displayPanelPoint.getY() + (this.worldPoint.getY() - worldPointIn.getY()))));
		if (children == null) {
			return;
		} else if (children.isEmpty()) {
			return;
		} else {
			for (GameObject child : children.keySet()) {
				child.coords.setLocation(
						(displayPanelPoint.getX() + (this.worldPoint.getX() - worldPointIn.getX()))
								+ children.get(child).getKey(),
						((displayPanelPoint.getY() + (this.worldPoint.getY() - worldPointIn.getY()))
								+ children.get(child).getValue()));
			}
		}
	}

	@Override
	public void clickAction() {
		// if(structureOnTile != null) {
		// System.out.println("Clicked a structure containing tile");
		// }else {
		// System.out.println("Clicked a tile of type: " + this.tileset + ".
		// Walkable:"+this.walkable);
		// }

		if (this.currentlyClicked == false) {
			this.currentlyClicked = true;
		} else {
			this.currentlyClicked = false;
		}
	}

	public void hoverAction() {
		// System.out.println("structure hovered");
		this.currentlyHovered = true;

	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		if (children == null) {
			return;
		} else if (children.isEmpty()) {
			return;
		} else {
			for (GameObject child : children.keySet()) {
				child.render(g);
			}
		}
	}
}
