import java.awt.Color;
import java.awt.Dimension;
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
		System.out.println(pos);
		elementText.setTextProperties(buttonText,Game.objectMap.getFont("primarygamefont"),Color.WHITE,pos);
		Dimension offset = new Dimension(this.dim.width/2 - elementText.width/2,this.dim.height /2 + elementText.height/3);
		this.addChild(elementText, offset);
			

	}

	
	public UserInterfaceObject(ObjectType objectType, UIElementType uiType) {
		super(objectType);
		type = uiType;
		switch(uiType) {
		
			case SMALL :
//				System.out.println("small obj created");
				this.dim = (new Dimension(64,32));
				this.objectImage = "uibuttonsmall";
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
