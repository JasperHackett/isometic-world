import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javafx.util.Pair;



/**
 * @author Jasper
 *
 */
public class UserInterfaceObject extends GameObject{
	
	public String fontKey = "smallbuttonfont";
	
	public enum UIElementType{
		CUSTOM,
		SMALL,
		MEDIUM,
		TEXT,
		TEXTBOX,
		TEXTBOXDROPDOWN,
		TEXTBOXSTATIC,
		TEXTBOXSTATICVALUE,
		TOPBAR,
		CITYBUTTON
	}
	UIElementType type;
	TextObject elementText;
	Image defaultObjectImage;
	Image hoverImage;
	GameObject referenceObject;
	WorldObject worldReference;
	//If this is true, when this object is clicked other 
	//clicked interface objects on a stack in InputHandler will be set to not clicked
	boolean clearsStack = false;
	
	public UserInterfaceObject(ObjectType objectType) {
		super(objectType);
		elementText = new TextObject(ObjectType.CHILD);
		this.fontKey = "smallbuttonfont";
	}
	
	//Initialise object with objectType specific properties
	public UserInterfaceObject(ObjectType objectType, UIElementType uiType) {
		super(objectType);
		type = uiType;
		fontKey = "smallbuttonfont";
		this.clickTag = "";
		switch(uiType) {

			case CUSTOM:
				this.dim = new Dimension(0,0);
				break;
		
			case TEXT:
				this.dim = new Dimension(0,0);
				break;
			case TEXTBOX:
				this.dim = new Dimension(160,20);
				this.objectImage = Game.objectMap.getImage("textbox2");
				this.defaultObjectImage = objectImage;
				this.hoverImage = Game.objectMap.getImage("textbox1");
				break;
			case TEXTBOXDROPDOWN:
				this.dim = new Dimension(160,20);
				this.objectImage = Game.objectMap.getImage("textbox4");
				this.defaultObjectImage = objectImage;
				this.hoverImage = Game.objectMap.getImage("textbox5");
				break;
			case TEXTBOXSTATIC:
				this.dim = new Dimension(160,20);
				this.objectImage = Game.objectMap.getImage("textbox2");
				this.defaultObjectImage = objectImage;
				break;
				
			case TEXTBOXSTATICVALUE:
				this.dim = new Dimension(160,20);
				this.objectImage = Game.objectMap.getImage("textbox3");
				this.defaultObjectImage = objectImage;
				break;
			case SMALL :
//				System.out.println("small obj created");
				this.dim = (new Dimension(64,32));

				this.objectImage = Game.objectMap.getImage("uibuttonsmall0");
				this.defaultObjectImage = objectImage;
				this.hoverImage = Game.objectMap.getImage("uibuttonsmall1");
				break;
				
			case TOPBAR:
				this.dim = (new Dimension(96,24));
				fontKey = "topbarfont";
				this.objectImage = Game.objectMap.getImage("topbarbtn0");
				this.defaultObjectImage = objectImage;
				this.hoverImage = Game.objectMap.getImage("topbarbtn1");
				break;
			case MEDIUM :
				this.dim = (new Dimension(128,32));

				this.objectImage = Game.objectMap.getImage("uibuttonmedium0");
				this.fontKey = "mediumbuttonfont";
				this.defaultObjectImage = objectImage;
				this.hoverImage = Game.objectMap.getImage("uibuttonmedium1");
				break;
				
			case CITYBUTTON:
				this.dim = new Dimension(160,36);
				this.fontKey = "citybuttonfont";
				this.objectImage = Game.objectMap.getImage("citybutton0");
				this.defaultObjectImage = objectImage;
				this.hoverImage = Game.objectMap.getImage("citybutton1");
						
				break;
			default :
				break;
		}

		
		
	}
	
	public void setProperties(Point pos, String clickTag) {
		this.coords = pos;
		this.clickable = true;
		this.clickTag = clickTag;
	}
	public void setClearsStack(boolean clearsStack) {
		this.clearsStack = clearsStack;
	}
	public void setWorldReference(WorldObject obj) {
		this.worldReference = obj;
	}
	
	public void setCustomProperties(Point pos, String objectImage, Dimension dimIn, boolean clickable) {
		this.objectImage = Game.objectMap.getImage(objectImage);
		this.defaultObjectImage = Game.objectMap.getImage(objectImage);
		this.dim = dimIn;
		this.clickable = clickable;
		this.coords = pos;
	}
	public void setProperties(Point pos, String clickTag, String buttonText) {
		this.coords = pos;
		this.clickable = true;
		this.clickTag = clickTag;
		TextObject elementText = new TextObject(ObjectType.CHILD);
//		System.out.println(pos);
		if(fontKey == null) {
			fontKey = "smallbuttonfont";
		}
		elementText.setTextProperties(buttonText,Game.objectMap.getFont(fontKey),Color.LIGHT_GRAY,pos);
		Dimension offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		this.addChild(elementText, offset);
			

	}
	public void setProperties(Point pos, Action clickAction, String buttonText) {
		this.coords = pos;
		this.clickable = true;
		this.clickAction = clickAction;
		TextObject elementText = new TextObject(ObjectType.CHILD);
//		System.out.println(pos);
		if(fontKey == null) {
			fontKey = "smallbuttonfont";
		}
		elementText.setTextProperties(buttonText,Game.objectMap.getFont(fontKey),Color.LIGHT_GRAY,pos);
		Dimension offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		this.addChild(elementText, offset);
			

	}
	public void setElementTextProperties(String text, String fontKey,Color textColor, Point pos) {
		this.coords = pos;
		elementText = new TextObject(ObjectType.CHILD);
		if(this.type == UIElementType.TEXT) {
			this.clickable = false;
		}

		elementText.setTextProperties(text, Game.objectMap.getFont(fontKey),textColor, pos);
//		this.dim = new Dimension(Game.graphics.getFontMetrics(font).stringWidth(text);)
		Dimension offset = null;
		if( this.type == UIElementType.TEXTBOXSTATICVALUE || this.type == UIElementType.TEXTBOXSTATIC) {
			 offset = new Dimension(this.dim.width/4 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		}else {
			 offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		}

		this.addChild(elementText, offset);
	}
	public void setElementText(String text) {
		elementText.setText(text);
		Dimension offset = null;
		if( this.type == UIElementType.TEXTBOXSTATICVALUE || this.type == UIElementType.TEXTBOXSTATIC) {
			 offset = new Dimension(this.dim.width/4 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		}else {
			 offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		}
		this.addChild(this.elementText,offset);
	}
	
	@Override
	public void setPosition(Point pos) {
		this.coords = pos;
		setChildrenPosition();
		if(this.type == UIElementType.TEXTBOX) {
//			this.elementText.setPosition(pos);
		}
	}
	@Override
	public void setChildrenPosition() {
		if (children == null) {
			return;
		} 

		for (GameObject child : children.keySet()) {
			if(child instanceof TextObject) {
				child.setPosition(this.coords);
			}

		}
		
	}

//	
	

	
	
	
	public void hoverAction(){
		this.currentlyHovered = true;
		if(hoverImage != null) {
			this.objectImage = hoverImage;
		}

	}
	public void disableHover(){
		this.currentlyHovered = false;
		this.objectImage = defaultObjectImage;
	}
	
	@Override
	public void clickAction(){
		if(referenceObject != null) {
			if(referenceObject instanceof City) {
				referenceObject.setClicked(true);
				return;
			}
		}
		super.clickAction();
	}
	
	@Override
	public void disableClick(){
		this.currentlyClicked = false;
		if(this.referenceObject != null) {
			System.out.println("Disabling click on City");
			referenceObject.disableClick();
		}
	}
	
	
	
	
//	public void addUIElement(UIElementType type) {
//		
	// }
}
