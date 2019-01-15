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
			ArrayList<UIContainer> containers;
			ArrayList<UserInterfaceObject> elements;
			
			UIContainer(){
				elements = new ArrayList<UserInterfaceObject>();
				containers = new ArrayList<UIContainer>();
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


	
	/**
	 * @param containerName
	 * Creates a container which will store a set of UI objects that will always render together
	 */
	public void createUIContainer(String containerName, Point firstElementPos, Point elementSpacing) {
		UIContainer newContainer = new UIContainer();
		newContainer.nextElementPos = firstElementPos;
		newContainer.elementSpacing = elementSpacing;
//		newContainer.visible = true;
		containerMap.put(containerName, newContainer);
	}
	
	public void addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  String clickTag) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
				newUIObject.setProperties(new Point(objectsContainer.nextElementPos), clickTag);
				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
			}
			
		
			objectsContainer.addObject(newUIObject);
		}else {
			System.out.println("UIContainer does not exist");
			return;
		}
		
	}
	/**
	 * @param elementType
	 * @param containerName
	 * @param objectKey
	 * @param clickTag
	 * @param buttonText
	 * 
	 * Used for UI elements that contain text
	 */
	public void addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  String clickTag, String buttonText) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
				newUIObject.setProperties(new Point(objectsContainer.nextElementPos), clickTag);
				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
			}
			
			if(elementType == UserInterfaceObject.UIElementType.SMALLTEXT) {
//				newUIObject.addChild(child, positionOffset);
			}
			
			
			objectsContainer.addObject(newUIObject);
		}else {
			System.out.println("UIContainer does not exist");
			return;
		}
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
	
	



}
