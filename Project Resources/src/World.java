import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Jasper
 *
 */
public class World {

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
//	public Map<String,Image> imageAssetMap;


	World(Dimension worldDims)  {
		panelDims = new Dimension(Game.width-200,Game.height-200);
		panelPoint = new Point(100,100);
		worldPoint = new Point(600,600);
		this.worldDims = worldDims;

	}

	/**
	 * creates a worldobject, laods its image from the map and adds it to the object map. Returns true if succesful
	 * @return
	 */
	public boolean newTileObject() {
		return false;
	}
	

	public void initialiseTileMap() {
		
		Point nextTileWorldCoords = new Point(400,400);
//		System.out.println(nextTileWorldCoords.x);
		BufferedReader br = null;
		String line = "";
		String delim = ",";
		int renderConstant = 1030;
		int tileX = renderConstant;
		int tileY = -1030;
		
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
		isoDims = new Dimension(tileCount/j,j);
		System.out.println(isoDims);
		
			
		
	}
	
	public void setTile(Point isoPos,IsometricTile.TILESET type) {
		Game.objectMap.getTile(isoPos).changeTileset(type);
	}

	/**
	 *  Repositions every object in the main display. Called after the display is offset
	 */
	public void updateDisplay() {
		Game.objectMap.updateMainDisplayObjects();

		for(Map.Entry<String,WorldObject> obj : Game.objectMap.getMainDisplayObjects().entrySet()) {
			obj.getValue().setPosition(worldPoint,panelPoint);
		}

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
}
