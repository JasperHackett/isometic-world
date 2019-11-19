import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Jasper
 *
 */
public class InterfaceController {


	ActionHandler clickActionControl = new ActionHandler();
	Dimension window; //Window dimensions
//	HashMap<GameObject,>
	

	public enum InterfaceZone{
		TopSidePanel,
		MiddleSidePanel,
		RightSidePanel;
	}
	public enum InterfaceContext{
		DEFAULT,
		TopBar,
		VolatileDropDown;
	}

	InterfaceContext uiContext;

	Structure startStructureHolder;
	Structure destStructureHolder;
	HashMap<InterfaceZone,String> zoneMap;
	Map<String,UIContainer> containerMap = new HashMap<String,UIContainer>();


	private class UIContainer{
		boolean visible = false;
		Point coords;
		Point elementSpacing;
		Point nextElementPos;
		HashMap<String,UIContainer> containers;
		HashMap<String,UserInterfaceObject> elements;
		HashMap<String,TextObject> textObjects;
		GameObject parentObject;
		InterfaceZone interfaceZone;


		UIContainer(Point coords){
			this.coords = coords;
			elements = new HashMap<String,UserInterfaceObject>();
			textObjects = new HashMap<String,TextObject>();
			containers = new HashMap<String,UIContainer>();
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
		
		/**
		 * @param containerName name of container
		 * @param containerIn
		 * 
		 * 	Adds a child container
		 */
		public void addContainer(String containerName,UIContainer containerIn) {
			this.containers.put(containerName, containerIn);
		}
		public void clearAllContainers() {
			this.containers.clear();
			this.elements.clear();
		}
		public void clearContainer(String containerName) {
			this.containers.get(containerName).containers.clear();
			this.containers.get(containerName).elements.clear();
		}
		public HashMap<String,TextObject> getTextObjects(){
			return textObjects;
		}

		public void updateValues() {
//			if(parentObject !=null) {
//				if(this.containers.containsKey("resourcestructure")) {
//					if(containerMap.get("resourcestructure").visible) {
//						ResourceStructure rStructure = (ResourceStructure) parentObject;
//						this.elements.get("resourcestoredvalue").setElementText(Integer.toString(rStructure.resourceStored));
//						this.elements.get("workersvalue").setElementText(Integer.toString(rStructure.currentWorkers));
//					}
//				}
//				if(this.containers.containsKey("workersvalue")) {
//						Warehouse rStructure = (Warehouse) parentObject;
//						this.elements.get("workersvalue").setElementText(Integer.toString(rStructure.currentWorkers));
//					}
//				}
//
//
//			}else {
//				System.out.println("updateValues called with null parentObject");
//			}
		}

	}



	ArrayList<ArrayList<UIContainer>> zIndex = new ArrayList<ArrayList<UIContainer>>();
	ActionHandler actionHandler;

	ArrayList<UserInterfaceObject> volatileObjects = new ArrayList<UserInterfaceObject>(); //Stores the set of objects that are open in a volatile state (1 click will remove them)

	public InterfaceController(Dimension dims,ActionHandler actionHandler){
		window = dims;
		this.actionHandler = actionHandler;
		System.out.println(dims);
		zoneMap = new HashMap<InterfaceZone,String>();
		zoneMap.put(InterfaceZone.TopSidePanel,"citiesmenu");

		for(int i = 0; i < 10; i++) {
			zIndex.add(new ArrayList<UIContainer>());
		}
	}

	public ArrayList<UserInterfaceObject> getZIndex(int i){
		ArrayList<UserInterfaceObject> zIndexArray = new ArrayList<UserInterfaceObject>();
		for(int j = 0; j< zIndex.get(i).size();j++) {
			if(this.zIndex.get(i).get(j).visible) {
				zIndexArray.addAll(this.zIndex.get(i).get(j).elements.values());
			}

		}
		return zIndexArray;

	}



	/**
	 * @param containerName
	 * Creates a container which will store a set of UI objects that will always render together
	 */
	public UIContainer createUIContainer(String containerName, Point pos, Point elementSpacing,int zIndex) {
		UIContainer newContainer = new UIContainer(pos);
		newContainer.nextElementPos = pos;
		newContainer.elementSpacing = elementSpacing;
//		newContainer.visible = true;
		containerMap.put(containerName, newContainer);
		this.zIndex.get(zIndex).add(newContainer);
		return newContainer;
	}

	public UIContainer createUIContainer(String containerName, Point pos, Point elementSpacing,GameObject parentObject) {
		UIContainer newContainer = new UIContainer(pos);
		newContainer.nextElementPos = pos;
		newContainer.elementSpacing = elementSpacing;
		newContainer.parentObject = parentObject;
		containerMap.put(containerName, newContainer);
		return newContainer;
	}



	/**
	 * @param elementType
	 * @param containerName
	 * @param objectKey
	 * @param clickTag
	 *
	 * Used for objects with custom image
	 */
	public UserInterfaceObject addCustomInterfaceObject(UserInterfaceObject.UIElementType elementType,Point pos,String containerName, String objectKey,  String objectImage, Dimension dimIn, boolean clickable) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);

		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			Point newPos = new Point(objectsContainer.coords.x + pos.x, objectsContainer.coords.y + pos.y);
			newUIObject.setCustomProperties(newPos,objectImage,dimIn,clickable);
			newUIObject.hoverable = false;


//			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
//
//				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
//						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
//			}
//

			objectsContainer.addObject(objectKey,newUIObject);
		}else {
			System.out.println("UIContainer does not exist");
			return null;
		}
		return newUIObject;

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
	 * 
	 * Testing the implementation of ClickAction interfaces
	 */
	public UserInterfaceObject addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  Action clickAction, String buttonText) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
				newUIObject.setProperties(new Point(objectsContainer.nextElementPos), clickAction, buttonText);
				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
			}


			objectsContainer.addObject(objectKey,newUIObject);
			return newUIObject;
		}else {
			System.out.println("UIContainer does not exist");
			return null;
		}
	}
	
	

	public UserInterfaceObject addInterfaceObject(UserInterfaceObject.UIElementType elementType, Point pos,String containerName, String objectKey,  Action action, String buttonText) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		newUIObject.clickAction = action;
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			Point newPos = new Point(objectsContainer.coords.x + pos.x, objectsContainer.coords.y + pos.y);
			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
				newUIObject.setProperties(newPos, action, buttonText);
				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
			}


			objectsContainer.addObject(objectKey,newUIObject);
			return newUIObject;
		}else {
			System.out.println("UIContainer does not exist");
			return null;
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
//	public void addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  String clickTag, String buttonText, City parentObject) {
//		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
//		newUIObject.referenceObject = parentObject;
//		if(containerMap.containsKey(containerName)) {
//			UIContainer objectsContainer = containerMap.get(containerName);
//			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
//				newUIObject.setProperties(new Point(objectsContainer.nextElementPos), clickTag, buttonText);
//				objectsContainer.nextElementPos.setLocation(objectsContainer.nextElementPos.x + objectsContainer.elementSpacing.x,
//						objectsContainer.nextElementPos.y + objectsContainer.elementSpacing.y);
//			}
//
//
//			objectsContainer.addObject(objectKey,newUIObject);
//		}else {
//			System.out.println("UIContainer does not exist");
//			return;
//		}
//	}


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
	public void addInterfaceTextObject(UserInterfaceObject.UIElementType elementType, String containerName, String objectKey, String text, String fontKey,Color textColor, Point pos, String clickTag) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey, elementType);
		newUIObject.clickTag = clickTag;
		newUIObject.clickable = true;

		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(elementType == UserInterfaceObject.UIElementType.TEXTBOX || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATIC
					|| elementType == UserInterfaceObject.UIElementType.TEXTBOXDROPDOWN) {
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
	public void addInterfaceTextObject(UserInterfaceObject.UIElementType elementType, String containerName, String objectKey, String text, String fontKey,Color textColor, Point pos, String clickTag,GameObject referenceObj) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey, elementType);
		newUIObject.referenceObject = referenceObj;
		newUIObject.clickTag = clickTag;
		newUIObject.clickable = true;

		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(elementType == UserInterfaceObject.UIElementType.TEXTBOX || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATIC
					|| elementType == UserInterfaceObject.UIElementType.TEXTBOXDROPDOWN) {
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
	
	/**
	 * @param elementType
	 * @param containerName
	 * @param objectKey
	 * @param text
	 * @param fontKey
	 * @param textColor
	 * @param pos
	 * @param clickTag
	 * @param action
	 * 
	 * 	Used in drop down menus to create buttons with associated action
	 * 
	 */
	public UserInterfaceObject addInterfaceTextObject(UserInterfaceObject.UIElementType elementType, String containerName, String objectKey, String text, String fontKey,Color textColor, Point pos, Action action) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey, elementType);
//		newUIObject.referenceObject = referenceObj;
		newUIObject.setClickAction(action);
		newUIObject.clickable = true;

		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(elementType == UserInterfaceObject.UIElementType.TEXTBOX || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATIC
					|| elementType == UserInterfaceObject.UIElementType.TEXTBOXDROPDOWN) {
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
			return newUIObject;
		}else {
			System.out.println("UIContainer does not exist");
			return null;
		}

	}

	public void updateContainerValues() {
		UIContainer visibleContainer;


		if(containerMap.get("workersrightpanel").visible) {
				visibleContainer = containerMap.get("workersrightpanel");
				populatePlayerData(Game.player,"workersrightpanel");
				

		}

	}
	public void setParentObject(String containerName, GameObject parentObject) {
		containerMap.get(containerName).parentObject = parentObject;
	}

	
	public void setRightPanel(String containerName) {
		String currentContainer = this.zoneMap.get(InterfaceController.InterfaceZone.RightSidePanel);
		if(currentContainer != containerName) {
			if(currentContainer == "workersrightpanel") {
				disableInterfaceContainer("workerassigntask");
			}
			System.out.println("Setting right panel");
			disableInterfaceContainer(currentContainer);
			currentContainer = containerName;
			enableInterfaceContainer(currentContainer);
			zoneMap.put(InterfaceController.InterfaceZone.RightSidePanel, containerName);
			
		}else {
			System.out.println("Attempted to setRightPanel with same active panel");
		}

	}
	
	public void enableInterfaceContainer(String containerName) {
		containerMap.get(containerName).visible = true;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}
		for(UIContainer cont: containerMap.get(containerName).containers.values()) {
			enableInterfaceContainer(cont);
		}

	}

	public void enableInterfaceContainer(UIContainer container) {
		container.visible = true;
		for(UserInterfaceObject uiObj : container.getObjects().values()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}
	}
	public void enableInterfaceContainer(String containerName,InterfaceZone zoneIn) {
		disableInterfaceContainer(zoneMap.get(zoneIn));
		if(zoneIn.equals(InterfaceZone.TopSidePanel)) {
			disableInterfaceContainer("workerslist");
			disableInterfaceContainer("workerassign");
		}
		containerMap.get(containerName).visible = true;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.add(uiObj);
		}
//		for(UIContainer cont: containerMap.get(containerName).containers.values()) {
//			enableInterfaceContainer(cont);
//		}
		zoneMap.put(zoneIn, containerName);

	}
	public void disableInterfaceContainer(String containerName) {
		if(containerMap.containsKey(containerName)) {
			containerMap.get(containerName).visible = false;
			for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
				Game.objectMap.enabledUIObjects.remove(uiObj);
			}
			for(UIContainer cont: containerMap.get(containerName).containers.values()) {
				disableInterfaceContainer(cont);
			}

		}else {
			System.out.println("Invalid container name: "+containerName);
		}

	}
	public void disableInterfaceContainer(UIContainer container) {
		container.visible = false;
		for(UserInterfaceObject uiObj : container.getObjects().values()) {
			Game.objectMap.enabledUIObjects.remove(uiObj);
		}
		for(UIContainer cont: container.containers.values()) {
			disableInterfaceContainer(cont);
		}


	}

	/**
	 * @param city
	 * @param containerName
	 *
	 *  Passes appropriate fields to city manager interfacew
	 */
//	public void passCityToInterfaceContainer(City city, String containerName) {
//		if(containerName.equals("citymanager")) {
//			UIContainer cityContainer = containerMap.get(containerName);
//			cityContainer.elements.get("citytitle").setElementText(city.name);
//		}
//	}

	public void passRStructureToInterfaceContainer(ResourceStructure rStructure, String containerName) {
		if(containerName.equals("resourcestructure")) {
			UIContainer resourceStructureContainer = containerMap.get(containerName);
			resourceStructureContainer.elements.get("structuretitle").setElementText(rStructure.name);
			resourceStructureContainer.elements.get("resourcestoredlabel").setElementText("Iron ore stored:");
			resourceStructureContainer.elements.get("resourcestoredvalue").setElementText(Integer.toString(rStructure.resourcesStored) + "/" + Integer.toString(rStructure.storageCap));
			resourceStructureContainer.elements.get("workerslabel").setElementText("Workers:");
			resourceStructureContainer.elements.get("workersvalue").setElementText(Integer.toString(rStructure.currentWorkers));
//			resourceStructureContainer.elements.get("workerticklabel").setElementText("Next worker:");
//			resourceStructureContainer.elements.get("workertickvalue").setElementText(Integer.toString(rStructure.tickCounter));
			resourceStructureContainer.containers.get("workerlist");


			for(UserInterfaceObject uiObj : containerMap.get(containerName).elements.values()) {
				uiObj.referenceObject =rStructure;
			}
//			enableInterfaceContainer("workerslist");
//			populateWorkersListContainer(rStructure);
		}
	}

	public void passResourceToInterfaceContainer(Resource resource, String containerName) {
		if (containerName.equals("resource")) {
			UIContainer resourceContainer = containerMap.get(containerName);
			resourceContainer.elements.get("resourcetitle").setElementText(resource.name);

			for(UserInterfaceObject uiObj : containerMap.get(containerName).elements.values()) {
				uiObj.referenceObject = resource;
			}
		}
	}
	
	//Called when player adopts a city to update UI elements
	public void cityAdopted() {
		System.out.println("cityAdopted");
		UIContainer cityPanel = containerMap.get("cityrightpanel");
		cityPanel.elements.remove("cityadoptbutton");
//		cityPanel.r
		
		
		
		
		
		
		/*
		 * Interfaces for city right panel in adopt stage
		 */
//		UIContainer citiesPanel = createUIContainer("citiesrightpanel",new Point(sidePanelPoint), new Point(0,20),0);
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "citiesrightpanel","citiesmenutitle","Cities","rightpanelheader",Color.WHITE,new Point (100,00),"");
//		citiesPanel.nextElementPos.x = Game.width - 160;
//		citiesPanel.nextElementPos.y = Game.topBarHeight + 150;
//		citiesPanel.elementSpacing.y = 50;
//				
//		UserInterfaceObject uiObj;
//		for(City city : Game.gameWorld.cityList) {
//			
////			addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarcities",ActionHandler::displayCitiesMenu,"Cities");
//			uiObj = addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,"citiesrightpanel", city.name+"citiesrightpanel",ActionHandler::selectCity,city.name);
//			uiObj.referenceObject = city;
//		}
//		
//		UIContainer cityPanel =  createUIContainer("cityrightpanel",new Point(sidePanelPoint), new Point(0,20),0);
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "cityrightpanel","citymenutitle","Undefined","rightpanelheader",Color.WHITE,new Point (100,0),"");
////		cityPanel.nextElementPos.y 
//		cityPanel.nextElementPos.x = Game.width - 140;
//		cityPanel.nextElementPos.y = Game.topBarHeight + 60;
//		
//		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "cityrightpanel", "cityadoptbutton",ActionHandler::adoptCity,"Adopt");		
//		
		
		
		
		
		
		
		
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
//			enableInterfaceContainer("workerslist");
//			populateWorkersListContainer(warehouse);
//			warehouseContainer.addContainer("workerslist", containerMap.get("workerslist"));

		}
	}
	
	public void populateCityData(City city, String containerName) {
		if(containerMap.containsKey(containerName)) {
			if(containerName.compareTo("cityrightpanel") == 0) {
				UIContainer cityRightPanel = containerMap.get(containerName);
				cityRightPanel.elements.get("citymenutitle").setElementText(city.name);
				if(Game.player.playerCity == null) {
					cityRightPanel.elements.get("cityadoptbutton").setWorldReference(city);
				}
					
				
			}	
		}
	}


	public void populatePlayerData(Player player, String containerName) {
		if(containerMap.containsKey(containerName)) {
			if(containerName.compareTo("workersrightpanel") == 0) {
				UIContainer workersRightPanel = containerMap.get(containerName);
				if(workersRightPanel.containers.containsKey("workerslist")) {
//					UIContainer availableWorkers = workersRightPanel.containers.get("workerslist");
					int offset = 0;
					UserInterfaceObject uiObj;
					for(Unit worker : Game.player.workers) {
						uiObj = addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "workerslist",worker.toString(),worker.actionTag,"primarygamefont",Color.WHITE,new Point (20,offset),ActionHandler::assignWorker);
						offset += 24;
						uiObj.referenceObject = worker;
					}
				}
				workersRightPanel.elements.get("availableworkers").setElementText("Available workers: "+player.availableWorkers+" / "+player.workerCount);
				
			}
		}
//		if(containerMap.containsKey("workerslist")) {
//			UIContainer workerslist = containerMap.get("workerslist");
//			boolean reenable = false;
//			if(workerslist.visible) {
//				reenable = true;
//			}
//			disableInterfaceContainer(workerslist);
////			System.out.println("disabled int");
////			containerMap.get("workerslist")
//			workerslist.elements.clear();
////			workerslist.parentObject = sourceOfWorkers;
//			int offset = 24;
//			for(Unit worker : Game.player.workers) {
//				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "workerslist",worker.toString(),worker.actionTag,"primarygamefont",Color.WHITE,new Point (-30,offset),"workerassign",worker);
//				offset += 24;
//			}
//			if(reenable) {
//				enableInterfaceContainer(workerslist);
//			}
//
////
//		}
		
		
		
	}

	public void showAssignWorkerDialogue(GameObject obj) {
		if((obj instanceof UserInterfaceObject)) {
			UserInterfaceObject uiObj = (UserInterfaceObject)obj;
			if(uiObj.referenceObject != null) {
				if(uiObj.referenceObject instanceof Unit) {
					enableInterfaceContainer("workerassigntask");
					System.out.println("Correct settings for showing dialogue");
					return;
				}
			}
		}
		System.out.println("Unable to show Assign Worker dialogue");
	}
	
	public void populateWorkerAssignContainer(GameObject objIn) {
		System.out.println("TEST assign cont");
		if(containerMap.containsKey("workerassign")) {
			UIContainer workerslist = containerMap.get("workerassign");
			if(objIn instanceof Unit) {
				Unit worker = (Unit) objIn;
				disableInterfaceContainer(workerslist);
				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXDROPDOWN, "workerassignmid","workerassignstartdd",worker.getStartStructName(),"primarygamefont",Color.WHITE,new Point (20,40),"workerassignstart");
				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXDROPDOWN, "workerassignmid","workerassigndestdd",worker.getDestStructName(),"primarygamefont",Color.WHITE,new Point (20,70),"workerassigndest");
				enableInterfaceContainer(workerslist);

			}

			Point spacingPos = new Point(10,50);
			for(City city : Game.gameWorld.cityList) {
//					City city = (City) obj;
					addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "workerassign",city.toString()+city.toString() ,city.name,"primarygamefont",Color.WHITE,spacingPos,"test",city);
//					System.out.println(Game.objectMap.get(obj.toString()+pos.toString()).coords);
					spacingPos.y = spacingPos.y + 20;
			}
//			UIContainer workerslist = containerMap.get("workerslist");
//			boolean reenable = false;
//			if(workerslist.visible) {
//				reenable = true;
//			}
//			disableInterfaceContainer(workerslist);
////			System.out.println("disabled int");
////			containerMap.get("workerslist")
//			workerslist.elements.clear();
////			workerslist.parentObject = sourceOfWorkers;
//			int offset = 24;
//			for(Unit worker : Game.player.workers) {
//				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "workerslist",worker.toString(),worker.actionTag,"primarygamefont",Color.WHITE,new Point (-30,offset),"workerassign");
//				offset += 24;
//			}
//			if(reenable) {
//				enableInterfaceContainer(workerslist);
//			}

//
		}
	}

	public void setDropdownParent(String clickTag, String containerName, City city){

		this.startStructureHolder = city;
		if(containerName.equals("dropdowncity")) {
//			System.out.println("TEST");
			if(containerMap.get("workerassignmid").containers.containsKey(containerName)) {
				if(clickTag.equals("workerassignstart")) {
					containerMap.get("workerassignmid").elements.get("workerassignstartdd").setElementText(city.name);
				}else if(clickTag.equals("workerassigndest")) {
//					System.out.println("TesT");
//					containerMap.get("workerassignmid"
				}

				updateContainerValues();
			}
		}
//		containerMap.get("workerassignmid").elements.get(key)
	}
	public InterfaceContext getUIContext(){
		return this.uiContext;
	}

	public void setUIContext(InterfaceContext uiCon) {
		this.uiContext = uiCon;
	}
	
	

	
	public void interfaceObjectClicked(UserInterfaceObject uiObj) {

		if(uiObj.isClicked()) {
//			if(uiContext == InterfaceContext.VolatileDropDown) {
//				disableInterfaceContainer("dropdown");
//				System.out.println("ivolatile");
//				uiContext = InterfaceContext.DEFAULT;
//			}
			uiObj.setClicked(false);
			uiObj = null;
		}else {
			uiObj.setClicked(true);
//			callClickAction(uiObj.clickTag);
		}
	}
	public void disableVolatile() {
//		System.out.println("DISABLE VOLATILE");
		this.disableInterfaceContainer("dropdown");
//		System.out.println(containerMap.get("dropdown").getObjects().size());
		for(UserInterfaceObject uiObj : volatileObjects) {
			if(uiObj.isClicked()) {
				uiObj.setClicked(false);
			}
		}
		volatileObjects.clear();
		uiContext = InterfaceContext.DEFAULT;
		
	}
	
	public void dropDown(ArrayList<Pair<String,Action>> itemList,Dimension itemSize, Point pos,Point elementSpacing, UserInterfaceObject parent) {

		UIContainer dDown = createUIContainer("dropdown",pos,elementSpacing,6);
		UserInterfaceObject uiObj;
		Point spacingPos = new Point(0,0);
		uiContext = InterfaceContext.VolatileDropDown;
		for(Pair<String,Action> itemPair : itemList) {

			uiObj = addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX,"dropdown", "dropDown"+itemPair.getKey(),itemPair.getKey(),"primarygamefont",Color.WHITE,spacingPos,itemPair.getValue());
			spacingPos.x = spacingPos.x + elementSpacing.x;
			spacingPos.y = spacingPos.y + elementSpacing.y;
			volatileObjects.add(uiObj);
		}
		enableInterfaceContainer(dDown);
	}
	
	
	/**
	 * @param containerName
	 * @param containerParent
	 * @param objectSet
	 * @param pos
	 * @param clickTag
	 */
	public void dropDownContainer(String containerName,String containerParent,ArrayList<GameObject> objectSet, Point pos,Point spacingPos, String clickTag) {
		if(!(containerMap.containsKey(containerParent))|| objectSet == null || pos == null) {
			System.out.println("Invalid dropDownContainer");
			return;
		}
		if(!containerMap.containsKey(containerName)) {
			
		
//			Point spacingPos = new Point(0,20);
			UIContainer uiCont = createUIContainer(containerName,pos, spacingPos,5);
	//		System.out.println(pos);
	//		for(GameObject obj : objectSet) {
	//			if(obj instanceof City) {
	//				City city = (City) obj;
	//				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, containerName,obj.toString()+pos.toString() ,city.name,"primarygamefont",Color.WHITE,spacingPos,clickTag,city);
	////				System.out.println(Game.objectMap.get(obj.toString()+pos.toString()).coords);
	//				spacingPos.y = spacingPos.y + 20;
	//			}
	//		}
			for(GameObject obj : objectSet) {
				
			}
	//
	
			containerMap.get(containerParent).addContainer(containerName, uiCont);
			if(containerMap.get(containerParent).visible) {
				enableInterfaceContainer(containerName);
			}


//		containerMap.get(containerName).elementSpacing = clickedObjec
//		if(clickedObject instanceof UserInterfaceObject) {
//			if(containerMap.containsKey(containerName)) {
//				Point spacing = new Point(clickedObject.coords);
//				for(UserInterfaceObject obj : containerMap.get(containerName).elements.values()) {
////					System.out.println("TEST");
//					spacing.setLocation(spacing.x + 0, spacing.y +20);
//					obj.setPosition(spacing);
////					enableInterfaceContainer(containerName);
////					return;
////					obj.setPosition(new Point(clickedObject.coords.x + spacing.x, clickedObject.coords.y + spacing.y));
////					spacing.x = spacing.x + containerMap.get(containerName).elementSpacing.x;
////					spacing.y = spacing.y + containerMap.get(containerName).elementSpacing.y;
//				}
//			}else {
//				System.out.println("Invalid container name");
//			}
//		}else {
//			System.out.println("dropDownContainer on non interface object");
//		}
//		enableInterfaceContainer(containerName);
	}else {
		
		if(containerMap.get(containerParent).visible) {
			enableInterfaceContainer(containerName);
		}
	}


	//Populates workers list (using structures)
//	public void populateWorkersListContainer(Structure sourceOfWorkers) {
//		if(containerMap.containsKey("workerslist")) {
//			UIContainer workerslist = containerMap.get("workerslist");
//			boolean reenable = false;
//			if(workerslist.visible) {
//				reenable = true;
//			}
//			disableInterfaceContainer(workerslist);
////			System.out.println("disabled int");
////			containerMap.get("workerslist")
//			workerslist.elements.clear();
//			workerslist.parentObject = sourceOfWorkers;
//			int offset = 24;
//			for(Unit worker : sourceOfWorkers.workers) {
//				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "workerslist",worker.toString() + worker.objID,worker.actionTag,"primarygamefont",Color.WHITE,new Point (-30,offset),"workerassign");
//				offset += 24;
//			}
//			if(reenable) {
//				enableInterfaceContainer(workerslist);
//			}
//
////
//		}
//	}
	}
	

	
	

	/**
	 *  Called on program start. Creates main menu UIContainer
	 */
	public void initaliseMainMenuInterface() {
//		Point pos = new Point((window.width/0.125),(window.height/0.67));
		
//		Action a = ActionHandler::displayControlMenu;
//		Action start = ActionHandler::startGame;
//		GameObject testObj = new GameObject(ObjectType.DEFAULT);
//		p.execute(this,testObj);

//		System.out.println(new Point( (int)(window.width*0.125),(int)(window.height*0.67)));
		UIContainer mainMenu = createUIContainer("mainmenu",new Point( (int)(window.width*0.4),(int)(window.height*0.4)), new Point(0,40),0);
		
//		Action a = ActionHandler::exitGame;
//		public UserInterfaceObject addCustomInterfaceObject(UserInterfaceObject.UIElementType elementType,Point pos,String containerName, String objectKey,  String objectImage, Dimension dimIn, boolean clickable) {
		UserInterfaceObject startBtn = addCustomInterfaceObject(UserInterfaceObject.UIElementType.CUSTOM,/*new Point((int)(Game.width *0.5),(int)(Game.height * 0.5))*/ new Point(0,0),"mainmenu", "newgamebutton","startbtn0",new Dimension(320,100),true);
		startBtn.setPosition(new Point( (int)(window.width*0.4),(int)(window.height*0.4+120)));
		startBtn.hoverImage = Game.objectMap.getImage("startbtn1");
		//		startBtn.setHoverImage("startbtn0")
		startBtn.setClickAction(ActionHandler::startGame);
		mainMenu.nextElementPos.setLocation(new Point((int)(Game.width*0.9),(int)(Game.height*0.9)));
		UserInterfaceObject quitBtn = addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu", "exitbutton", ActionHandler::exitGame,"Quit");
//		quitBtn.setPosition(new Point( (int)(window.width*0.4+300),(int)(window.height*0.4+300)));
//		public void addInterfaceObject(UserInterfaceObject.UIElementType elementType,String containerName, String objectKey,  ClickAction clickAction, String buttonText) {
		
//		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu","exitbutton2",ClickAction::ExitGame,"Quit");
		
		enableInterfaceContainer("mainmenu");
	}
	
	

	/**
	 *  Called when the game starts, creates all the UIContainers for the main game
	 */
	/**
	 *
	 */
	public void initialiseMainGameInterface() {

		//Creating text overlay instructing player
		createUIContainer("textoverlay",new Point(0,0),new Point(0,0),7);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT,"textoverlay","maininstruction","Choose a city","largeheading",Color.WHITE,new Point((Game.width/2)-100,80),"");
		enableInterfaceContainer("textoverlay");
	
		createUIContainer("dropdown",new Point(0,0),new Point(0,0),6);
		
		
		initialiseTopMenuBar();
		
		
		
		
		//Initialise interfaces accessed from top menu bar buttons
		Point sidePanelPoint = new Point((int)(window.width-Game.sideBarWidth),Game.topBarHeight*2); // Point used to position objects relative to right side menu
		
		initialiseWorkersSideMenu(sidePanelPoint); 
		initialiseConstructionSideMenu(sidePanelPoint); 
		initialiseCitiesSideMenu(sidePanelPoint);
		initialiseCitySideMenu(sidePanelPoint);
		
		
		
		
		/*
		 * MONEY DISPLAY CODE

		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "topmenubar","moneylabel","$ ","primarygamefont",Color.WHITE,new Point(70,12),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "topmenubar","moneyvalue","undefined","primarygamefont",Color.YELLOW,new Point (100,12),"");
		 */






		/*
		 * Interfaces for buildings in the world
		 */
		createUIContainer("resourcestructure", new Point((int)(window.width*0.9),(int)(window.height*0.13)), new Point(0,30),1);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","structuretitle","undefined","primarygamefont",Color.WHITE,new Point (0,-50),"");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "resourcestructure", "addworkerbtn", "addWorker", "Assign");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE, "resourcestructure","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (-30,40),"");

		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workersvalue","undefined","primarygamefont",Color.WHITE,new Point (130,50),"");
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","resourcestoredlabel","undefined","primarygamefont",Color.WHITE,new Point (100,0));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE, "resourcestructure","resourcestoredlabel","undefined","primarygamefont",Color.WHITE,new Point (-30,10),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","resourcestoredvalue","undefined","primarygamefont",Color.WHITE,new Point (130,20),"");
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (0,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workerslistlabel","Workers","topbarfont",Color.WHITE,new Point (30,260),"");

		// Resource
		createUIContainer("resource", new Point(1450,120), new Point(0,30), 1);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resource", "resourcetitle", "undefined", "primarygamefont", Color.WHITE, new Point (0, -50), "");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "resource", "buildRStructureBtn", "buildRStructure", "Build Structure");



		createUIContainer("warehouse", new Point((int)(window.width*0.9),(int)(window.height*0.13)), new Point(0,30),0);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","structuretitle","undefined","primarygamefont",Color.WHITE,new Point (0,-50),"");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "warehouse", "addworkerbtn", "addWorker", "Assign");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE, "warehouse","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (-30,40),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","workersvalue","undefined","primarygamefont",Color.WHITE,new Point (130,50),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","workerslistlabel","Workers","topbarfont",Color.WHITE,new Point (30,260),"");

	}
	
	
	//Initialises top menu bar of interface
	public void initialiseTopMenuBar() {
		UIContainer topMenuBar = createUIContainer("topmenubar", new Point((int)(window.width*0.10),4), new Point(128,0),0);
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarmenu", ActionHandler::displayControlMenu, "Menu");
		topMenuBar.nextElementPos = new Point(topMenuBar.nextElementPos.x + 2*topMenuBar.elementSpacing.x,topMenuBar.nextElementPos.y + 2*topMenuBar.elementSpacing.y);
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarlabour", ActionHandler::displayWorkersMenu, "Workers");

		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarconstructions",ActionHandler::displayConstructionMenu, "Construction");
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarcities",ActionHandler::displayCitiesMenu,"Cities");
	}

	public void initialiseWorkersSideMenu(Point sidePanelPoint) {
		/*
		 * Interfaces for workers right panel
		 */
		UIContainer workersPanel = createUIContainer("workersrightpanel",new Point(sidePanelPoint),new Point(0,20),0);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersrightpanel","workersmenutitle","Workers","rightpanelheader",Color.WHITE,new Point (100,00),"");
		UIContainer workersAssignSubpanel = createUIContainer("workerslist",new Point(sidePanelPoint),new Point(0,20),0);
		workersAssignSubpanel.nextElementPos.y = workersPanel.nextElementPos.y + 300;
		
		workersPanel.addContainer("workerslist", workersAssignSubpanel);
		workersPanel.nextElementPos.x = Game.width - 120;
		workersPanel.nextElementPos.y = Game.topBarHeight + 32;
		
//		addInterfaceObject(UserInterfaceObject.UIElementType.TEXTBOXT,"workerslist","hireworkerbutton",ActionHandler::assignWorker,"Idle");

		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersrightpanel","availableworkers","Available workers: ","primarygamefont",Color.WHITE,new Point (00,50),"");
		
		workersPanel.nextElementPos.x = Game.width - 80;
		workersPanel.nextElementPos.y = Game.topBarHeight + 100;
		
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"workersrightpanel","hireworkerbutton",ActionHandler::hireWorker,"Hire");
		
		UIContainer workersAssignSubpanelBackground = createUIContainer("workerassigntaskbackground",new Point(sidePanelPoint),new Point(0,20),0);
		addCustomInterfaceObject(UserInterfaceObject.UIElementType.CUSTOM,new Point(-380,((int)(sidePanelPoint.y + Game.height*0.4))),"workerassigntaskbackground","workerassigntaskbackground","assigntaskbox",new Dimension(300,250),true);
		workersAssignSubpanel = createUIContainer("workerassigntask",new Point(sidePanelPoint),new Point(0,20),1);
		workersAssignSubpanel.addContainer("workersassigntaskbackground", workersAssignSubpanelBackground);
//		public void addCustomInterfaceObject(UserInterfaceObject.UIElementType elementType,Point pos,String containerName, String objectKey,  String objectImage, Dimension dimIn, boolean clickable) {

		UserInterfaceObject assignTaskXBtn = addCustomInterfaceObject(UserInterfaceObject.UIElementType.CUSTOM,new Point(-70,((int)(sidePanelPoint.y + Game.height*0.4 + 10))),"workerassigntask","workerassigntaskclose","xbutton",new Dimension(24,24),true);
//		UserInterfaceObject assignTaskStart = addCustomInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,new Point(-70,((int)(sidePanelPoint.y + Game.height*0.4 + 10))),"workerassigntask","workerassigntaskstart","xbutton",new Dimension(24,24),true);

		UserInterfaceObject assignTaskStart = addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,new Point((int)(Game.width*0.5),(int)(Game.height*0.5)),"workerassigntask", "workerassigntaskstart",ActionHandler::assignWorkerStart,"Start");
//		workersAssignSubpanel.
//		assignTaskStart.setPosition(new Point((int)(Game.width*0.5),(int)(Game.height*0.5)));
//		public UserInterfaceObject addInterfaceObject(UserInterfaceObject.UIElementType elementType, Point pos,String containerName, String objectKey,  Action action, String buttonText) {
		
		//		assignTaskXBtn.set
		assignTaskXBtn.setClickAction(ActionHandler::disableAssignTaskBox);
	}

	public void initialiseConstructionSideMenu(Point sidePanelPoint) {
		createUIContainer("constructionrightpanel",new Point(sidePanelPoint), new Point(0,20),0);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "constructionrightpanel","constructionmenutitle","Construction","rightpanelheader",Color.WHITE,new Point (100,00),"");

	}
	public void initialiseCitiesSideMenu(Point sidePanelPoint) {
		UIContainer citiesPanel = createUIContainer("citiesrightpanel",new Point(sidePanelPoint), new Point(0,20),0);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "citiesrightpanel","citiesmenutitle","Cities","rightpanelheader",Color.WHITE,new Point (100,00),"");
		citiesPanel.nextElementPos.x = Game.width - 160;
		citiesPanel.nextElementPos.y = Game.topBarHeight + 150;
		citiesPanel.elementSpacing.y = 50;
				
		UserInterfaceObject uiObj;
		for(City city : Game.gameWorld.cityList) {
			
//			addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarcities",ActionHandler::displayCitiesMenu,"Cities");
			uiObj = addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,"citiesrightpanel", city.name+"citiesrightpanel",ActionHandler::selectCity,city.name);
			uiObj.referenceObject = city;
		}
	}	
	public void initialiseCitySideMenu(Point sidePanelPoint) {
		
		
		UIContainer cityPanel =  createUIContainer("cityrightpanel",new Point(sidePanelPoint), new Point(0,20),0);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "cityrightpanel","citymenutitle","Undefined","rightpanelheader",Color.WHITE,new Point (100,0),"");
//		cityPanel.nextElementPos.y 
		cityPanel.nextElementPos.x = Game.width - 140;
		cityPanel.nextElementPos.y = Game.topBarHeight + 60;

		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "cityrightpanel", "cityadoptbutton",ActionHandler::adoptCity,"Adopt"); 
		
		cityPanel.nextElementPos.x = Game.width - 180;
		cityPanel.nextElementPos.y = Game.topBarHeight + 400;
		cityPanel.elementSpacing.y = 36;
		UserInterfaceObject uiObj = addInterfaceObject(UserInterfaceObject.UIElementType.CITYBUTTON,"cityrightpanel", "marketbutton",ActionHandler::selectCity,"Market");
		 uiObj = addInterfaceObject(UserInterfaceObject.UIElementType.CITYBUTTON,"cityrightpanel", "companiesbutton",ActionHandler::selectCity,"Companies");
		 uiObj = addInterfaceObject(UserInterfaceObject.UIElementType.CITYBUTTON,"cityrightpanel", "forumbutton",ActionHandler::selectCity,"Forum");
//		uiO.setWorldReference(obj);
	}


}


