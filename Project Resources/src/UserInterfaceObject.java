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
	
	
	
	public enum UIElementType{
		CUSTOM,
		SMALL,
		MEDIUM,
		TEXT
	}
	UIElementType type;
	TextObject elementText;
	String defaultObjectImage;
	String hoverImage;

	
	public UserInterfaceObject(ObjectType objectType) {
		super(objectType);
		elementText = new TextObject(ObjectType.CHILD);
	}
	
	public void setProperties(Point pos, String clickTag) {
		this.coords = pos;
		this.clickable = true;
		this.clickTag = clickTag;
	}
	public void setProperties(Point pos, String clickTag, String buttonText) {
		this.coords = pos;
		this.clickable = true;
		this.clickTag = clickTag;
		TextObject elementText = new TextObject(ObjectType.CHILD);
//		System.out.println(pos);
		elementText.setTextProperties(buttonText,Game.objectMap.getFont("smallbuttonfont"),Color.LIGHT_GRAY,pos);
		Dimension offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		this.addChild(elementText, offset);
			

	}
	public void setElementTextProperties(String text, String fontKey,Color textColor, Point pos) {
		this.coords = pos;
		elementText = new TextObject(ObjectType.CHILD);
		elementText.setTextProperties(text, Game.objectMap.getFont(fontKey),textColor, pos);
//		this.dim = new Dimension(Game.graphics.getFontMetrics(font).stringWidth(text);)
		Dimension offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		this.addChild(elementText, offset);
	}
	public void setElementText(String text) {
		elementText.setText(text);
	}

	
	public UserInterfaceObject(ObjectType objectType, UIElementType uiType) {
		super(objectType);
		type = uiType;
		switch(uiType) {
		
			case TEXT:
				this.dim = new Dimension(64,32);
				break;
			case SMALL :
//				System.out.println("small obj created");
				this.dim = (new Dimension(64,32));

				this.objectImage = "uibuttonsmall0";
				this.defaultObjectImage = objectImage;
				this.hoverImage = "uibuttonsmall1";
				break;
				
			case MEDIUM :
				break;
				
				
			default :
				break;
		}

		
		
	}
	
	public void hoverAction(){
		this.currentlyHovered = true;
		this.objectImage = hoverImage;
	}
	public void disableHover(){
		this.currentlyHovered = false;
		this.objectImage = defaultObjectImage;
	}
	
	
	
	
	
//	public void addUIElement(UIElementType type) {
//		
//	}
}
