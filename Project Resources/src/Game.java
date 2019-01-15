import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

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
	public static int height;
	public static int worldWidth;
	public static int worldHeight;
//	public static Menu gameMenu;
	public static Graphics graphics;
	public static JFrame window;
	public static Renderer mainGameRenderer;
	public static InterfaceController userInterface = new InterfaceController();
	public static ObjectMap objectMap;
	public static World gameWorld;
	public static ArrayList<String> nameList;
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
//		mainHUD.displayCityOnHUD();

		settingsControl = new SettingsHandler();
		settingsControl.loadSettings();
		currentState = STATE.Menu;
//		worldWidth = tileWidth *isoDims.width;
//		 worldHeight = tileHeight *isoDims.height;
		objectMap = new ObjectMap();
//		Image icon = new ImageIcon("assets/testImage.png").getImage();
//		Image clickableImage = new ImageIcon("assets/click.png").getImage();



		//Loading all image assets
//		objectMap.addImage("border", "assets/border.png");
		objectMap.addImage("border", "assets/border_draft.png");
		objectMap.addImage("uibuttonsmall", "assets/uibutton1.png");
		objectMap.addImage("hover", "assets/hovertile.png");
		objectMap.addImage("cityhover", "assets/hovercity.png");
		objectMap.addImage("click", "assets/click.png");
		objectMap.addImage("roadtile","assets/road.png");
		objectMap.addTileImage("grasstile","assets/grasstiles.png", new Dimension(64,32), 4);
		objectMap.addTileImage("watertile","assets/watertiles.png", new Dimension(64,32), 3);
		objectMap.addTileImage("treetile", "assets/foresttiles.png", new Dimension(64,40), 3);
		objectMap.addTileImage("citytile", "assets/City1.png", new Dimension(192,112), 3);
		objectMap.addImage("cube", "assets/placeholder.png");
		objectMap.addImage("teststructure", "assets/structuretest.png");
		objectMap.addImage("redOwnedTile", "assets/redOwnedTile.png");
		objectMap.addImage("hudbutton01", "assets/hudbutton01.png");
		objectMap.addImage("menuButton1", "assets/menuButton1.png");
		objectMap.addImage("menuBackground", "assets/menuBackground.png");
		objectMap.addTileImage("road", "assets/roadTiles.png", new Dimension(64,32), 11);
		objectMap.addTileImage("redowned", "assets/redBorder.png", new Dimension(64,32), 16);
		objectMap.addTileImage("blueowned", "assets/blueBorder.png", new Dimension(64,32), 16);
		objectMap.addFont("smallbuttonfont", "Calibri",10);
		Dimension dim = new Dimension (width, height);
		window = new JFrame("Draggable");
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setPreferredSize(dim);
		window.setMaximumSize(dim);
		window.setMinimumSize(dim);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setBackground(Color.DARK_GRAY);

		graphics = window.getGraphics();
		Renderer mainGameRenderer = new Renderer("mainGameRenderer", window);
//		Font gameFont = new Font("Arial", Font.PLAIN,11);
		objectMap.addFont("primarygamefont", "Arial", 11);
		graphics.setFont(objectMap.getFont("primarygamefont"));


		gameWorld = new World();
		nameList = gameWorld.populateNameList();
		gameWorld.initialiseTileMap();
		gameWorld.initialiseEntityMap();
//		gameMenu = new Menu();
//		gameWorld.



//		UserInterfaceObject testButton = new UserInterfaceElement(ObjectType.DEFAULT,UserInterfaceElement.UIElementType.SMALL,new Point(400,400),"newgame");
		userInterface.createUIContainer("mainmenu",new Point(200,600), new Point(0,50));
		userInterface.addInterfaceObject(UserInterfaceObject.UIElementType.SMALLTEXT,"mainmenu", "newgamebutton","newgame","Start");
		userInterface.addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu", "exitbutton","exit");
		userInterface.enableInterfaceContainer("mainmenu");
//		objectMap.put("testbutton", testButton);
//		objectMap.addObject(ObjectType.DEFAULT, "testbutton", testButton);





		//Border
		GameObject border = new GameObject(ObjectType.DEFAULT);
		Game.objectMap.addObject(ObjectType.DEFAULT,  "border", border);
		Game.objectMap.getObject("border").setProperties(new Dimension(1600,900), new Point(0,0),"border");


//		//UI Background
//		GameObject uibackground = new GameObject(ObjectType.DEFAULT);
//		objectMap.addObject(ObjectType.DEFAULT,  "uibackground", uibackground);
//		objectMap.getObject("uibackground").setProperties(new Dimension(200,300), new Point(1289,110),"uibackground");

		//Mouse data text objects
		TextObject globalMousePosText = new TextObject(ObjectType.DEFAULT,objectMap.getFont("primarygamefont"), Color.WHITE);
		globalMousePosText.setProperties("Global mouse position:",Color.WHITE, new Point(xOffset+5, 70));
		objectMap.addObject(ObjectType.DEFAULT, "globalMousePosText", globalMousePosText);

		TextObject worldMousePosText = new TextObject(ObjectType.DEFAULT,objectMap.getFont("primarygamefont"), Color.WHITE);
		worldMousePosText.setProperties("World mouse position:",Color.WHITE, new Point(xOffset+5, 85));
		objectMap.addObject(ObjectType.DEFAULT, "worldMousePosText", worldMousePosText);

		TextObject isoMousePosText = new TextObject(ObjectType.DEFAULT,objectMap.getFont("primarygamefont"), Color.WHITE);
		isoMousePosText.setProperties("Iso mouse position:",Color.WHITE, new Point(xOffset+5, 100));
		objectMap.addObject(ObjectType.DEFAULT, "isoMousePosText", isoMousePosText);


		//Test unit
		Unit cube;
		cube = new Unit(new Point(4,6));
		objectMap.addObject(ObjectType.WORLD, "placeholder", cube);
		objectMap.addEntity("placeholder", cube,8);
		cube.setProperties(new Dimension(64,32), new Point(600,200),"cube");
		cube.setDestination(new Point(41,55));
		Game.gameWorld.addTickingObject(cube);


		Unit cube2;
		cube2 = new Unit(new Point(20,37));
		objectMap.addObject(ObjectType.WORLD, "placeholder2", cube2);
		objectMap.addEntity("placeholder2", cube2,8);
		cube2.setProperties(new Dimension(64,32), new Point(600,200),"cube");
		objectMap.getTile(new Point(41,19)).getEntityOnTile().getClosestNeighbour(cube2.isoPoint);
		cube2.setDestination(new Point(41,21));
		Game.gameWorld.addTickingObject(cube2);

		//Initialise input handler
		InputHandler inputControl = new InputHandler();
		window.getContentPane().addMouseListener(inputControl);
		window.getContentPane().addMouseMotionListener(inputControl);


//		gameWorld.setTile(new Point(0,1), IsometricTile.TILESET.grass);
		objectMap.updateMainDisplayObjects();
		mainGameRenderer.start();


		// testing tile ownership
		for (int x = 36; x < 54; x++) {
			for (int y = 15; y < 27; y++) {
				objectMap.getTile(new Point(x,y)).setOwner(IsometricTile.OWNERSET.red);
			}
		}
		for (int x = 25; x < 36; x++) {
			for (int y = 15; y < 27; y++) {
				objectMap.getTile(new Point(x,y)).setOwner(IsometricTile.OWNERSET.blue);
			}
		}
		objectMap.getTile(new Point(45,27)).setOwner(IsometricTile.OWNERSET.red);
		objectMap.getTile(new Point(40,26)).setOwner(IsometricTile.OWNERSET.none);
		objectMap.getTile(new Point(35,20)).setOwner(IsometricTile.OWNERSET.red);
		objectMap.getTile(new Point(36,24)).setOwner(IsometricTile.OWNERSET.none);
		objectMap.getTile(new Point(45,14)).setOwner(IsometricTile.OWNERSET.red);
		objectMap.getTile(new Point(40,15)).setOwner(IsometricTile.OWNERSET.none);
		objectMap.getTile(new Point(54,20)).setOwner(IsometricTile.OWNERSET.red);
		objectMap.getTile(new Point(53,24)).setOwner(IsometricTile.OWNERSET.none);
		objectMap.getTile(new Point(36,21)).setOwner(IsometricTile.OWNERSET.blue);
		objectMap.getTile(new Point(37,21)).setOwner(IsometricTile.OWNERSET.blue);
		objectMap.getTile(new Point(37,20)).setOwner(IsometricTile.OWNERSET.blue);

//
		System.out.println(objectMap.getTile(new Point(18,15)).getEntityOnTile().getClosestNeighbour(new Point(15,18)));
		
		
		
		// temporary "tick" loop
		while(true) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(currentState == Game.STATE.Game) {
				gameWorld.tick();


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
