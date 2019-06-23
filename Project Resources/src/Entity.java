import java.awt.Dimension;
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
		resource;
	}

	public ArrayList<IsometricTile> tileList;
	public Point masterTile;
	public EntityType type;
	public String isoTileKey;
	public int structureOffset;
	public String name;
//	protected HashMap<GameObject, Pair<Integer, Integer>> children;

	
	public Entity() {
		
	}
//	Entity(Point isoPos){
//		
////		currentPath = new LinkedList<Point>(Game.gameWorld.getPathBetween(new Point(4,6), new Point(41,55)));
////		this.structureOffset = 16;
//		this.isoPoint = isoPos;
////		Game.objectMap.getTile(isoPos);
//		this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
//		this.worldDims = new Dimension(64,32);
//		this.dim = new Dimension(64,32);
//		this.coords = new Point(0,0);
//		
//	}
	
	
	public Entity(ArrayList<IsometricTile> tileList) {
//		System.out.println(tileList.size());
		this.tileList = tileList;
		for (IsometricTile tile : tileList) {
			tile.setEntityOnTile(this);
		}
		this.worldPoint = tileList.get(0).worldPoint;
		this.isoPoint = tileList.get(0).isoPos;
//		System.out.println(isoPoint);
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
	public Point getClosestNeighbour(Point pointIn) {
		ArrayList<IsometricTile> entityNeighbours = new ArrayList<IsometricTile>();
		for (IsometricTile entityTile : tileList) {
			ArrayList<IsometricTile> tileNeighbours = Game.gameWorld.getNeighbours(entityTile);
			for (IsometricTile tile : tileNeighbours) {
				if (tile.isWalkable() && !entityNeighbours.contains(tile) && !tileList.contains(tile)) {
					entityNeighbours.add(tile);
				}
			}
		}
		int fromX = pointIn.x;
		int fromY = pointIn.y;
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

			setChildrenPosition();
		
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
		g.drawImage(objectImage, coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		if (children != null) {
			for (GameObject child : children.keySet()) {
				child.render(g);
			}
		}
	}
}
