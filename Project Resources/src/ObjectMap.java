import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javafx.util.Pair;

/**
 * Stores all GameObjects and image assets
 *
 */
public class ObjectMap extends HashMap<String, GameObject> {

	/**
	 * Sorts via Y axis
	 *
	 */
	private class YAxisComparator implements Comparator<IsometricTile> {
		public int compare(IsometricTile o1, IsometricTile o2) {
			int y1 = (int) o1.getPosition().getValue().getY();
			int y2 = (int) o2.getPosition().getValue().getY();
			if (y1 == y2) {
				return 0;
			} else if (y1 > y2) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	private ArrayList<WorldObject> mainDisplayObjects;
	private ArrayList<Entity> mainDisplayEntitys;
	public ArrayList<IsometricTile> mainDisplayTiles;
	private HashMap<String, WorldObject> worldObjects;
	private HashMap<String, GameObject> menuObjects;
	private HashMap<String, GameObject> otherObjects;
	public HashMap<String, IsometricTile> worldTiles;
	public HashMap<String, Entity> worldEntitys;

	private HashMap<String, Image> imageMap;
	private HashMap<IsometricTile.TILESET, Integer> tilesPerTileset;

	public ObjectMap() {
		super();
		mainDisplayEntitys = new ArrayList<Entity>();
		mainDisplayTiles = new ArrayList<IsometricTile>();
		worldObjects = new HashMap<String, WorldObject>();
		otherObjects = new HashMap<String, GameObject>();
		menuObjects = new HashMap<String, GameObject>();
		imageMap = new HashMap<String, Image>();
		worldTiles = new HashMap<String, IsometricTile>();
		tilesPerTileset = new HashMap<IsometricTile.TILESET, Integer>();
		worldEntitys = new HashMap<String,Entity>();
	}

	
	public void addObject(ObjectType type, String s, GameObject obj) {

		this.put(s, obj);
		switch (type) {
		case MAINMENU:
			menuObjects.put(s, obj);
			break;
		case DEFAULT:
			otherObjects.put(s, obj);
			break;
		case CHILD:
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

	public Point toIsometric(Point pointIn) {
		Point tempPoint = new Point(0, 0);
		tempPoint.x = pointIn.x - pointIn.y;
		tempPoint.y = (pointIn.x + pointIn.y) / 2;

		return (tempPoint);
	}

	public void addWorldTile(Point pointIn, IsometricTile.TILESET tileset, Point tilePos) {

		Random randomNum = new Random();
		int rn = randomNum.nextInt(3);
		String tileName = "";
		Dimension dim = new Dimension(64, 32); // default dimensions is 64x32, the else if statement below changes this
												// for tiles with bigger sprites e.g. trees.
		Point offsetPoint = toIsometric(new Point(pointIn.x, pointIn.y)); // default offsetPoint is the same as pointIn,
																			// the else if statement can change this
																			// however

		if (tileset == IsometricTile.TILESET.grass) {
			tileName = "grasstile" + Integer.toString(rn);
		} else if (tileset == IsometricTile.TILESET.water) {
			tileName = "watertile" + Integer.toString(rn);
		} else if (tileset == IsometricTile.TILESET.road) {
			tileName = "roadtile";
		} else if (tileset == IsometricTile.TILESET.trees) {
			tileName = "treetile" + Integer.toString(rn);
			dim = new Dimension(64, 40);
			offsetPoint.y = offsetPoint.y - 8;
		}

		IsometricTile newTile = new IsometricTile(ObjectType.WORLD, new Dimension(64, 32), offsetPoint, tileset,
				tilePos);
		newTile.setProperties(dim, tilePos, tileName, true);
		newTile.hoverable = true;
		tileName = Integer.toString(tilePos.x) + ":" + Integer.toString(tilePos.y);
		this.put(tileName, newTile);
		worldObjects.put(tileName, newTile);
		worldTiles.put(tileName, newTile);
		// System.out.println(tileName + ": " +
		// worldObjects.get(tileName).getWorldPosition());
	}

	public void addEntity(String structureName, Entity structureIn, int structureOffset) {
		structureIn.structureOffset = structureOffset;
		this.worldObjects.put(structureName, structureIn);
		this.worldEntitys.put(structureName, structureIn);
		this.put(structureName, structureIn);
	}
	
	public void addWorldEntity(Entity.EntityType type, Point masterTile, ArrayList<Point> tileList) {

		String imageName = "";
		Random randomNum = new Random();
		int rn = randomNum.nextInt(3);

		// calculating the dimensions of the structure
		// based on the upper and lower limits of all of the tiles it occupies.
		int upperX = Integer.MIN_VALUE;
		int lowerX = Integer.MAX_VALUE;
		int upperY = Integer.MIN_VALUE;
		int lowerY = Integer.MAX_VALUE;
		for (Point tilePos : tileList) {
			IsometricTile tile = worldTiles.get(Integer.toString(tilePos.x) + ":" + Integer.toString(tilePos.y));
			int x = tile.getWorldPosition().getValue().x;
			int y = tile.getWorldPosition().getValue().y;
			if (x + tile.getPosition().getKey().getWidth() > upperX) {
				upperX = tilePos.x;
			}
			if (x < lowerX) {
				lowerX = tilePos.x;
			}
			if (y + tile.getPosition().getKey().getHeight() > upperY) {
				upperY = tilePos.y;
			}
			if (y < lowerY) {
				lowerY = tilePos.y;
			}
		}
		Dimension dim = new Dimension(upperX - lowerX, upperY - lowerY);
		String structureName = "";
		Entity newEntity = null;

		Point offsetPoint = new Point(lowerX, lowerY);
		if (type == Entity.EntityType.city) {
			imageName = "city" + Integer.toString(rn);
			offsetPoint.y = offsetPoint.y - 16;
			// newEntity = new City(tileList, masterTile);

			structureName += "city";
		}
		// temporary way to create unique keys until a naming convention is decided for
		// structures
		while (true) {
			structureName += Integer.toString(randomNum.nextInt(10));
			if (this.putIfAbsent(structureName, newEntity) == null) {
				break;
			}
		}
		newEntity.setProperties(dim, offsetPoint, imageName);

		worldObjects.put(structureName, newEntity);
	}

	public void addImage(String imgID, String FilePath) {
		Image newImage = new ImageIcon(FilePath).getImage();
		imageMap.put(imgID, newImage);
	}

	public void addTileImage(String imgID, String FilePath, Dimension tileDims, int tileCount) {
		if (imgID == "grasstile") {
			tilesPerTileset.put(IsometricTile.TILESET.grass, tileCount);
		} else if (imgID == "watertile") {
			tilesPerTileset.put(IsometricTile.TILESET.water, tileCount);
		} else if (imgID == "treetile") {
			tilesPerTileset.put(IsometricTile.TILESET.trees, tileCount);
		} else if (imgID == "citytile") {
			tilesPerTileset.put(IsometricTile.TILESET.city, tileCount);
		}
		String imgName;
		BufferedImage tilesheet = null;
		try {
			tilesheet = ImageIO.read(new File(FilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Image newImage = null;
		for (int i = 0; i < tileCount; i++) {
			newImage = tilesheet.getSubimage(tileDims.width * i, 0, tileDims.width, tileDims.height);
			imgName = imgID + Integer.toString(i);
			imageMap.put(imgName, newImage);
		}
		// System.out.println(this.imageMap.keySet());
		// imageMap.put(imgID,newImage);
	}

	public Image getImage(String imgID) {
		return this.imageMap.get(imgID);
	}

	public IsometricTile getTile(Point pointIn) {
		if (this.worldTiles.containsKey(pointIn.x + ":" + pointIn.y)) {
			return this.worldTiles.get(pointIn.x + ":" + pointIn.y);
		} else {
			System.out.println("Invalid tile coordinate: [" + pointIn.x + "," + pointIn.y + "]");
			return null;
		}
	}

	// returns true after a successful remove operation
	// returns false if there is no object with that key in the map
	public boolean removeObject(String s) {
		if (!this.containsKey(s)) {
			return false;
		}
		this.remove(s);
		worldObjects.remove(s);
		// mainDisplayTiles.remove(s);
		return true;
	}

	public GameObject getObject(String s) {
		return (GameObject) this.get(s);
	}

	public TextObject getTextObject(String s) {
		return (TextObject) this.get(s);
	}

	public ArrayList<IsometricTile> getMainDisplayTiles() {
		return mainDisplayTiles;

	}

	public ArrayList<Entity> getMainDisplayEntitys() {
		return mainDisplayEntitys;

	}

	public ArrayList<WorldObject> getMainDisplayObjects() {
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

	public void updateMainDisplayObjects(/* Dimension displayDimension, Point displayPoint */) {

		try {
			Game.mainGameRenderer.semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainDisplayObjects = new ArrayList<WorldObject>();
		mainDisplayTiles = new ArrayList<IsometricTile>();

		for (Map.Entry<String, IsometricTile> mapEntry : worldTiles.entrySet()) {
			IsometricTile obj = mapEntry.getValue();
			if (isWithinDisplay(obj.getWorldPosition(),
					new Pair<Dimension, Point>(Game.gameWorld.panelDims, Game.gameWorld.worldPoint))) {
				mainDisplayTiles.add(mapEntry.getValue());
				mainDisplayObjects.add(mapEntry.getValue());
			}
		}

		mainDisplayEntitys = new ArrayList<Entity>();
		if (worldEntitys != null) {
			for (Map.Entry<String, Entity> mapEntry : worldEntitys.entrySet()) {
				WorldObject obj = mapEntry.getValue();
				if (isWithinDisplay(obj.getWorldPosition(),
						new Pair<Dimension, Point>(Game.gameWorld.panelDims, Game.gameWorld.worldPoint))) {
					mainDisplayEntitys.add(mapEntry.getValue());
					mainDisplayObjects.add(mapEntry.getValue());
				}
			}
		}

		YAxisComparator comp = new YAxisComparator();
		// mainDisplayObjects.sort((Comparator)comp);
		// mainDisplayEntitys.sort((Comparator)comp);
		mainDisplayTiles.sort((Comparator) comp);

		Game.mainGameRenderer.semaphore.release();

	}

	// returns true if at least one corner of a given object is within the Display's
	// current bounds
	public boolean isWithinDisplay(Pair<Dimension, Point> objectBounds, Pair<Dimension, Point> displayBounds) {

		// checking each corner of the object to see if it is within the display's
		// bounds

		// top left corner == x,y
		if (objectBounds == null) {
			System.out.println("test");
		}
		int x = objectBounds.getValue().x;
		int y = objectBounds.getValue().y;
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()) {
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()) {
				return true;
			}
		}
		// top right corner == x+width, y
		x = objectBounds.getValue().x + (int) objectBounds.getKey().getWidth();
		y = objectBounds.getValue().y;
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()) {
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()) {
				return true;
			}
		}
		// bottom left corner == x, y+height
		x = objectBounds.getValue().x;
		y = objectBounds.getValue().y + (int) objectBounds.getKey().getHeight();
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()) {
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()) {
				return true;
			}
		}
		// bottom right corner == x+width, y+height
		x = objectBounds.getValue().x + (int) objectBounds.getKey().getWidth();
		y = objectBounds.getValue().y + (int) objectBounds.getKey().getHeight();
		if (x > displayBounds.getValue().x && x < displayBounds.getValue().x + displayBounds.getKey().getWidth()) {
			if (y > displayBounds.getValue().y && y < displayBounds.getValue().y + displayBounds.getKey().getHeight()) {
				return true;
			}
		}
		return false;
	}
}
