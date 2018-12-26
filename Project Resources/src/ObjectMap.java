import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Orly
 *
 */
public class ObjectMap extends HashMap<String, GameObject> {

	private HashMap<String, WorldObject> mainDisplayObjects;
	private HashMap<String, WorldObject> worldObjects;
	private HashMap<String, GameObject> menuObjects;
	private HashMap<String, GameObject> otherObjects;
	private HashMap<String, IsometricTile> worldTiles;

	public ObjectMap(){
		super();
		mainDisplayObjects = new HashMap<String, WorldObject>();
		worldObjects = new HashMap<String, WorldObject>();
		otherObjects = new HashMap<String, GameObject>();
		menuObjects = new HashMap<String,GameObject>();
	}

	public void addObject(ObjectType type, String s, GameObject obj) {
		
		
		this.put(s, obj);
		switch(type) {
			case MAINMENU:
				menuObjects.put(s, obj);
				break;
			case DEFAULT:
				otherObjects.put(s,obj);
				break;
			default:
				break;
		}

	}


	public void addWorldObject(String s, WorldObject obj) {
		this.put(s, obj);
		if (obj.type == ObjectType.WORLD) {
			worldObjects.put(s, obj);
		}
	}
	
	public void addWorldTile(String s,  IsometricTile.TILESET tileset) {
		IsometricTile newTile = new IsometricTile(ObjectType.WORLD,new Dimension(100, 100), new Point(300, 300),IsometricTile.TILESET.grass);
		this.put(s, newTile);
		worldObjects.put(s, newTile);
		worldTiles.put(s, newTile);
	}
	

	// returns true after a successful remove operation
	// returns false if there is no object with that key in the map
	public boolean removeObject(String s){
		if (!this.containsKey(s)) {
			return false;
		}
		this.remove(s);
		worldObjects.remove(s);
		mainDisplayObjects.remove(s);
		return true;
	}

	public GameObject getObject(String s) {
		return (GameObject)this.get(s);
	}

	public Map<String, WorldObject> getMainDisplayObjects() {
		return mainDisplayObjects;

	}
	public Map<String, GameObject> getOtherObjects() {
		return otherObjects;
	}
	public Map<String, GameObject> getMenuObjects() {
		return menuObjects;
	}
	public Map<String, WorldObject> WorldObjects() {
		return worldObjects;
	}

	public void updateMainDisplayObjects(/*Dimension displayDimension, Point displayPoint*/){
		
		try {
			Game.mainGameRenderer.semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mainDisplayObjects = new HashMap<String, WorldObject>();

		for (Map.Entry<String, WorldObject> mapEntry : worldObjects.entrySet()) {
			WorldObject obj = mapEntry.getValue();
			if (isWithinDisplay(obj.getWorldPosition(), new Pair<Dimension, Point>(Game.gameWorld.panelDims, Game.gameWorld.worldPoint))) {
				mainDisplayObjects.put(mapEntry.getKey(), mapEntry.getValue());
			}
		}
		
		Game.mainGameRenderer.semaphore.release();

	}

	// returns true if at least one corner of a given object is within the Display's current bounds
	public boolean isWithinDisplay(Pair<Dimension, Point> objectBounds, Pair<Dimension, Point> displayBounds) {

		//checking each corner of the object to see if it is within the display's bounds

		// top left corner == x,y
		int x = objectBounds.getValue().x;
		int y = objectBounds.getValue().y;
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()){
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()){
				return true;
			}
		}
		// top right corner == x+width, y
		x = objectBounds.getValue().x + (int)objectBounds.getKey().getWidth();
		y = objectBounds.getValue().y;
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()){
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()){
				return true;
			}
		}
		// bottom left corner == x, y+height
		x = objectBounds.getValue().x;
		y = objectBounds.getValue().y + (int)objectBounds.getKey().getHeight();
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()){
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()){
				return true;
			}
		}
		// bottom right corner == x+width, y+height
		x = objectBounds.getValue().x + (int)objectBounds.getKey().getWidth();
		y = objectBounds.getValue().y + (int)objectBounds.getKey().getHeight();
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()){
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()){
				return true;
			}
		}
		return false;
	}
}

