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
	

	public enum InterfaceZone{
		TopSidePanel,
		MiddleSidePanel;
	}
	
	
	private class UIContainer{
		boolean visible = false;
		Point coords;
		Point elementSpacing;
		Point nextElementPos;
		ArrayList<UIContainer> containers;
		HashMap<String,UserInterfaceObject> elements;
		HashMap<String,TextObject> textObjects;
		GameObject parentObject;
		InterfaceZone interfaceZone;


		UIContainer(Point coords){
			this.coords = coords;
			elements = new HashMap<String,UserInterfaceObject>();
			textObjects = new HashMap<String,TextObject>();
			containers = new ArrayList<UIContainer>();
		}
		
		public void addObject(String objKey,UserInterfaceObject newObject){
			elements.put(objKey,newObject);
		}
		
		public void setUIContainerPos(String containerName, Point pos) {
			if(containerMap.containsKey(containerName)) {
				containerMap.get(containerName).coords = pos;
			}else {
				System.out.println("invalid containerName");
			}
		}
		
		public HashMap<String,UserInterfaceObject> getObjects(){
			return elements;
		}
		public HashMap<String,TextObject> getTextObjects(){
			return textObjects;
		}

		public void updateValues() {
			if(parentObject !=null) {
				ResourceStructure rStructure = (ResourceStructure) parentObject;
				this.elements.get("resourcestoredvalue").setElementText(Integer.toString(rStructure.resourceStored));
				this.elements.get("workersvalue").setElementText(Integer.toString(rStructure.currentWorkers));
				
			}else {
				System.out.println("updateValues called with null parentObject");
			}
		}
		
	}
	HashMap<InterfaceZone,String> zoneMap;
	Map<String,UIContainer> containerMap = new HashMap<String,UIContainer>();

	
	public InterfaceController(){
		zoneMap = new HashMap<InterfaceZone,String>();
		zoneMap.put(InterfaceZone.TopSidePanel,"citiesmenu");
	}


	
	/**
	 * @param containerName
	 * Creates a container which will store a set of UI objects that will always render together
	 */
	public void createUIContainer(String containerName, Point firstElementPos, Point elementSpacing) {
		UIContainer newContainer = new UIContainer(firstElementPos);
		newContainer.nextElementPos = firstElementPos;
		newContainer.elementSpacing = elementSpacing;
//		newContainer.visible = true;
		containerMap.put(containerName, newContainer);
	}
	
	public void createUIContainer(String containerName, Point firstElementPos, Point elementSpacing,GameObject parentObject) {
		UIContainer newContainer = new UIContainer(firstElementPos);
		newContainer.nextElementPos = firstElementPos;
		newContainer.elementSpacing = elementSpacing;
		newContainer.parentObject = parentObject;
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
	
	
	/**
	 * @param elementType
	 * @param containerName
	 * @param objectKey
	 * @param clickTag
	 * @param buttonText
	 * @param parentObject
	 * 
	 * 
	 * Adds interface object assigning a parentCity
	 */
	public void addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  String clickTag, String buttonText, City parentObject) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		newUIObject.referenceObject = parentObject;
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
	
	
	/**
	 * @param elementType
	 * @param containerName
	 * @param objectKey
	 * @param text
	 * @param fontKey
	 * @param textColor
	 * @param pos
	 * 
	 *  Adds a TextObject and positions it relative to UI container
	 */
	public void addInterfaceTextObject(UserInterfaceObject.UIElementType elementType, String containerName, String objectKey, String text, String fontKey,Color textColor, Point pos) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey, elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(elementType == UserInterfaceObject.UIElementType.TEXTBOX) {
				Point newPos = new Point(objectsContainer.coords.x + pos.x, objectsContainer.coords.y + pos.y);
				newUIObject.coords = newPos;
//				newPos.setLocation(x, y);
				newUIObject.setElementTextProperties(text,fontKey,textColor,newPos);
				
			}else if(elementType == UserInterfaceObject.UIElementType.TEXT) {
				
					
					Point newPos = new Point(objectsContainer.coords.x + pos.x, objectsContainer.coords.y + pos.y);
					newUIObject.coords = newPos;
					newUIObject.setElementTextProperties(text,fontKey,textColor,newPos);
	//				if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
	//					
	//					objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
	//							objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
	//				}
					
					

			}
			objectsContainer.addObject(objectKey,newUIObject);
		}else {
			System.out.println("UIContainer does not exist");
			return;
		}
		
	}
	
	public void updateContainerValues(String containerName) {
		if(containerName.equals("resourcestructure")) {
			containerMap.get(containerName).updateValues();
//			containerMap.get(containerName)
//			resourceStructureContainer.elements.get("workersvalue").setElementText(Integer.toString(p))
		}
	}
	public void setParentObject(String containerName, GameObject parentObject) {
		containerMap.get(containerName).parentObject = parentObject;
	}

	public void enableInterfaceContainer(String containerName) {
		containerMap.get(containerName).visible = true;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}

	}
	public void enableInterfaceContainer(String containerName,InterfaceZone zoneIn) {
		disableInterfaceContainer(zoneMap.get(zoneIn));
		containerMap.get(containerName).visible = true;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}
		zoneMap.put(zoneIn, containerName);

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
	
	public void passRStructureToInterfaceContainer(ResourceStructure rStructure, String containerName) {
		if(containerName.equals("resourcestructure")) {
			UIContainer resourceStructureContainer = containerMap.get(containerName);
			resourceStructureContainer.elements.get("structuretitle").setElementText(rStructure.name);
			resourceStructureContainer.elements.get("resourcestoredlabel").setElementText("Iron ore stored:");
			resourceStructureContainer.elements.get("resourcestoredvalue").setElementText(Integer.toString(rStructure.resourceStored));
			resourceStructureContainer.elements.get("workerslabel").setElementText("Workers:");
			resourceStructureContainer.elements.get("workersvalue").setElementText(Integer.toString(rStructure.currentWorkers));
//			resourceStructureContainer.elements.get("workerticklabel").setElementText("Next worker:");
//			resourceStructureContainer.elements.get("workertickvalue").setElementText(Integer.toString(rStructure.tickCounter));
			for(UserInterfaceObject uiObj : containerMap.get(containerName).elements.values()) {
				uiObj.referenceObject =rStructure;
			}
		}
	}
	
	public void passWarehouseToInterfaceContainer(Warehouse warehouse, String containerName) {

		if(containerName.equals("warehouse")) {
			UIContainer warehouseContainer = containerMap.get(containerName);
			warehouseContainer.elements.get("structuretitle").setElementText(warehouse.name);
//			resourceStructureContainer.elements.get("resourcestoredlabel").setElementText("Iron ore stored:");
//			resourceStructureContainer.elements.get("resourcestoredvalue").setElementText(Integer.toString(rStructure.resourceStored));
			warehouseContainer.elements.get("workerslabel").setElementText("Workers:");
			warehouseContainer.elements.get("workersvalue").setElementText(Integer.toString(warehouse.currentWorkers));
//			resourceStructureContainer.elements.get("workerticklabel").setElementText("Next worker:");
//			resourceStructureContainer.elements.get("workertickvalue").setElementText(Integer.toString(rStructure.tickCounter));

			for(UserInterfaceObject uiObj : containerMap.get(containerName).elements.values()) {
				uiObj.referenceObject = warehouse;
			}
			
		}
	}
	
	public void initaliseMainMenuInterface() {
		createUIContainer("mainmenu",new Point(200,600), new Point(0,50));
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu", "newgamebutton","newgame","Start");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu", "exitbutton","exit","Quit");
		enableInterfaceContainer("mainmenu");
	}
	
	public void initialiseMainGameInterface() {
		createUIContainer("topmenubar", new Point(384,4), new Point(128,0));
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarlabour", "workersmenu", "Workers");
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarconstruction", "constructionmenu", "Construction");
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarcities", "citiesmenu", "Cities");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "topmenubar","moneylabel","$ ","primarygamefont",Color.WHITE,new Point(70,0));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "topmenubar","moneyvalue","undefined","primarygamefont",Color.YELLOW,new Point (100,0));

		createUIContainer("citymanager", new Point(1450,120), new Point(0,50));
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "citymanager", "hellobtn", "hello", "Hello");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "citymanager","citytitle","undefined","primarygamefont",Color.WHITE,new Point (0,-100));
//		enableInterfaceContainer("cityinterface");
		
		
		createUIContainer("constructionmenu",new Point(1450,200), new Point(0,50));
		addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,"constructionmenu", "buildironmine","buildironmine","Iron Mine");
		
		createUIContainer("citiesmenu",new Point(1450,200), new Point(0,40));
		for(City city : Game.gameWorld.cityList) {
			addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,"citiesmenu", city.name+"citiesmenu","citybtn",city.name,city);
		}
		
		createUIContainer("workersmenu",new Point(1450,200), new Point(0,40));
		addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM, "workersmenu", "hireworker", "hireworker", "Hire Worker");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalworkerslabel","Total workers:","primarygamefont",Color.WHITE,new Point (0,0));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalworkersvalue","undefined","primarygamefont",Color.WHITE,new Point (80,0));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","availableworkerslabel","Available workers:","primarygamefont",Color.WHITE,new Point (00,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","availableworkersvalue","undefined","primarygamefont",Color.WHITE,new Point (80,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalcostlabel","Cost:","primarygamefont",Color.WHITE,new Point (0,60));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalcostvalue","undefined","primarygamefont",Color.YELLOW,new Point (80,60));


		createUIContainer("resourcestructure", new Point(1450,120), new Point(0,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","structuretitle","undefined","primarygamefont",Color.WHITE,new Point (0,-50));
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "resourcestructure", "addworkerbtn", "addWorker", "Assign");
	
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","resourcestoredlabel","undefined","primarygamefont",Color.WHITE,new Point (100,0));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "resourcestructure","resourcestoredlabel","undefined","primarygamefont",Color.WHITE,new Point (-30,10));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","resourcestoredvalue","undefined","primarygamefont",Color.WHITE,new Point (130,20));
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (0,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "resourcestructure","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (-30,40));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workersvalue","undefined","primarygamefont",Color.WHITE,new Point (130,50));
		
		
		createUIContainer("warehouse", new Point(1450,120), new Point(0,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","structuretitle","undefined","primarygamefont",Color.WHITE,new Point (0,-50));
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "warehouse", "addworkerbtn", "addWorker", "Assign");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "warehouse","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (-30,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","workersvalue","undefined","primarygamefont",Color.WHITE,new Point (100,40));
		
	}
	
	



}
