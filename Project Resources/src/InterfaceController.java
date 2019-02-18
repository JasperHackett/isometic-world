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
		public void addContainer(String containerName,UIContainer containerIn) {
			this.containers.put(containerName, containerIn);
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
	HashMap<InterfaceZone,String> zoneMap;
	Map<String,UIContainer> containerMap = new HashMap<String,UIContainer>();
	ArrayList<ArrayList<UIContainer>> zIndex = new ArrayList<ArrayList<UIContainer>>();
	

	
	public InterfaceController(){
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
	public UIContainer createUIContainer(String containerName, Point firstElementPos, Point elementSpacing,int zIndex) {
		UIContainer newContainer = new UIContainer(firstElementPos);
		newContainer.nextElementPos = firstElementPos;
		newContainer.elementSpacing = elementSpacing;
//		newContainer.visible = true;
		containerMap.put(containerName, newContainer);
		this.zIndex.get(zIndex).add(newContainer);
		return newContainer;
	}
	
	public UIContainer createUIContainer(String containerName, Point firstElementPos, Point elementSpacing,GameObject parentObject) {
		UIContainer newContainer = new UIContainer(firstElementPos);
		newContainer.nextElementPos = firstElementPos;
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
	public void addCustomInterfaceObject(UserInterfaceObject.UIElementType elementType,Point pos,String containerName, String objectKey,  String objectImage, Dimension dimIn, boolean clickable) {
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
			return;
		}
		
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
	
	public void addInterfaceObject(UserInterfaceObject.UIElementType elementType, Point pos,String containerName, String objectKey,  String clickTag, String buttonText) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey,elementType);
		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			Point newPos = new Point(objectsContainer.coords.x + pos.x, objectsContainer.coords.y + pos.y);
			if(objectsContainer.elementSpacing != null && objectsContainer.nextElementPos != null) {
				newUIObject.setProperties(newPos, clickTag, buttonText);
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
	public void addInterfaceTextObject(UserInterfaceObject.UIElementType elementType, String containerName, String objectKey, String text, String fontKey,Color textColor, Point pos, String clickTag) {
		UserInterfaceObject newUIObject = Game.objectMap.addUIObject(objectKey, elementType);
		newUIObject.clickTag = clickTag;
		newUIObject.clickable = true;

		if(containerMap.containsKey(containerName)) {
			UIContainer objectsContainer = containerMap.get(containerName);
			if(elementType == UserInterfaceObject.UIElementType.TEXTBOX || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE || elementType == UserInterfaceObject.UIElementType.TEXTBOXSTATIC) {
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
	
	public void updateContainerValues() {
		UIContainer visibleContainer;
		if(containerMap.get("resourcestructure").visible) {
			visibleContainer = containerMap.get("resourcestructure");
//			populateWorkersListContainer((Structure) visibleContainer.parentObject);
//			disableInterfaceContainer("resourcestructure");
//			enableInterfaceContainer("resourcestructure")
//			containerMap.get(containerName)
//			resourceStructureContainer.elements.get("workersvalue").setElementText(Integer.toString(p))
		}else if(containerMap.get("warehouse").visible) {
			visibleContainer = containerMap.get("warehouse");
//			populateWorkersListContainer((Structure) visibleContainer.parentObject);
//			populateWorkersListContainer((Warehouse) visibleContainer.parentObject);
			
//			enableInterfaceContainer("workerslist");
		}
		
		if(containerMap.get("workerslist").visible) {
//			System.out.println("test");
			if(containerMap.containsKey("workerslist")) {
//				containerMap.get("workerslist").elements.clear();
//				Structure currentStruct = (Structure) containerMap.get("workerslist").parentObject;

//				disableInterfaceContainer("workerslist");
//				enableInterfaceContainer("workerslist");
//				enableInterfaceContainer("workerslist");
//				warehouseContainer.addContainer("workerslist", containerMap.get("workerslist"));
	
				
			}
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
		containerMap.get(containerName).visible = false;
		for(UserInterfaceObject uiObj : containerMap.get(containerName).getObjects().values()) {
			Game.objectMap.enabledUIObjects.remove(uiObj);
		}
		for(UIContainer cont: containerMap.get(containerName).containers.values()) {
			disableInterfaceContainer(cont);
		}

	}
	public void disableInterfaceContainer(UIContainer container) {
		container.visible = false;
		for(UserInterfaceObject uiObj : container.getObjects().values()) {
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
			resourceStructureContainer.containers.get("workerlist");
			for(UserInterfaceObject uiObj : containerMap.get(containerName).elements.values()) {
				uiObj.referenceObject =rStructure;
			}
//			enableInterfaceContainer("workerslist");
//			populateWorkersListContainer(rStructure);
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
//			enableInterfaceContainer("workerslist");
//			populateWorkersListContainer(warehouse);
//			warehouseContainer.addContainer("workerslist", containerMap.get("workerslist"));

		}
	}
	
	
	public void populateWorkersListContainer() {

		if(containerMap.containsKey("workerslist")) {
			UIContainer workerslist = containerMap.get("workerslist");
			boolean reenable = false;
			if(workerslist.visible) {
				reenable = true;
			}
			disableInterfaceContainer(workerslist);
//			System.out.println("disabled int");
//			containerMap.get("workerslist")
			workerslist.elements.clear();
//			workerslist.parentObject = sourceOfWorkers;
			int offset = 24;
			for(Unit worker : Game.player.workers) {
				addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOX, "workerslist",worker.toString(),worker.actionTag,"primarygamefont",Color.WHITE,new Point (-30,offset),"workerassign");
				offset += 24;
			}
			if(reenable) {
				enableInterfaceContainer(workerslist);
			}

//			
		}
	}
	
	public void populateWorkerAssignContainer(GameObject objIn) {

		if(containerMap.containsKey("workerassign")) {
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
	
	/**
	 *  Called on program start. Creates main menu UIContainer
	 */
	/**
	 * 
	 */
	/**
	 * 
	 */
	public void initaliseMainMenuInterface() {
		createUIContainer("mainmenu",new Point(200,600), new Point(0,50),0);
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu", "newgamebutton","newgame","Start");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL,"mainmenu", "exitbutton","exit","Quit");
		enableInterfaceContainer("mainmenu");
	}
	
	/**
	 *  Called when the game starts, creates all the UIContainers for the main game
	 */
	public void initialiseMainGameInterface() {
		
		createUIContainer("topmenubar", new Point(384,4), new Point(128,0),0);
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarlabour", "workersmenu", "Workers");
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarconstruction", "constructionmenu", "Construction");
		addInterfaceObject(UserInterfaceObject.UIElementType.TOPBAR, "topmenubar", "topbarcities", "citiesmenu", "Cities");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "topmenubar","moneylabel","$ ","primarygamefont",Color.WHITE,new Point(70,12),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "topmenubar","moneyvalue","undefined","primarygamefont",Color.YELLOW,new Point (100,12),"");

		createUIContainer("citymanager", new Point(1450,120), new Point(0,50),0);
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "citymanager", "hellobtn", "hello", "Hello");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "citymanager","citytitle","undefined","primarygamefont",Color.WHITE,new Point (0,-100),"");
//		enableInterfaceContainer("cityinterface");
		
		
		createUIContainer("constructionmenu",new Point(1450,200), new Point(0,50),0);
		addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,"constructionmenu", "buildironmine","buildironmine","Iron Mine");
		
		createUIContainer("citiesmenu",new Point(1450,200), new Point(0,40),0);
		for(City city : Game.gameWorld.cityList) {
			addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM,"citiesmenu", city.name+"citiesmenu","citybtn",city.name,city);
		}
		
		createUIContainer("workerslist", new Point(1450,560),new Point(0,20),0);

		
		createUIContainer("workersmenu",new Point(1450,200), new Point(0,40),0);
		addInterfaceObject(UserInterfaceObject.UIElementType.MEDIUM, "workersmenu", "hireworker", "hireworker", "Hire Worker");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalworkerslabel","Total workers:","primarygamefont",Color.WHITE,new Point (0,20),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalworkersvalue","undefined","primarygamefont",Color.WHITE,new Point (80,20),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","availableworkerslabel","Idle workers:","primarygamefont",Color.WHITE,new Point (00,50),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","availableworkersvalue","undefined","primarygamefont",Color.WHITE,new Point (80,50),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalcostlabel","Cost:","primarygamefont",Color.WHITE,new Point (0,80),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","totalcostvalue","undefined","primarygamefont",Color.YELLOW,new Point (80,80),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workersmenu","workerslistlabel","Workers","topbarfont",Color.WHITE,new Point (40,330),"");
		
		createUIContainer("workerassign",new Point(1180,500),new Point(0,0),1);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "workerassign","structuretitle","Assign Task","mediumbuttonfont",Color.WHITE,new Point (100,20),"");
//		containerMap.get("workerassign").addContainer("workerassig, containerIn);
		addCustomInterfaceObject(UserInterfaceObject.UIElementType.CUSTOM,new Point (0,0),"workerassign","workerassignsmalluibox","smalluibox",new Dimension(200,320),false);
		
		createUIContainer("workerassignmid",new Point(1180,500),new Point(0,0),2);
		containerMap.get("workerassign").addContainer("workerassignmid", containerMap.get("workerassignmid"));

		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, new Point(120,270),"workerassignmid", "cancelworkerassign", "cancelworkerassign", "Cancel");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, new Point(30,270),"workerassignmid", "saveworkerassign", "saveworkerassign", "Save");



		createUIContainer("resourcestructure", new Point(1450,120), new Point(0,30),1);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","structuretitle","undefined","primarygamefont",Color.WHITE,new Point (0,-50),"");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "resourcestructure", "addworkerbtn", "addWorker", "Assign");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE, "resourcestructure","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (-30,40),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workersvalue","undefined","primarygamefont",Color.WHITE,new Point (130,50),"");
	
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","resourcestoredlabel","undefined","primarygamefont",Color.WHITE,new Point (100,0));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE, "resourcestructure","resourcestoredlabel","undefined","primarygamefont",Color.WHITE,new Point (-30,10),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","resourcestoredvalue","undefined","primarygamefont",Color.WHITE,new Point (130,20),"");
//		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (0,30));
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "resourcestructure","workerslistlabel","Workers","topbarfont",Color.WHITE,new Point (30,260),"");

		
		
		createUIContainer("warehouse", new Point(1450,120), new Point(0,30),0);
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","structuretitle","undefined","primarygamefont",Color.WHITE,new Point (0,-50),"");
		addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "warehouse", "addworkerbtn", "addWorker", "Assign");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXTBOXSTATICVALUE, "warehouse","workerslabel","undefined","primarygamefont",Color.WHITE,new Point (-30,40),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","workersvalue","undefined","primarygamefont",Color.WHITE,new Point (130,50),"");
		addInterfaceTextObject(UserInterfaceObject.UIElementType.TEXT, "warehouse","workerslistlabel","Workers","topbarfont",Color.WHITE,new Point (30,260),"");
		
	}
	
	



}
