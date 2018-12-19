import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Orly
 *
 */
public class GameWindow {
	
	public static final int width = 1600;
	public static final int height = 900;
	public static Graphics graphics;
	public static JFrame window;
	
	public GameWindow(){ 
		window = new JFrame("Draggable");
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = window.getContentPane();
		Dimension dim = new Dimension (width, height);
		
		window.setPreferredSize(dim);
		window.setMaximumSize(dim);
		window.setMinimumSize(dim);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		graphics = window.getGraphics();
		try {
			graphics.drawImage(new ImageIcon("testImage.png").getImage(), 0, 0, null);
		} catch (Exception e) {
			System.out.println("Unable to find file");
		}
//		window.repaint();
		graphics.drawImage(new ImageIcon("testImage.png").getImage(), 10, 10, null);
	}
	
	

	public void renderFrame() {
		graphics.drawImage(new ImageIcon("testImage.png").getImage(), 10, 10, null);
		window.repaint();
//		graphics.clear

	}
	

}
