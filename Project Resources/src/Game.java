import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GameWindow mainGameWindow = new GameWindow();
		Image icon = new ImageIcon("testImage.png").getImage();
	
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
		
		
		//Test code
		GameObject testObject = new GameObject();
		mainGameRenderer.addObject("thing", testObject);
		mainGameRenderer.getObject("thing").setProperties(new Dimension(0,0),new Point(0,0),icon);
		
		InputHandler inputControl = new InputHandler();
		window.addMouseListener(inputControl);
		window.addMouseMotionListener(inputControl);

		mainGameRenderer.start();

		int i = 0;
		while(true) {
			try {
				Thread.sleep(100);
				mainGameRenderer.getObject("thing").setProperties(new Dimension(0,0),new Point(i,i),icon);
				i++;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		

	}		
	
	
	

}
