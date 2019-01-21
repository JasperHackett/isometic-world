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
			HashMap<String,UserInterfaceObject> elements;
			
			UIContainer(){
				elements = new HashMap<String,UserInterfaceObject>();
				containers = new ArrayList<UIContainer>();
			}
			
			public void addObject(String objKey,UserInterfaceObject newObject){
				elements.put(objKey,newObject);
			}
			public HashMap<String,UserInterfaceObject> getObjects(){
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
			
		
			objectsContainer.addObject(objectKey,newUIObject);
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
	 * Used for UI elements that contain image and text (e.g. buttons)
	 */
	public void addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  String clickTag, String buttonText) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
				newUIObject.setProperties(new Point(objectsContainer.nextElementPos), clickTag, buttonText);
				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
			}
			
			
			objectsContainer.addObject(objectKey,newUIObject);
		}else {
			System.out.println("UIContainer does not exist");
			return;
		}
	}
	
	public void addInterfaceTextObject(UserInterfaceObject.UIElementType elementType, String containerName, String objectKey, String text, String fontKey,Color textColor, Point pos) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey, elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			newUIObject.setElementTextProperties(text,fontKey,textColor,pos);
//			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
//				
//				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
//						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
//			}
			
			
			objectsContainer.addObject(objectKey,newUIObject);
		}else {
			System.out.println("UIContainer does not exist");
			return;
		}
	}


	public void enableInterfaceContainer(String containerName) {
		containerMap.get(containerName).visible = true;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}

	}
	public void disableInterfaceContainer(String containerName) {
		containerMap.get(containerName).visible = false;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.remove(uiObj);
		}

	}
	
	/**
	 * @param city
	 * @param containerName
	 * 
	 *  Passes appropriate fields to city manager interfacew
	 */
	public void passCityToInterfaceContainer(City city, String containerName) {
		if(containerName.equals("citymanager")) {
			UIContainer cityContainer = containerMap.get(containerName);
			cityContainer.elements.get("citytitle").setElementText(city.name);
		}
	}
	
	



}
