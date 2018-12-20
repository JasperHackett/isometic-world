import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class GameObject {
	
	private static Dimension dim; //Dimensions of object
	private static Point pos; //Top left corner of object
	private static Image objectImage; //test for a single image object
	
	public void setProperties(Dimension dimIn, Point posIn, Image imageIn) {
		this.dim = dimIn;
		this.pos = posIn;
		this.objectImage = imageIn;
	}
	
	public void render(Graphics g) {
		g.drawImage(objectImage, pos.x, pos.y, null);
	}

}
