import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


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
		gameWorld = new World(new Dimension(worldWidth,worldHeight));
		objectMap = new ObjectMap();
//		Image icon = new ImageIcon("assets/testImage.png").getImage();
//		Image clickableImage = new ImageIcon("assets/click.png").getImage();
		
		objectMap.addImage("border", "assets/border.png");
//		objectMap.addImage("tile", "assets/grasstiles.png");
		objectMap.addImage("click", "assets/click.png");
		objectMap.addTileImage("grasstile","assets/grasstiles.png", new Dimension(64,32), 3);
		objectMap.addTileImage("watertile","assets/watertiles.png", new Dimension(64,32), 3);
		
//		Image borderImage = new ImageIcon("assets/border.png").getImage();
//		Image icon = new ImageIcon("assets/testImage.png").getImage();
//		Image background = new ImageIcon("assets/background_draggable.png").getImage();
//		Image tile = new ImageIcon("assets/grasstiles.png").getImage();
//		
//		BufferedImage grasstiles = null;
//		try {
//			grasstiles = ImageIO.read(new File("assets/watertiles.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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

		graphics = window.getGraphics();
		Renderer mainGameRenderer = new Renderer("mainGameRenderer", window);
		
		//Border
		GameObject border = new GameObject(ObjectType.DEFAULT);
		objectMap.addObject(ObjectType.DEFAULT,  "border", border);
		objectMap.getObject("border").setProperties(new Dimension(1600,900), new Point(0,0),"border");
		
		//Menu button
		GameObject menuButton = new GameObject(ObjectType.MAINMENU);
		objectMap.addObject(ObjectType.MAINMENU, "menubutton", menuButton);
		objectMap.getObject("menubutton").setProperties(new Dimension(146,75), new Point(150,600), "click",true,"mainmenustart");

		
		gameWorld.initialiseTileMap();
		objectMap.updateMainDisplayObjects();

		
		//Initialise input handler
		InputHandler inputControl = new InputHandler();
		window.getContentPane().addMouseListener(inputControl);
		window.getContentPane().addMouseMotionListener(inputControl);

		
//		gameWorld.setTile(new Point(1,1), IsometricTile.TILESET.grass);
		mainGameRenderer.start();

	

		System.out.println(mainGameRenderer.toIsometric(new Point(650,100)));
		System.out.println(mainGameRenderer.toGrid(new Point(1930,0)));
		





	}




}
