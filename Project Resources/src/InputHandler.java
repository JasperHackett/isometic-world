import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Jasper
 *
 */
public class InputHandler implements MouseListener, MouseMotionListener {

	private Point mousePressPos;
	private boolean dragEnabled;
	private GameObject hoveredObject;
	private GameObject mousePressedObject;
	private GameObject clickedObject;
	private InterfaceController ui;
	Integer planeOffsetX;
	Integer planeOffsetY;
//	private GameObject referenceObject;
	private Stack<UserInterfaceObject> clickedInterfaceObjects = new Stack<UserInterfaceObject>();

	
	InputHandler(InterfaceController uiIn){
		super();
		this.ui = uiIn;
		planeOffsetX = Game.xOffset;
		planeOffsetY = Game.yOffset;
	}


	
	public boolean checkContains(Pair<Dimension,Point> pairIn, Point mousePosition) {
			if(pairIn.getValue().getX() < mousePosition.getX() && pairIn.getValue().getY() < mousePosition.getY()
					&& pairIn.getValue().getX()+pairIn.getKey().getWidth() > mousePosition.getX()
					&& pairIn.getValue().getY()+pairIn.getKey().getHeight() > mousePosition.getY() ){

				return true;
			}
			return false;
	}
	public boolean checkContains(Point pointIn, Dimension dimensionIn, Point mousePosition) {
		if(pointIn.getX() < mousePosition.getX() && pointIn.getY() < mousePosition.getY()
				&& pointIn.getX()+dimensionIn.getWidth() > mousePosition.getX()
				&& pointIn.getY()+dimensionIn.getHeight() > mousePosition.getY() ){

			return true;
		}
		return false;
}

	@Override
	public void mouseDragged(MouseEvent e) {


//		if(checkContains((new Pair<Dimension,Point>(new Dimension(1400,700), new Point(100,100))), e.getPoint())){
			if(dragEnabled == true && Game.currentState == Game.STATE.Game) {
				Game.gameWorld.offsetDisplay(mousePressPos,e.getPoint());
//			}

		}

	}


	@Override
	public void mouseMoved(MouseEvent e) {
		Point iso2D = toGrid(Game.gameWorld.getWorldPosition(e.getPoint()));
		iso2D.setLocation(iso2D.getX() -975, iso2D.getY() + 975);
		iso2D.setLocation((int) iso2D.getX()/32, (int) iso2D.getY()/32);
		Game.objectMap.getTextObject("globalMousePosText").setText("Global mouse position: ["+ e.getX() + "," + e.getY()+ "]");
		Game.objectMap.getTextObject("worldMousePosText").setText("World mouse position: [" + (int) Game.gameWorld.getWorldPosition(e.getPoint()).getX() + "," +  (int) Game.gameWorld.getWorldPosition(e.getPoint()).getY() + "]");
		Game.objectMap.getTextObject("isoMousePosText").setText("Iso mouse position: [" + (int) iso2D.getX() + "," + (int) iso2D.getY() + "]");
		checkHover(e.getPoint());



	}



	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePressPos = e.getPoint();
		
		//Check all interface objects for containing mouse position
		try {
			Game.sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i = Game.userInterface.zIndex.size()-1; i >= 0 ; i--) {
			for(UserInterfaceObject uiObj : Game.objectMap.getUIIndex(i)) {
				if(uiObj.isClickable()) {
					if(checkContains(uiObj.getPosition(),e.getPoint())) {
						//Interface object moused down on
						mousePressedObject = uiObj;
						Game.sem.release();
						return;
					}
				}
			}
		}
		Game.sem.release();
		
		
		//Check game objects for mouse press			
		if(Game.currentState== Game.STATE.Game) {
			
			//iso2D is the mouse position isometrically converted. Used for mouse detection on iso grid. Uses magic number
			Point iso2D = toGrid(Game.gameWorld.getWorldPosition(e.getPoint()));
			iso2D.setLocation(iso2D.getX() - 975, iso2D.getY() + 975);
			iso2D.setLocation((int) iso2D.getX()/32, (int) iso2D.getY()/32);
			
			//Uses absolute point
			if(checkContains((new Pair<Dimension,Point>(new Dimension(1400,700), new Point(100,100))), e.getPoint())){
				dragEnabled = true;
			}

			if(checkContains(Game.gameWorld.getMainDisplayCoords(),e.getPoint())) {
				IsometricTile tile = Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY());
				if(tile != null) {
					if ((tile.getEntityOnTile() != null)) {
						mousePressedObject = tile.getEntityOnTile();
					} else {
						mousePressedObject = tile;
					}
				}
			}
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(mousePressedObject != null) {
			if(checkContains(mousePressedObject.getPosition(),e.getPoint())) {
				if(mousePressedObject instanceof UserInterfaceObject) {
					UserInterfaceObject uiObj = (UserInterfaceObject) mousePressedObject;
					if(uiObj.isClickable()) {
						if(!clickedInterfaceObjects.contains(mousePressedObject)) {
//							clickedInterfaceObjects.push(uiObj);
							uiObjectClicked(uiObj);
						}
					}
					
				}else if(mousePressedObject instanceof WorldObject){
					WorldObject worldObj = (WorldObject) mousePressedObject;
					if(worldObj.isClickable()) {
						worldObjectClicked(worldObj);
					}
					
				}else {
					System.out.println("Object clicked that is not a WorldObject or UserInterfaceObject");
				}
				
			}
		}
		
		
		dragEnabled = false;
		mousePressPos = null;
		mousePressedObject = null;
		Game.gameWorld.staticWorldPoint = null;
	}


	@Override
	public void mouseEntered(MouseEvent e) {

	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public Point toIsometric(Point pointIn) {
		Point tempPoint = new Point(0,0);
		tempPoint.x =  pointIn.x - pointIn.y;
		tempPoint.y = (pointIn.x + pointIn.y) /2;


		return(tempPoint);
	}
	public Point toGrid(Point pointIn) {
		Point tempPoint = new Point(0,0);

		tempPoint.x = (2*pointIn.y + pointIn.x) / 2;
		tempPoint.y = (2*pointIn.y - pointIn.x) / 2;

		return(tempPoint);
	}
	//Checks for the object the mouse may be hovering over
	public void checkHover(Point mousePos) {


		
		/* CHECK HOVER FOR ISOMETRIC OBJECTS*/
		//Convert mouse position to isometric
		Point iso2D = toGrid(Game.gameWorld.getWorldPosition(mousePos));
		iso2D.setLocation((iso2D.getX() - 976), (iso2D.getY() + 976));
		iso2D.setLocation((int) iso2D.getX() / 32, (int) iso2D.getY() / 32);

		GameObject tempObj = null;
		
		for(int i = Game.userInterface.zIndex.size()-1; i >= 0 ; i--) {
			for (UserInterfaceObject uiObj : Game.objectMap.getUIIndex(i)) {
				if(checkContains(uiObj.getPosition().getValue(),uiObj.getPosition().getKey(),mousePos)){

					tempObj = uiObj;
					
					if(hoveredObject == null) {
						hoveredObject = tempObj;
					}
					
					
					if(tempObj.equals(hoveredObject)) {
//						System.out.println(uiObj.clickTag);
//						System.out.println(hoveredObject.objID);
						hoveredObject.setHovered(true);
	//					return;
	//					System.out.println(hoveredObject.clickable);
					}else {
						hoveredObject.disableHover();
						this.hoveredObject = tempObj;
						this.hoveredObject.hoverAction();
					}
	


	
					return;
				}
			}
		}

		

		//Check iso coordinate is within world bounds (potentially useless)
		if(Game.currentState == Game.STATE.Game) {
			//This should check for inside iso world as well as inside main display
			if(checkContains(Game.gameWorld.getMainDisplayCoords(),mousePos)) { 
//				iso2D.getX() >= 0 && iso2D.getY() >= 0 && iso2D.getX() < Game.gameWorld.isoDims.width && iso2D.getY() < Game.gameWorld.isoDims.height
				tempObj = Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY());
				

				if(tempObj != null) {
					if((tempObj.type == ObjectType.WORLD)){

						if(hoveredObject == null) {
							hoveredObject = tempObj;
						}

						if(tempObj.equals(hoveredObject)) {
							hoveredObject.hoverAction();
						}else {
							hoveredObject.disableHover();
							this.hoveredObject = tempObj;
							this.hoveredObject.hoverAction();

						}
					}

				}
			}
		}



		if(tempObj == null && hoveredObject != null) {
			hoveredObject.setHovered(false);
			hoveredObject = null;
		}
//		GameObject tempObj = null;
	}
	
	
	
	public void entityClicked(Entity entity) {
		
	}
	
	public void uiObjectClicked(UserInterfaceObject uiObj) {
		clickedObject = uiObj;
		
//		if(uiObj.referenceObject != null) {
//			this.referenceObject = uiObj.referenceObject;
//		}
		if(ui.uiContext == InterfaceController.InterfaceContext.VolatileDropDown) {
//			if
		}
		
		if(this.clickedObject.isClicked()) {

			this.clickedObject.setClicked(false);
			this.clickedObject = null;
		}else {
			this.clickedObject.setClicked(true);
			callClickAction(uiObj.clickTag);
		}

	}
	
	public void worldObjectClicked(WorldObject objIn) {
		
//		System.out.println("worldObj test");
		if(clickedObject != null) {
			if(objIn.isClicked()) {
				objIn.setClicked(false);
			}else {
				objIn.setClicked(true);
				callClickAction(objIn.clickTag);
			}
			
		}else {
			objIn.setClicked(true);
			callClickAction(objIn.clickTag);
		}
		

	}

	
	
	/**
	 * @param clickTag
	 * Stores all possible actions when a button is clicked
	 */
	public void callClickAction(String clickTag) {
		
		if(clickTag == null) {
			return;
		}

		if(clickTag.isEmpty()) {
			System.out.println("Blank click tag");
			return;
		}

		if(clickTag.equals("newgame")) {
			System.out.println("New game button clicked");
//			Game.gameWorld = new World();
			Game.userInterface.initialiseMainGameInterface();
			Game.userInterface.disableInterfaceContainer("mainmenu");
			Game.userInterface.enableInterfaceContainer("topmenubar");
			Game.currentState = Game.STATE.Game;
//			Game.gameWorld.updateDisplay();
//		}else if (clickTag.equals("exit")) {
//			System.out.println("Exiting");
//			System.exit(0);
//			
		}else if (clickTag.equals("citybtn")){
			UserInterfaceObject uiObject = (UserInterfaceObject) clickedObject;
			if(clickedObject == null) {
				System.out.println("clickedobj null");
			}
			if(uiObject != null) {
				System.out.println("ui not null");
				City city = null;
				if(uiObject.referenceObject != null) {
					 city = (City) uiObject.referenceObject;
				}
				Game.userInterface.passCityToInterfaceContainer(city, "citymanager");
				Game.userInterface.enableInterfaceContainer("citymanager",InterfaceController.InterfaceZone.TopSidePanel);
				uiObject.disableClick();
				clickedObject = city;
			}else {
				System.out.println("ui null");
			}

			
			
//			Game.userInterface.enableInterfaceContainer("citymanager");
		}else if (clickTag.equals("addWorker")) {
			clickedObject.currentlyClicked = false;
//			clickedObject = null;
//			Structure clickedStructure = (Structure)clickedEntity;
//			clickedStructure.addWorker();
			Game.userInterface.updateContainerValues();
		}else if(clickTag.equals("citiesmenu")) {
			Game.userInterface.enableInterfaceContainer("citiesmenu",InterfaceController.InterfaceZone.TopSidePanel);
		}else if(clickTag.equals("constructionmenu")) {
			clickedObject.disableClick();
			Game.userInterface.enableInterfaceContainer("constructionmenu",InterfaceController.InterfaceZone.TopSidePanel);
		}else if(clickTag.equals("buildironmine")) {
//			System.out.println("test");
//			constructionOutline.isoPoint = new Point(5,5);
//			System.out.println(constructionOutline.isoPoint);
//			currentState = InputState.CONSTRUCTION;
//			Game.gameWorld.const
//			for(String str : Game.objectMap.keySet()) {
//				System.out.println(str);
//			}
//			WorldObject ironMine = new WorldObject();
//			ironMine.setProperties(new Dimension(128,64), new Point(600,600),"ironhut");
//			Game.objectMap.addWorldObject("ironhuthover", ironMine);
//			UserInterfaceObject uiObject = (UserInterfaceObject) clickedObject;
//			if(clickedObject == null) {
//				System.out.println("clickedobj null");
//			}
//			if(uiObject != null) {
//				System.out.println("ui not null");
//				Structure structure = null;
//				if(uiObject.referenceObject != null) {
//					 structure = (Structure) uiObject.referenceObject;
//				}
////				Game.userInterface.passCityToInterfaceContainer(city, "citymanager");
////				Game.userInterface.enableInterfaceContainer("citymanager",InterfaceController.InterfaceZone.TopSidePanel);
////				uiObject.disableClick();
////				clickedObject = city;
//			}else {
//				System.out.println("ui null");
//			}
		} else if (clickTag.equals("buildRStructure")) {
			UserInterfaceObject uiObj = (UserInterfaceObject)clickedObject;
			Resource resource = (Resource)uiObj.referenceObject;
			resource.addStructure();
		} else if(clickTag.equals("workersmenu")) {
			clickedObject.disableClick();
			Game.userInterface.enableInterfaceContainer("workersmenu",InterfaceController.InterfaceZone.TopSidePanel);
			Game.userInterface.populateWorkersListContainer();
			Game.userInterface.enableInterfaceContainer("workerslist");
		}else if(clickTag.equals("hireworker")) {
			Game.player.hireWorker();
			Game.userInterface.populateWorkersListContainer();
		}else if(clickTag.equals("workerassign")) {
			System.out.println("TEST!!");
//			Game.userInterface.populateWorkerAssignContainer(referenceObject);
			Game.userInterface.enableInterfaceContainer("workerassign");
//			System.out.println(Game.userInterface.containerMap.get("worke);
		}else if(clickTag.equals("saveworkerassign")) {
			Game.userInterface.disableInterfaceContainer("workerassign");
			System.out.println("save worker assign");
		}else if(clickTag.equals("cancelworkerassign")) {
			Game.userInterface.disableInterfaceContainer("workerassign");
		} else if(clickTag.equals("workerassignstart")) {
//			System.out.println("Test");
//			if(referenceObject instanceof Unit) {
//				System.out.println(clickedObject.objID);
//				if(clickedObject != null) {
//					Game.userInterface.dropDownContainer("dropdowncitystart", "workerassignmid",new ArrayList<GameObject>(Game.gameWorld.cityList), clickedObject.coords, "workerassignstart");
//					
//				}
//				
//			}else if(referenceObject instanceof City) {
//				City city = (City) referenceObject;
////				Game.userInterface.queueActionStrcture("workerassignstart", city);
////				Game.userInterface.setStartStructureHolder(city);
//				Game.userInterface.setDropdownParent("workerassignstart","dropdowncitystart",city);
//				Game.userInterface.disableInterfaceContainer("dropdowncitystart");
//			}
		}else if(clickTag.equals("workerassigndest")) {
////			System.out.println("Test");
//			if(referenceObject instanceof Unit) {
//				System.out.println(clickedObject.objID);
//				if(clickedObject != null) {
//					Game.userInterface.dropDownContainer("dropdowncitydest", "workerassignmid",new ArrayList<GameObject>(Game.gameWorld.cityList), clickedObject.coords, "workerassigndest");
//					
//				}
//				
//			}else if(referenceObject instanceof City) {
//				City city = (City) referenceObject;
//				Game.userInterface.setDropdownParent("workerassigndest","dropdowncitydest",city);
//				Game.userInterface.disableInterfaceContainer("dropdowncitydest");
//			}
		}


	}
}
