import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
		TEXTBOXSTATIC,
		TEXTBOXSTATICVALUE,
		TOPBAR
	}
	UIElementType type;
	TextObject elementText;
	String defaultObjectImage;
	String hoverImage;
	GameObject referenceObject;


	
	public UserInterfaceObject(ObjectType objectType) {
		super(objectType);
		elementText = new TextObject(ObjectType.CHILD);
		this.fontKey = "smallbuttonfont";
	}
	
	public void setProperties(Point pos, String clickTag) {
		this.coords = pos;
		this.clickable = true;
		this.clickTag = clickTag;
	}
	
	public void setCustomProperties(Point pos, String objectImage, Dimension dimIn, boolean clickable) {
		this.objectImage = objectImage;
		this.defaultObjectImage = objectImage;
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
	}

	
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
				this.objectImage = "textbox2";
				this.defaultObjectImage = objectImage;
				this.hoverImage = "textbox1";
				break;
			case TEXTBOXSTATIC:
				this.dim = new Dimension(160,20);
				this.objectImage = "textbox2";
				this.defaultObjectImage = objectImage;
				break;
				
			case TEXTBOXSTATICVALUE:
				this.dim = new Dimension(160,20);
				this.objectImage = "textbox3";
				this.defaultObjectImage = objectImage;
				break;
			case SMALL :
//				System.out.println("small obj created");
				this.dim = (new Dimension(64,32));

				this.objectImage = "uibuttonsmall0";
				this.defaultObjectImage = objectImage;
				this.hoverImage = "uibuttonsmall1";
				break;
				
			case TOPBAR:
				this.dim = (new Dimension(96,24));
				fontKey = "topbarfont";
				this.objectImage = "topbarbtn0";
				this.defaultObjectImage = objectImage;
				this.hoverImage = "topbarbtn1";
				break;
			case MEDIUM :
				this.dim = (new Dimension(128,32));

				this.objectImage = "uibuttonmedium0";
				this.fontKey = "mediumbuttonfont";
				this.defaultObjectImage = objectImage;
				this.hoverImage = "uibuttonmedium1";
				break;
				
				
			default :
				break;
		}

		
		
	}
	
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
	public void disableClick(){
		this.currentlyClicked = false;
		if(this.referenceObject != null) {
			referenceObject.disableClick();
		}
	}
	
	
	
	
//	public void addUIElement(UIElementType type) {
//		
//	}
}
