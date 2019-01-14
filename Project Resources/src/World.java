import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Jasper
 *
 */
public class World {

	
	//WORLD CONSTANTS
	public static int tileWidth = 64;
	public static int tileHeight = 32;
	public Double tileDistance = 10.0; // Distance to traverse between two tiles
	
	
	// panelDims = dimensions of the main display (the portion of the world currently being rendered)
	// panelPoint = true location of the top left corner of the main display - (100, 100) relative to the ContentPane
	// worldDims = dimensions of the entire world, including parts not currently being rendered
	// worldPoint = the point within the world which is currently corresponding to panelPoint
	public Dimension panelDims;
	public Point panelPoint;
	public Dimension worldDims;
	public Point worldPoint;
	public Point staticWorldPoint;
	public Dimension isoDims;
	public int tileCount = 0;
	public ArrayList<WorldObject> tickingObjects;
	
	Pair<Dimension,Point> isometricPlane;
	public Queue<Pair<String,Point>> structureList;
//	public Map<String,Image> imageAssetMap;
	public HashMap<Point,Point> worldToIsoTable;


	public World()  {
		worldToIsoTable = new HashMap<Point,Point>();
		structureList = new PriorityQueue<Pair<String,Point>>();
		this.isoDims = initialiseTileMap();
		
		tickingObjects = new ArrayList<WorldObject>();
		//This needs to be changed to accommodate different borders and resolutions
		panelDims = new Dimension(Game.width-200,Game.height-64);
		panelPoint = new Point(0,34);
		worldPoint = new Point(600,600);
		
		
		
		this.worldDims = new Dimension(isoDims.width*tileWidth+ 5*tileWidth,isoDims.height*tileHeight -2* tileHeight);
		initialiseStructures();
	}
	
	//Called every at every increment of time in the game
	void tick() {
		if(!tickingObjects.isEmpty()) {
			for(WorldObject obj : tickingObjects) {
				obj.tickAction();
			}
			updateDisplay();
		}
	}

	/**
	 * creates a worldObject, loads its image from the map and adds it to the object map. Returns true if successful
	 * @return
	 */
	public boolean newTileObject() {
		return false;
	}
	

	public Dimension initialiseTileMap() {
		
		Point nextTileWorldCoords = new Point(400,400);
//		System.out.println(nextTileWorldCoords.x);
		BufferedReader br = null;
		String line = "";
		String delim = ",";
		
		// renderConstant and tileY (960) use the magic value to position the isometric plane
		int renderConstant = 960;
		int tileX = renderConstant;
		int tileY = -960;
		
		int j = 0; //Used for calculating isoDims
		
		// Iterates through a .csv file and checks each field for a string that matches a known tile type.
		try {
			br = new BufferedReader(new FileReader("tilemap.csv"));
			while((line = br.readLine()) != null){
				String[] tileLine = line.split(delim);
				
				IsometricTile.TILESET tileType = null;
				for(int i = 0; i < tileLine.length; i++) {


					if(tileLine[i].compareTo("g") == 0) {
						tileType = IsometricTile.TILESET.grass;
					}else if(tileLine[i].compareTo("w") == 0){
						tileType = IsometricTile.TILESET.water;
					}else if (tileLine[i].compareTo("f") == 0) {
						tileType = IsometricTile.TILESET.trees;
					}else if (tileLine[i] != null) {
//						structureList.add(new Pair<String,Point>(tileLine[i],new Point(i,j)));
					}
					
					
					if(tileType != null) {
						nextTileWorldCoords = new Point(tileX, tileY);
						Game.objectMap.addWorldTile(nextTileWorldCoords,tileType,new Point(i,j));
						
//						System.out.println(nextTileWorldCoords);
						tileCount ++;

					}
					tileX += 32;

					tileType = null;
				}
				tileY +=32;
				tileX = renderConstant;

				j++;
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.updateDisplay();
		
		
		

		
		
		return (new Dimension(tileCount/j,j));	
	}
	
	public ArrayList<String> populateNameList() {
		ArrayList<String> nameList = new ArrayList<String>();
		BufferedReader br;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("citynamelist.csv"), "UTF-8"));
			while((line = br.readLine()) != null) {
				nameList.add(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nameList;
	}
	
	public void initialiseStructureMap() {
		Random rn = new Random();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("structuremap.csv"));
			String line = "";
			String delim = ",";
			int y = 0;
			int numStructures = 0;
			while((line = br.readLine()) != null){
				String[] tileLine = line.split(delim);
				for (int x = 0; x < tileLine.length; x++) {
					if (tileLine[x].equals("")) {
						continue;
					} else if (tileLine[x].equals("C")) {
						ArrayList<IsometricTile> structureTiles = new ArrayList<IsometricTile>();
						structureTiles.add(Game.objectMap.getTile(new Point(x-1,y+1)));
						structureTiles.add(Game.objectMap.getTile(new Point(x-1,y-1)));
						structureTiles.add(Game.objectMap.getTile(new Point(x,y)));
						structureTiles.add(Game.objectMap.getTile(new Point(x,y-1)));
						structureTiles.add(Game.objectMap.getTile(new Point(x+1,y-1)));
						structureTiles.add(Game.objectMap.getTile(new Point(x-1,y)));
						structureTiles.add(Game.objectMap.getTile(new Point(x+1,y)));
						structureTiles.add(Game.objectMap.getTile(new Point(x,y+1)));
						structureTiles.add(Game.objectMap.getTile(new Point(x+1,y+1)));
						
						String name = Game.nameList.get(rn.nextInt(Game.nameList.size()));
						City newCity = new City(structureTiles, name);
						newCity.setProperties(new Dimension(192,96), new Point(500,500), "citytile" + Integer.toString(rn.nextInt(3)), true, "city" + Integer.toString(numStructures));
						Game.objectMap.addStructure("city" + Integer.toString(numStructures), newCity, 48);
						numStructures++;
						
					} else if (tileLine[x].equals("R")) {
//						ArrayList<IsometricTile> structureTiles = new ArrayList<IsometricTile>();
//						structureTiles.add(Game.objectMap.getTile(new Point(x,y)));
//						Road newRoad = new Road(structureTiles);
//						newRoad.setProperties(new Dimension(64,32), new Point(0,0), "road10", false, "road" + Integer.toString(numStructures));
//						Game.objectMap.addStructure("road" + Integer.toString(numStructures), newRoad, 0);
						Game.objectMap.getTile(new Point(x,y)).setRoad(true);
						numStructures++;
					}
					
				}
				y++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void addTickingObject(WorldObject tickingObj) {
		this.tickingObjects.add(tickingObj);
	}
	
	public void setTile(Point isoPos,IsometricTile.TILESET type) {
		Game.objectMap.getTile(isoPos).changeTileset(type);
	}
	
	public void initialiseStructures() {
//		while(!structureList.isEmpty()) {
//			Pair<String,Point> structure = structureList.poll();
//			if(structure.getKey().equals("c1")) {
//				Structure newStructure = new Structure(structure.getValue(),worldToIsoTable.get(structure.getValue()));
//
//				newStructure.setProperties(new Dimension(64,32),new Point(0,0),"citytile1", true);
//				newStructure.dim = new Dimension(64,32);
////				Game.objectMap.WorldObjects().put("c1", newStructure);
//			}
//			
//		}
	}

	/**
	 *  Repositions every object in the main display. Called after the display is offset
	 */
	public void updateDisplay() {
//		try {
//			Game.mainGameRenderer.semaphore.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Game.objectMap.updateMainDisplayObjects();

		for(WorldObject obj : Game.objectMap.getMainDisplayTiles()) {
			obj.setPosition(worldPoint,panelPoint);
		}
		for(Structure obj : Game.objectMap.getMainDisplayStructures()) {
			obj.setPosition(worldPoint,panelPoint);
		}
		
//		Game.mainGameRenderer.semaphore.release();

	}
	public Pair<Dimension,Point> getMainDisplayCoords() {
		return new Pair<Dimension,Point>(this.panelDims,this.panelPoint);
	}


	/**
	 * This function is called by InputHandler when the mouse is dragged, it moves the display view of the world.
	 *
	 * @param mousePressPos : the position the mouse was pressed in, used as the world anchor for offsetting display
	 * @param mousePos : the position of the mouse when function is called
	 */
	public void offsetDisplay(Point mousePressPos, Point mousePos) {
		if(this.staticWorldPoint == null) {
			this.staticWorldPoint = this.worldPoint.getLocation();
		}

		this.worldPoint.y = staticWorldPoint.y + ((( mousePressPos.y - mousePos.y)) );
		this.worldPoint.x = staticWorldPoint.x + ((( mousePressPos.x - mousePos.x)) );
		if(!withinBounds(this.worldPoint)) {
			if(worldPoint.y < 0) {
				worldPoint.y = 0;
			}
			if(worldPoint.x < 0) {
				worldPoint.x = 0;
			}
			if(worldPoint.x + panelDims.width > worldDims.width) {
				worldPoint.x = worldDims.width-panelDims.width;
			}
			if(worldPoint.y + panelDims.height > worldDims.height) {
				worldPoint.y = worldDims.height-panelDims.height;
			}


		}

		updateDisplay();

	}



	public boolean withinBounds(Point worldPointIn) {
		if(worldPointIn.y > 0 && worldPointIn.x > 0 && worldPointIn.x+panelDims.width < worldDims.width && worldPointIn.y+panelDims.height < worldDims.height) {
			return true;
		}
		return false;
	}

	public Point getWorldPosition(Point mousePointIn) {
		return new Point((this.worldPoint.x +  (mousePointIn.x - panelPoint.x)),((this.worldPoint.y +  (mousePointIn.y - panelPoint.y))));

	}

	
	//Checks for all walkable neighbours of an isometrictile
	// returns an ArrayList in the following order: Left,Up,Right,Down
	public ArrayList<IsometricTile> getNeighbours(IsometricTile centreTile){
		ArrayList<IsometricTile> returnList = new ArrayList<IsometricTile>();
		//Check up and to the right for neighbour
		if(centreTile.getIsoPoint().x > 0) {
			if(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x - 1, centreTile.getIsoPoint().y)).isWalkable()) {
				returnList.add(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x - 1, centreTile.getIsoPoint().y)));
			}
		}
		if(centreTile.getIsoPoint().y > 0) {
			if(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x , centreTile.getIsoPoint().y- 1)).isWalkable()) {
				returnList.add(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x , centreTile.getIsoPoint().y- 1)));
			}
		}
		if(centreTile.getIsoPoint().x <= this.isoDims.getWidth()-2) {
			if(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x + 1, centreTile.getIsoPoint().y)).isWalkable()) {
				returnList.add(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x + 1, centreTile.getIsoPoint().y)));
			}
		}
		if(centreTile.getIsoPoint().y <= this.isoDims.getHeight()-2) {
			if(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x, centreTile.getIsoPoint().y + 1)).isWalkable()) {
				returnList.add(Game.objectMap.getTile(new Point(centreTile.getIsoPoint().x, centreTile.getIsoPoint().y + 1)));
			}

		}
//		for(IsometricTile tile : returnList) {
//			if(tile.walkable == false) {
//				returnList.remove(tile);
//			}
//		}
//		System.out.println("Returnlist:");
//		for(IsometricTile it : returnList){
//			System.out.println(it.getIsoPoint());
//		}
//		System.out.println(returnList.size());
		return returnList;
		
	}


	private class PathQueueComparator implements Comparator<Pair<Double,IsometricTile>> {
			
		public int compare(Pair<Double,IsometricTile> o1, Pair<Double,IsometricTile> o2) {
			Double d1 = o1.getKey();
			Double d2 = o2.getKey();

			if (d1 >= d2) {
				return 1;
			} else  {
				return -1;
			}
		}
	}
	
	
	
	public ArrayList<Point> getPathBetween(Point tilePosStart, Point tilePosEnd){
		ArrayList<Point> returnList = new ArrayList<Point>();
		if(tileCount == 0) {
			System.out.println("Empty tile list");
			return returnList;
		}
		
		//Stores each tile that has been discovered with the shortest distance to reach it and the point of the tile that path comes from
		Map<IsometricTile,Pair<Double,Point>> distanceMap = new HashMap<IsometricTile,Pair<Double,Point>>();
		//Queue of tiles for processing sorted by a heuristic double
		Queue<Pair<Double,IsometricTile>> queuedTiles  = new PriorityQueue<Pair<Double,IsometricTile>>(new PathQueueComparator());
		
		
		//Add initial tile to queue and distance map
		queuedTiles.add(new Pair<Double,IsometricTile>(0.0,Game.objectMap.getTile(tilePosStart)));
		distanceMap.put(Game.objectMap.getTile(tilePosStart), new Pair<Double,Point>(0.0,tilePosStart));
		
		
		while(!queuedTiles.isEmpty()) {

			Pair<Double,IsometricTile> currentEntry = queuedTiles.poll();
			
			//Iterate through walkable neighbours of currentEntry
			for(IsometricTile tile : getNeighbours(currentEntry.getValue())) {
				tileDistance = 10.0;
				if(tile == null) {
					System.out.println("NULL TILE");
				}
				if(tile.tileset == currentEntry.getValue().tileset) {
					if(!(tile.structureOnTile == null)) {
						if (tile.hasRoad()) {
							tileDistance = 5.0;
						}
						
					}
				}

				
				//Check if tile has been visited before
				if(distanceMap.containsKey(tile)) {
					//Check if shortest distance to tile is greater than traveling to the tile from currentEntry. If so update distanceMap
					if(distanceMap.get(tile).getKey() > distanceMap.get(currentEntry.getValue()).getKey() + tileDistance) {
						distanceMap.put(tile, new Pair<Double,Point>(distanceMap.get(currentEntry.getValue()).getKey() + tileDistance,currentEntry.getValue().getIsoPoint()));
						queuedTiles.add(new Pair<Double,IsometricTile>(
								(tileDistance * (Math.abs(tile.getIsoPoint().getX() - tilePosEnd.getX() )) + (Math.abs(tile.getIsoPoint().getY() - tilePosEnd.getY())))
								,tile));
					}
				}else {
					distanceMap.put(tile, new Pair<Double,Point>(distanceMap.get(currentEntry.getValue()).getKey() + tileDistance,currentEntry.getValue().getIsoPoint()));
					queuedTiles.add(new Pair<Double,IsometricTile>(
							(tileDistance * (Math.abs(tile.getIsoPoint().getX() - tilePosEnd.getX() )) + (Math.abs(tile.getIsoPoint().getY() - tilePosEnd.getY())))
							,tile));
				}
				
				if(tile.isoPos.equals(tilePosEnd)) {
					System.out.println("Found a path. Distance: " + distanceMap.get(tile).getKey());
					Point routePoint = tilePosEnd;
					while(!routePoint.equals(tilePosStart)) {
						routePoint = distanceMap.get(Game.objectMap.getTile(routePoint)).getValue();
						returnList.add(routePoint);
					}
					return returnList;
				}
				
	
			}
			
			
		}
		
		
		
		
		
		System.out.println("No path found");
		return returnList;
	}
	

}
