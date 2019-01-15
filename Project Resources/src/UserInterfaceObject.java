import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javafx.util.Pair;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class UserInterfaceObject extends GameObject{
	
	
	
	public enum UIElementType{
		CUSTOM,
		SMALL,
		SMALLTEXT,
		MEDIUM,
		TEXT
	}
	UIElementType type;
	TextObject elementText;
	
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
		if(type == UIElementType.SMALLTEXT) {
			elementText.setText(buttonText);
		}
	}

	
	public UserInterfaceObject(ObjectType objectType, UIElementType uiType) {
		super(objectType);
		type = uiType;
		switch(uiType) {
		
			case SMALL :
				System.out.println("small obj created");
				this.dim = (new Dimension(64,32));
				this.objectImage = "uibuttonsmall";
				break;
				
			case SMALLTEXT:
				
				System.out.println("smalltext created");
				this.dim = (new Dimension(64,32));
				this.objectImage = "uibuttonsmall";
				TextObject elementText = new TextObject(ObjectType.CHILD);
//				TextObject elementText = new TextObject(ObjectType.CHILD);
				elementText.setTextProperties("Test",Game.objectMap.getFont("smallbuttonfont"),Color.WHITE,new Point(0,0));
				Pair<Double,Double> offset = new Pair<Double,Double>((this.dim.getWidth()/2) , (this.dim.getHeight()/2));
				this.addChild(elementText, offset);
				
				break;
			case MEDIUM :
				break;
				
				
			default :
				break;
		}

		
		
	}
	
	
	
	
	
//	public void addUIElement(UIElementType type) {
//		
//	}
}
