import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Map;

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
	private GameObject clickedObject;
	private Entity clickedEntity;
	private Entity constructionOutline;
	
	private enum InputState{
		DEFAULT,
		CONSTRUCTION,
		SELECT
	};
	InputState currentState = InputState.DEFAULT;


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
		iso2D.setLocation(iso2D.getX() - 975, iso2D.getY() + 975);
		iso2D.setLocation((int) iso2D.getX()/32, (int) iso2D.getY()/32);
		Game.objectMap.getTextObject("globalMousePosText").setText("Global mouse position: ["+ e.getX() + "," + e.getY()+ "]");
		Game.objectMap.getTextObject("worldMousePosText").setText("World mouse position: [" + (int) Game.gameWorld.getWorldPosition(e.getPoint()).getX() + "," +  (int) Game.gameWorld.getWorldPosition(e.getPoint()).getY() + "]");
		Game.objectMap.getTextObject("isoMousePosText").setText("Iso mouse position: [" + (int) iso2D.getX() + "," + (int) iso2D.getY() + "]");
		checkHover(e.getPoint());



	}
	public void setConstructionOutline(Entity outline) {
		this.constructionOutline = outline;
	}


	@Override
	public void mouseClicked(MouseEvent e) {

		
		/*
		 * Working code for button presses
		 */
				String mouseButton = "not";
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseButton = "left";
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					mouseButton = "middle";
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					mouseButton = "right";
				}
			if(mouseButton.equals("right")) {
				if(currentState == InputState.CONSTRUCTION) {
					currentState = InputState.DEFAULT;
				}
			}

		//iso2D is the mouse position isometrically converted. Used for mouse detection on iso grid. Uses magic number
		Point iso2D = toGrid(Game.gameWorld.getWorldPosition(e.getPoint()));
		iso2D.setLocation(iso2D.getX() - 975, iso2D.getY() + 975);
		iso2D.setLocation((int) iso2D.getX()/32, (int) iso2D.getY()/32);

		
		
		//Check all interface objects for click
		try {
			Game.sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		for(UserInterfaceObject uiObj : Game.objectMap.getEnabledUIObjects()) {
			if(uiObj.isClickable()) {
				if(checkContains(uiObj.getPosition(),e.getPoint())) {
					if(clickedObject != null) {
						if(uiObj.referenceObject != null) {
							if(!uiObj.referenceObject.equals(clickedObject)) {
								clickedObject.disableClick();
							}
								
						}else {
							clickedObject.disableClick();
						}
						
					}
					uiObjectClicked(uiObj);
					Game.sem.release();
					return;
				}
			}
		}
		
		switch(currentState) {
		case SELECT:
			
			break;
		case CONSTRUCTION:
			System.out.println("tesT");

			
			
			break;
		case DEFAULT:
			

			
			
			
			
			
			
			break;
		}
		
		

		Game.sem.release();


		if(Game.currentState == Game.STATE.Menu) {
//			for(GameObject obj : Game.objectMap.getOtherObjects().values()) {
//				if(obj.isClickable()){
//					if(checkContains(obj.getPosition(),e.getPoint())) {
//						callClickAction(obj.clickTag);
//					}
//				}
//			}
		}else if(Game.currentState== Game.STATE.Game) {

			if(checkContains(Game.gameWorld.getMainDisplayCoords(),e.getPoint())) {

				IsometricTile tile = Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY());
//				Boolean entityClicked = tile.getEntityOnTile() != null;
				
				if(tile != null) {
					if(clickedObject != null && tile != null) {
						
						if ((tile.getEntityOnTile() != null)&& clickedObject.type != ObjectType.TILE) {
							if (!tile.getEntityOnTile().equals(clickedObject)) {
								clickedObject.setClicked(false);
								clickedObject = null;
							}
						} else {
							if(!clickedObject.equals(tile)){
								clickedObject.setClicked(false);
								clickedObject = null;
							}
						}
	
	
	
					}
					if (tile.getEntityOnTile() != null) {
						clickedObject = tile.getEntityOnTile();
						clickedEntity = tile.getEntityOnTile();
					} else {
						clickedObject = tile;
					}
				
				}


				if(clickedObject != null) {
					if(this.clickedObject.isClicked()) {
						this.clickedObject.setClicked(false);
						this.clickedObject = null;
					}else {
						this.clickedObject.setClicked(true);
					}
				}
				



			}
		}


	}

	@Override
	public void mousePressed(MouseEvent e) {

//		if (!(e.getButton() == MouseEvent.BUTTON3)) {
//			return;
//		}

		this.mousePressPos = e.getPoint();

		if(checkContains((new Pair<Dimension,Point>(new Dimension(1400,700), new Point(100,100))), e.getPoint())){
			dragEnabled = true;
		}

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		dragEnabled = false;
		mousePressPos = null;
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

		//Check iso coordinate is within world bounds (potentially useless)
		if(Game.currentState == Game.STATE.Game) {
			//This should check for inside iso world as well as inside main display
			if(checkContains(Game.gameWorld.getMainDisplayCoords(),mousePos)) { 
//				iso2D.getX() >= 0 && iso2D.getY() >= 0 && iso2D.getX() < Game.gameWorld.isoDims.width && iso2D.getY() < Game.gameWorld.isoDims.height
				tempObj = Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY());
				
				if(currentState == InputState.CONSTRUCTION) {
					if(constructionOutline != null && tempObj != null) {

						WorldObject tempWObj = (WorldObject) tempObj;
						constructionOutline.isoPoint = tempWObj.isoPoint;
						constructionOutline.worldPoint = tempWObj.worldPoint;
//						constructionOutline.setP
					}
//					Game.objectMap.get(key)
				}

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

		for(UserInterfaceObject uiObj : Game.objectMap.getEnabledUIObjects()) {
			if(checkContains(uiObj.getPosition().getValue(),uiObj.getPosition().getKey(),mousePos)){
				tempObj = uiObj;

				if(tempObj.equals(hoveredObject)) {
					hoveredObject.setHovered(true);
				}

				if(hoveredObject == null) {
					hoveredObject = tempObj;
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
		if(this.clickedObject.isClicked()) {
			this.clickedObject.setClicked(false);
			this.clickedObject = null;
		}else {
			this.clickedObject.setClicked(true);
		}
		callClickAction(uiObj.clickTag);
	}

	/**
	 * @param clickTag
	 * Stores all possible actions when a button is clicked
	 */
	public void callClickAction(String clickTag) {

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
		}else if (clickTag.equals("exit")) {
			System.out.println("Exiting");
			System.exit(0);
			
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
			
			Structure clickedStructure = (Structure)clickedEntity;
			clickedStructure.addWorker();
			Game.userInterface.updateContainerValues();
		}else if(clickTag.equals("citiesmenu")) {
			Game.userInterface.enableInterfaceContainer("citiesmenu",InterfaceController.InterfaceZone.TopSidePanel);
		}else if(clickTag.equals("constructionmenu")) {
			clickedObject.disableClick();
			Game.userInterface.enableInterfaceContainer("constructionmenu",InterfaceController.InterfaceZone.TopSidePanel);
		}else if(clickTag.equals("buildironmine")) {
			System.out.println("test");
			constructionOutline.isoPoint = new Point(5,5);
			System.out.println(constructionOutline.isoPoint);
			currentState = InputState.CONSTRUCTION;
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
		}else if(clickTag.equals("workersmenu")) {
			clickedObject.disableClick();
			Game.userInterface.enableInterfaceContainer("workersmenu",InterfaceController.InterfaceZone.TopSidePanel);
		}else if(clickTag.equals("hireworker")) {
			Game.player.hireWorker();
			
		}


	}
}
