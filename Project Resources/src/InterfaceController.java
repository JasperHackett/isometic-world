import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class InterfaceController {
	

	
		private class UIContainer{
			boolean visible = false;
			Point elementSpacing;
			Point nextElementPos;
			ArrayList<UserInterfaceObject> elements;
			
			UIContainer(){
				elements = new ArrayList<UserInterfaceObject>();
			}
			
			public void addObject(UserInterfaceObject newObject){
				elements.add(newObject);
			}
			public ArrayList<UserInterfaceObject> getObjects(){
				return elements;
			}
			
		}
		
		Map<String,UIContainer> containerMap = new HashMap<String,UIContainer>();

	
	public InterfaceController(){
		
	}


	
	public void createUIContainer(String containerName) {
		UIContainer newContainer = new UIContainer();
//		newContainer.visible = true;
		containerMap.put(containerName, newContainer);
	}
	
	public void addInterfaceObject(String containerName, String objectKey, String clickTag) {
		UserInterfaceObject tempObject = Game.objectMap.addUIObject(objectKey);
		tempObject.setProperties(new Dimension(64,32), new Point(500,500), "uibuttonsmall", true, "newgame");
		
//		UserInterfaceObject testButton = new UserInterfaceElement(ObjectType.DEFAULT,UserInterfaceElement.UIElementType.SMALL,new Point(400,400),"newgame"
		
		
		containerMap.get(containerName).addObject(tempObject);
		
	}



	public void enableInterfaceContainer(String containerName) {
		containerMap.get(containerName).visible = true;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}

	}
	public void disableInterfaceContainer(String containerName) {
		containerMap.get(containerName).visible = false;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects()) {
			Game.objectMap.enabledUIObjects.remove(uiObj);
		}

	}
	
	
//	public void renderInterfaceController(Graphics g) {
//
//		for (Entry<String,UIContainer> container : containerMap.entrySet()) {
////			System.out.println("asasdasd");
//			if(container.getValue().visible == true) {
//				for(UserInterfaceObject element : container.getValue().getObjects()) {
//					element.render(g);
//				}
//			}
//		}
//	}


}
