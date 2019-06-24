import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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
	protected Image objectImage; //test for a single image object
	protected Runnable clickAction;
	protected boolean clickable;
	private boolean visible = true;
	public boolean hoverable;
	public boolean currentlyHovered;
	public boolean currentlyClicked;
	public ObjectType type;
	public String clickTag;
	public String objID;


	protected HashMap<GameObject, Dimension> children;
	
	public GameObject(ObjectType type) {
		this.coords = new Point(0,0);
		this.dim = new Dimension(64,32);
		this.type = type;
		this.children = new HashMap<GameObject, Dimension>();
	}
	
	public void setProperties(Dimension dimIn, Point posIn, String imageIn) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = Game.objectMap.getImage(imageIn);
	}

	public void setProperties(Dimension dimIn, Point posIn, String imageIn, boolean clickable, String clickTag) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = Game.objectMap.getImage(imageIn);
		this.clickTag = clickTag;
		this.clickable = clickable;
	}
	
	
	/**
	 * @param dimIn
	 * @param posIn
	 * @param imageIn
	 * @param clickable
	 * @param clickAction ClickAction interface object to be called when this GameObject is clicked
	 * 
	 *  This function is a work in progress to test changing click functionality to not use 'clickTag' Strings
	 */
	public void setProperties(Dimension dimIn, Point posIn, String imageIn, boolean clickable, Runnable clickAction) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = Game.objectMap.getImage(imageIn);
		this.clickAction = clickAction;
		this.clickable = clickable;
	}
	
	public void setProperties(Dimension dimIn, Point posIn, String imageIn, boolean clickable) {
		this.dim = dimIn;
		this.coords = posIn;
		this.objectImage = Game.objectMap.getImage(imageIn);
		this.clickable = clickable;
	}
	public void setProperties(String imageIn, boolean clickable) {
		this.objectImage = Game.objectMap.getImage(imageIn);
		this.clickable = clickable;
	}
	
	public Pair<Dimension,Point> getPosition(){
		Pair<Dimension,Point> newPos = new Pair<Dimension,Point>(dim,coords);
		return newPos;
	}
	
	public void setPosition(Dimension dimIn, Point posIn) {
		this.dim = dimIn;
		this.coords = posIn;
		setChildrenPosition();
		
	}
	public void setPosition(Point pos) {
		this.coords = pos;
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
								+ children.get(child).width,
						(this.coords.y)
								+ children.get(child).height);
			}
		}
	}
	
	public void setClickAction(Runnable actionIn) {
		this.clickAction = actionIn;
	}
	
	public void addChild(GameObject child, Dimension positionOffset) {
		child.setPosition(child.dim, new Point(this.coords.x+positionOffset.width+Game.xOffset,this.coords.y+positionOffset.height+Game.yOffset));
		this.children.put(child, positionOffset);
		
	}
	
	/**
	 * called on each object every frame, draws the objects image(s) to the main window
	 */
	public void render(Graphics g) {
		g.drawImage(objectImage, coords.x + Game.xOffset, coords.y + Game.yOffset, null);
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
		
		if(isHovered) {
			if(!this.currentlyHovered) {
				hoverAction();
			}
		}else {
			if(this.currentlyHovered) {
				disableHover();
			}
		}



	}
	public boolean isHovered(){
		return currentlyHovered;
	}
	
	public boolean isVisible(){
		return visible;
	}
	public void clickAction() {
//		System.out.println("Click action on: "+this.objectImage);
		this.currentlyClicked = true;
		if(this.clickAction != null) {
			ClickAction.callClickAction(clickAction);
		}
	}
	
	Runnable getClickAction() {
		return this.clickAction;
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
		this.visible = isVisible;
	}
	public void hoverAction(){
		this.currentlyHovered = true;
	}
	public void disableHover(){
		this.currentlyHovered = false;
	}
	public void disableClick(){
		this.currentlyClicked = false;
		
	}
}
