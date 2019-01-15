import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

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
		MEDIUM,
		TEXT
	}
	
	public UserInterfaceObject(ObjectType objectType) {
		super(objectType);
	}
	
	public void setProperties(Point pos, String clickTag) {
		this.coords = pos;
		this.clickable = true;
		this.clickTag = clickTag;
	}
	
	public UserInterfaceObject(ObjectType objectType, UIElementType uiType) {
		super(objectType);
		
		switch(uiType) {
		
			case SMALL :
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
