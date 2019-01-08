import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import javafx.scene.paint.Color;

//import javafx.scene.paint.Color;


/**
 *
 */

/**
 * @author Orly
 *
 */
public class Game {

	public static int width;
	public static int height;;
	public static int worldWidth;
	public static int worldHeight;
	public static Graphics graphics;
	public static JFrame window;
	public static Renderer mainGameRenderer;
	public static ObjectMap objectMap;
	public static World gameWorld;
	public static SettingsHandler settingsControl;
	public static final int xOffset = 3;
	public static final int yOffset = 26;

	public enum STATE{
		Menu,
		Exiting,
		Game
	}
	public static STATE currentState;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		settingsControl = new SettingsHandler();
		settingsControl.loadSettings();
		currentState = STATE.Menu;
;
//		worldWidth = tileWidth *isoDims.width;
//		 worldHeight = tileHeight *isoDims.height;
		objectMap = new ObjectMap();
//		Image icon = new ImageIcon("assets/testImage.png").getImage();
//		Image clickableImage = new ImageIcon("assets/click.png").getImage();


		//Loading all image assets
		objectMap.addImage("border", "assets/border.png");
		objectMap.addImage("hover", "assets/hovertile.png");
		objectMap.addImage("cityhover", "assets/hovercity.png");
		objectMap.addImage("click", "assets/click.png");
		objectMap.addImage("roadtile","assets/road.png");
		objectMap.addTileImage("grasstile","assets/grasstiles.png", new Dimension(64,32), 4);
		objectMap.addTileImage("watertile","assets/watertiles.png", new Dimension(64,32), 3);
		objectMap.addTileImage("treetile", "assets/foresttiles.png", new Dimension(64,40), 3);
		objectMap.addTileImage("citytile", "assets/City1.png", new Dimension(192,112), 1);
		objectMap.addImage("placeholder", "assets/placeholder.png");
		objectMap.addImage("teststructure", "assets/structuretest.png");

		gameWorld = new World();
		gameWorld.initialiseTileMap();


		window = new JFrame("Draggable");
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = new Dimension (width, height);


		window.setPreferredSize(dim);
		window.setMaximumSize(dim);
		window.setMinimumSize(dim);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setBackground(Color.DARK_GRAY);

		graphics = window.getGraphics();
		Renderer mainGameRenderer = new Renderer("mainGameRenderer", window);
		Font gameFont = new Font("Arial", Font.BOLD, 12);
		graphics.setFont(gameFont);

		//Border
		GameObject border = new GameObject(ObjectType.DEFAULT);
		objectMap.addObject(ObjectType.DEFAULT,  "border", border);
		objectMap.getObject("border").setProperties(new Dimension(1600,900), new Point(0,0),"border");

		//Menu button
		GameObject menuButton = new GameObject(ObjectType.MAINMENU);
		objectMap.addObject(ObjectType.MAINMENU, "menubutton", menuButton);
		objectMap.getObject("menubutton").setProperties(new Dimension(146,75), new Point(150,600), "click",true,"mainmenustart");

		//Mouse data text objects
		TextObject globalMousePosText = new TextObject(ObjectType.DEFAULT,gameFont);
		globalMousePosText.setProperties("Global mouse position:",Color.WHITE, new Point(xOffset+5, 45));
		objectMap.addObject(ObjectType.DEFAULT, "globalMousePosText", globalMousePosText);
		
		TextObject worldMousePosText = new TextObject(ObjectType.DEFAULT,gameFont);
		worldMousePosText.setProperties("World mouse position:",Color.WHITE, new Point(xOffset+5, 65));
		objectMap.addObject(ObjectType.DEFAULT, "worldMousePosText", worldMousePosText);
		
		TextObject isoMousePosText = new TextObject(ObjectType.DEFAULT,gameFont);
		isoMousePosText.setProperties("Iso mouse position:",Color.WHITE, new Point(xOffset+5, 85));
		objectMap.addObject(ObjectType.DEFAULT, "isoMousePosText", isoMousePosText);
		
		
//		//Testing text
//		JLabel testText = new JLabel("testing");
//		testText.setSize(new Dimension(100,100));
//		testText.setForeground(Color.white);
//		window.add(testText);



//		GameObject test = new GameObject(ObjectType.DEFAULT);
//		objectMap.addObject(ObjectType.DEFAULT,  "test", border);
//		objectMap.getObject("test").setProperties(new Dimension(300,100), new Point(500,500),"citytile0");
		Structure city0;
		ArrayList<IsometricTile> structureTiles = new ArrayList<IsometricTile>();
		structureTiles.add(objectMap.getTile(new Point(17,38)));
		structureTiles.add(objectMap.getTile(new Point(18,38)));
		structureTiles.add(objectMap.getTile(new Point(19,38)));
		structureTiles.add(objectMap.getTile(new Point(17,37)));
		structureTiles.add(objectMap.getTile(new Point(18,37)));
		structureTiles.add(objectMap.getTile(new Point(19,37)));
		structureTiles.add(objectMap.getTile(new Point(17,36)));
		structureTiles.add(objectMap.getTile(new Point(18,36)));
		structureTiles.add(objectMap.getTile(new Point(19,36)));
		city0 = new Structure(structureTiles);
		city0.setProperties(new Dimension(192,96), new Point(500,500), "citytile0", true, "city0");
		objectMap.addObject(ObjectType.WORLD,"city", city0);
		objectMap.addWorldObject("city0", city0);
		objectMap.addStructure("city0",city0,48);

		Structure city1;
		structureTiles.clear();
		structureTiles.add(objectMap.getTile(new Point(40,19)));
		structureTiles.add(objectMap.getTile(new Point(41,19)));
		structureTiles.add(objectMap.getTile(new Point(42,19)));
		structureTiles.add(objectMap.getTile(new Point(40,18)));
		structureTiles.add(objectMap.getTile(new Point(41,18)));
		structureTiles.add(objectMap.getTile(new Point(42,18)));
		structureTiles.add(objectMap.getTile(new Point(40,17)));
		structureTiles.add(objectMap.getTile(new Point(41,17)));
		structureTiles.add(objectMap.getTile(new Point(42,17)));
		city1 = new Structure(structureTiles);
		city1.setProperties(new Dimension(192,96), new Point(500,500), "citytile0", true, "city1");
		objectMap.addObject(ObjectType.WORLD,"city", city1);
		objectMap.addWorldObject("city1", city1);
		objectMap.addStructure("city1",city1,48);

		//Test unit
		Unit cube;
		cube = new Unit(new Point(4,6));
		objectMap.addObject(ObjectType.WORLD, "placeholder", cube);
		objectMap.addStructure("placeholder", cube,8);
		cube.setProperties(new Dimension(64,32), new Point(600,200),"placeholder");
		Game.gameWorld.addTickingObject(cube);

		//Initialise input handler
		InputHandler inputControl = new InputHandler();
		window.getContentPane().addMouseListener(inputControl);
		window.getContentPane().addMouseMotionListener(inputControl);


//		gameWorld.setTile(new Point(0,1), IsometricTile.TILESET.grass);
		objectMap.updateMainDisplayObjects();
		mainGameRenderer.start();



//		System.out.println(mainGameRenderer.toIsometric(new Point(650,100)));
//		System.out.println(mainGameRenderer.toGrid(new Point(1900,0)));
//		System.out.println("IsoDims: " +gameWorld.isoDims);
//		System.out.println("WorldDims: " +gameWorld.worldDims);


//		Font testFont = new Font("Arial",11,11);
//		ArrayList<Point> testArray = new ArrayList<Point>();
//		testArray.add(new Point(14,18));
//		testArray.add(new Point(14,17));
//		testArray.add(new Point(15,19));
//		testArray.add(new Point(15,18));
//		testArray.add(new Point(15,17));
//		testArray.add(new Point(16,19));
//		testArray.add(new Point(16,18));
//		testArray.add(new Point(16,17));

//		Structure testStructure = new Structure(testArray,new Point(14,19));
		// THIS CODE MAKES THE GAME UNPLAYABLE SOMEHOW AND ITS 3AM SO IM WORKING IT OUT LATER
//		objectMap.addWorldStructure(Structure.StructureType.city, new Point(14,19), testArray);


		while(true) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(currentState == Game.STATE.Game) {
				gameWorld.tick();
				//test code for ticking a TextObject
//				globalMousePosText.setText(Integer.toString(testInc));

				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
