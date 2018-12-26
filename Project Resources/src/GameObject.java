import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javafx.util.Pair;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class GameObject {
	
	protected Dimension dim; //Dimensions of object
	protected Point coords; //Top left corner of object
	private String objectImage; //test for a single image object
	private boolean clickable;
	private boolean isVisible;
	public ObjectType type;
	public String clickTag;
	
	public GameObject(ObjectType type) {
		this.type = type;
	}
	
	public void setProperties(Dimension dimIn, Point posIn, String imageIn) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = imageIn;
	}
	public void setProperties(Dimension dimIn, Point posIn, String imageIn, boolean clickable, String clickTag) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = imageIn;
		this.clickTag = clickTag;
		this.clickable = clickable;
	}
	
	public void setProperties(Dimension dimIn, Point posIn, String imageIn, boolean clickable) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = imageIn;
		this.clickable = clickable;
	}
	public void setProperties(String imageIn, boolean clickable) {
		this.objectImage = imageIn;
		this.clickable = clickable;
	}
	
	public Pair<Dimension,Point> getPosition(){
		Pair<Dimension,Point> newPos = new Pair<Dimension,Point>(dim,coords);
		return newPos;
	}
	
	public void setPosition(Dimension dimIn, Point posIn) {
		this.coords = posIn;
		this.dim = dimIn;
	}
	
	/**
	 * called on each object every frame, draws the objects image(s) to the main window
	 */
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x, coords.y, null);
	}
	
	public boolean isClickable(){
		return clickable;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public void setVisible(boolean isVisible){
		this.isVisible = isVisible;
	}
}
