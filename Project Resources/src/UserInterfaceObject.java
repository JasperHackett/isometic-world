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
		SMALL,
		MEDIUM,
		TEXT
	}
	
	public UserInterfaceObject(ObjectType objectType) {
		super(objectType);
	}
	
	public UserInterfaceObject(ObjectType objectType, UIElementType uiType, Point posIn, String clickTag) {
		super(objectType);
		
		switch(uiType) {
		
			case SMALL :
				this.setProperties(new Dimension(64,32), posIn,"uibuttonsmall",true, clickTag);
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
