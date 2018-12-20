import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

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

	public static final int width = 1600;
	public static final int height = 900;
	public static Graphics graphics;
	public static JFrame window;
	public static Renderer mainGameRenderer;
	public static ObjectMap worldObjects;
	public static ObjectMap otherObjects;
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		worldObjects = new ObjectMap();
		otherObjects = new ObjectMap();
		GameWindow mainGameWindow = new GameWindow();
//		Image icon = new ImageIcon("assets/testImage.png").getImage();
		Image clickableImage = new ImageIcon("assets/click.png").getImage();
		Image borderImage = new ImageIcon("assets/border.png").getImage();

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
		Renderer mainGameRenderer = new Renderer("mainGameRenderer",window);

		GameObject border = new GameObject();
		otherObjects.put("border", border);
		otherObjects.getObject("border").setProperties(new Dimension(1600,900), new Point(0,0), borderImage,false);

		//Test code
//		GameObject testObject = new GameObject();
//		objectMap.put("thing", testObject);
//		objectMap.getObject("thing").setProperties(new Dimension(800,690),new Point(0,0),icon,true);
//
		GameObject clickable = new GameObject();

		otherObjects.put("clickable", clickable);
		otherObjects.getObject("clickable").setProperties(new Dimension(159,100),new Point(1500,0),clickableImage,true);





//		objectMap.get("thing").setProperties(new Dimension(0,0),new Point(0,0),icon);


		InputHandler inputControl = new InputHandler();
		window.getContentPane().addMouseListener(inputControl);
		window.getContentPane().addMouseMotionListener(inputControl);

		mainGameRenderer.start();







	}




}
