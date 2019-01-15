import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

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
	protected String objectImage; //test for a single image object
	protected boolean clickable;
	private boolean isVisible;
	public boolean hoverable;
	public boolean currentlyHovered;
	public boolean currentlyClicked;
	public ObjectType type;
	public String clickTag;
	public String objID;
	protected HashMap<GameObject, Pair<Double, Double>> children;
	
	public GameObject(ObjectType type) {
		this.type = type;
		this.children = new HashMap<GameObject, Pair<Double, Double>>();
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
		setChildrenPosition();
		
	}
	
	public void setChildrenPosition() {
		if (children == null) {
			return;
		} else if (children.isEmpty()) {
			return;
		} else {
			for (GameObject child : children.keySet()) {
				child.coords.setLocation(
						(this.coords.x)
								+ children.get(child).getKey(),
						(this.coords.y)
								+ children.get(child).getValue());
			}
		}
	}
	
	public void addChild(GameObject child, Pair<Double, Double> positionOffset) {
		this.children.put(child, positionOffset);

	}
	
	/**
	 * called on each object every frame, draws the objects image(s) to the main window
	 */
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset, null);
		if (children == null) {
			return;
		} else if (children.isEmpty()) {
			return;
		} else {
			for (GameObject child : children.keySet()) {
				child.render(g);
			}
		}
		
	}
	
	public boolean isClickable(){
		return clickable;
	}

	public boolean isHoverable(){
		return hoverable;
	}
	public void setHovered(boolean isHovered){
		 this.currentlyHovered = isHovered;
	}
	public boolean isHovered(){
		 return currentlyHovered;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	public void clickAction() {
		System.out.println("Click action on: "+this.objectImage);
		this.currentlyClicked = true;
	}
	public void setClicked(boolean isClicked) {
		if(isClicked) {
			if(this.currentlyClicked == false) {
				this.currentlyClicked = true;
				this.clickAction();
			}
		}else {
			if(this.currentlyClicked == true) {
				this.currentlyClicked = false;
				this.disableClick();
			}
		}
		
//		System.out.println("Click action on: "+this.objectImage);
	}
	public boolean isClicked() {
		return currentlyClicked;
	}
	
	public void setVisible(boolean isVisible){
		this.isVisible = isVisible;
	}
	public void hoverAction(){
		this.currentlyHovered = true;
		System.out.println("Hovering on: ");
	}
	public void disableHover(){
		this.currentlyHovered = false;
	}
	public void disableClick(){
		this.currentlyClicked = false;
	}
}
