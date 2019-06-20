import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
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
	private class YAxisComparator implements Comparator<WorldObject> {
		public int compare(WorldObject o1, WorldObject o2) {
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
	public HashMap<String,UserInterfaceObject> uiObjects;
	public ArrayList<UserInterfaceObject> enabledUIObjects;
	public HashMap<String,Font> gameFonts;

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
		uiObjects = new HashMap<String,UserInterfaceObject>();
		enabledUIObjects = new ArrayList<UserInterfaceObject>();
		gameFonts = new HashMap<String,Font>();
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
	public void transformImage(String imageName,int nWidth, int nHeight) {
		if(!imageMap.containsKey(imageName)) {
			System.out.println("Does not contain image");
			return;
		}
		Image tmp = imageMap.get(imageName).getScaledInstance(nHeight, nWidth, Image.SCALE_SMOOTH);
		tmp = tmp.getScaledInstance(nWidth, nHeight, Image.SCALE_FAST);
		BufferedImage img = new BufferedImage(nWidth,nHeight,BufferedImage.TYPE_INT_ARGB);
//		System.out.println(img.getHeight(observer));
//		img = img.getScaledInstance(1, 1, 0);

		System.out.println(imageMap.get(imageName).toString());
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(tmp,0, 0, null);
        g2d.dispose();
//		imageMap.put(imageName, img);
	}

	public Point toIsometric(Point pointIn) {
		Point tempPoint = new Point(0, 0);
		tempPoint.x = pointIn.x - pointIn.y;
		tempPoint.y = (pointIn.x + pointIn.y) / 2;

		return (tempPoint);
	}

	public void addFont(String fontKey, String fontName,int fontStyle, int fontSize) {
		Font newFont = new Font(fontName,fontStyle,fontSize);
		this.gameFonts.put(fontKey, newFont);
//				Font.p
	}
	public Font getFont(String fontKey) {
		return gameFonts.get(fontKey);
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
		structureIn.objID = structureName;
		this.worldObjects.put(structureName, structureIn);
		this.worldEntitys.put(structureName, structureIn);
		this.put(structureName, structureIn);
	}
	
//	public void addResource(String structureName, Entity structureIn, int structureOffset,Resource.ResourceType type) {
//		structureIn.structureOffset = structureOffset;
//		this.worldObjects.put(structureName, structureIn);
//		this.worldEntitys.put(structureName, structureIn);
//		this.put(structureName, structureIn);
//	}
//	
	
//	public  void addResource(Resource resourceIn) {
//		this.worldEntitys.put(key, value)
//	}

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

	public void addImageSheet(String imgID, String FilePath, Dimension tileDims, int tileCount) {
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
	
	public enum MultiTiledImageType {
		border, resourceStructure;
	}
	
	public void updateMultiTiledImage(IsometricTile tile, MultiTiledImageType type) {
		ArrayList<IsometricTile> neighbours = new ArrayList<IsometricTile>();
		if (Game.objectMap.tileExists(new Point(tile.isoPos.x-1, tile.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x-1, tile.isoPos.y)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(tile.isoPos.x, tile.isoPos.y-1))) {neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x, tile.isoPos.y-1)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(tile.isoPos.x+1, tile.isoPos.y))) {neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x+1, tile.isoPos.y)));} else {neighbours.add(null);}
		if (Game.objectMap.tileExists(new Point(tile.isoPos.x, tile.isoPos.y+1))) {neighbours.add(Game.objectMap.getTile(new Point(tile.isoPos.x, tile.isoPos.y+1)));} else {neighbours.add(null);}
		
		boolean hasLeft = false;
		boolean hasRight = false;
		boolean hasUp = false;
		boolean hasDown = false;
		String imageID = "";
		switch(type){ 
			case border:
				if (neighbours.get(0) != null){ if (neighbours.get(0).currentOwner == tile.currentOwner) {
					hasLeft = true;
				} }
				if (neighbours.get(1) != null){ if (neighbours.get(1).currentOwner == tile.currentOwner) {
					hasUp = true;
				} }
				if (neighbours.get(2) != null){ if (neighbours.get(2).currentOwner == tile.currentOwner) {
					hasRight = true;
				} }
				if (neighbours.get(3) != null){ if (neighbours.get(3).currentOwner == tile.currentOwner) {
					hasDown = true;
				} }
				imageID += tile.currentOwner.toString() + "owned";
				break;
			case resourceStructure:
				if (neighbours.get(0) != null){ if (neighbours.get(0).hasEntityOnTile()) { if (neighbours.get(0).entityOnTile instanceof Resource) {
					Resource neighbourResource = (Resource)neighbours.get(0).entityOnTile;
					Resource tileResource = (Resource)tile.entityOnTile;
					if (neighbourResource.resourceType == tileResource.resourceType && neighbourResource.hasStructure()) {
						hasLeft = true;
					} } } }
				if (neighbours.get(1) != null){ if (neighbours.get(1).hasEntityOnTile()) { if (neighbours.get(1).entityOnTile instanceof Resource) {
					Resource neighbourResource = (Resource)neighbours.get(1).entityOnTile;
					Resource tileResource = (Resource)tile.entityOnTile;
					if (neighbourResource.resourceType == tileResource.resourceType && neighbourResource.hasStructure()) {
						hasUp = true;
					} } } }
				if (neighbours.get(2) != null){ if (neighbours.get(2).hasEntityOnTile()) { if (neighbours.get(2).entityOnTile instanceof Resource) {
					Resource neighbourResource = (Resource)neighbours.get(2).entityOnTile;
					Resource tileResource = (Resource)tile.entityOnTile;
					if (neighbourResource.resourceType == tileResource.resourceType && neighbourResource.hasStructure()) {
						hasRight = true;
					} } } }
				if (neighbours.get(3) != null){ if (neighbours.get(3).hasEntityOnTile()) { if (neighbours.get(3).entityOnTile instanceof Resource) {
					Resource neighbourResource = (Resource)neighbours.get(3).entityOnTile;
					Resource tileResource = (Resource)tile.entityOnTile;
					if (neighbourResource.resourceType == tileResource.resourceType && neighbourResource.hasStructure()) {
						hasDown = true;
					} } } }
				Resource r = (Resource)tile.entityOnTile;
				
				imageID += r.resourceType.toString() + "ResourceStructure";
				break;
		}
		
		/*
		Determining which image to use based on neighbours ownership status
		*/
		// 1side down
		if (hasLeft && hasRight && hasUp && !hasDown) {
			imageID += "0";
		}
		// 1side right
		else if (hasLeft && !hasRight && hasUp && hasDown) {
			imageID += "1";
		}
		// 1side left
		else if (!hasLeft && hasRight && hasUp && hasDown) {
			imageID += "2";
		}
		// 1side up
		else if (hasLeft && hasRight && !hasUp && hasDown) {
			imageID += "3";
		}
		// 2side up and left
		else if (!hasLeft && hasRight && !hasUp && hasDown) {
			imageID += "4";
		}
		// 2side down and right
		else if (hasLeft && !hasRight && hasUp && !hasDown) {
			imageID += "5";
		}
		// 2side up and right
		else if (hasLeft && !hasRight && !hasUp && hasDown) {
			imageID += "6";
		}
		// 2side down and left
		else if (!hasLeft && hasRight && hasUp && !hasDown) {
			imageID += "7";
		}
		// 2side up and down
		else if (hasLeft && hasRight && !hasUp && !hasDown) {
			imageID += "13";
		}
		// 2side left and right
		else if (!hasLeft && !hasRight && hasUp && hasDown) {
			imageID += "14";
		}
		// 3side up, down, left
		else if (!hasLeft && hasRight && !hasUp && !hasDown) {
			imageID += "8";
		}
		// 3side up, left, right
		else if (!hasLeft && !hasRight && !hasUp && hasDown) {
			imageID += "9";
		}
		// 3side down, left, right
		else if (!hasLeft && !hasRight && hasUp && !hasDown) {
			imageID += "10";
		}
		// 3side up, down, right
		else if (hasLeft && !hasRight && !hasUp && !hasDown) {
			imageID += "11";
		}
		// 4side
		else if (!hasLeft && !hasRight && !hasUp && !hasDown) {
			imageID += "12";
		}
		// no sides
		else if (hasLeft && hasRight && hasUp && hasDown) {
			imageID += "15";
		}
		switch(type) {
		case border:
			tile.borderImage = imageID;
			break;
		case resourceStructure:
			Resource r = (Resource)tile.entityOnTile;
			r.getStructure().tileImageMap.put(tile, imageID);
			break;
		}
	}

	public Image getImage(String imgID) {
		return this.imageMap.get(imgID);
	}


	public Boolean tileExists(Point pointIn) {
		if (this.worldTiles.containsKey(pointIn.x + ":" + pointIn.y)) {
			return true;
		} else return false;

	}


	public UserInterfaceObject addUIObject(String objectKey, UserInterfaceObject.UIElementType elementType) {
		UserInterfaceObject newElement = new UserInterfaceObject(ObjectType.DEFAULT, elementType);
		newElement.objID = objectKey;
		this.put(objectKey, newElement);
		this.uiObjects.put(objectKey, newElement);
		return newElement;
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

	public Map<String, UserInterfaceObject> getUIObjects() {
		return uiObjects;
	}

	public ArrayList<UserInterfaceObject> getEnabledUIObjects(){
//		Game.userInterface
		return enabledUIObjects;

	}
	public ArrayList<UserInterfaceObject> getUIIndex(int i){
		return Game.userInterface.getZIndex(i);
	}
 
	public Map<String, WorldObject> WorldObjects() {
		return worldObjects;
	}

	public void updateMainDisplayObjects(/* Dimension displayDimension, Point displayPoint */) {

		try {
			Game.sem.acquire();
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

				if (obj.isVisible() && isWithinDisplay(obj.getWorldPosition(),
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

		Game.sem.release();

	}
	
	
	
	public void removeEntity(Entity entityIn) {
//		Game.sem.aq
		this.worldEntitys.remove(entityIn);
	}

	// returns true if at least one corner of a given object is within the Display's
	// current bounds
	public boolean isWithinDisplay(Pair<Dimension, Point> objectBounds, Pair<Dimension, Point> displayBounds) {

		// checking each corner of the object to see if it is within the display's
		// bounds

		// top left corner == x,y

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
