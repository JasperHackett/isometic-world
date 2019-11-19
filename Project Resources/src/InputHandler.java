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
		iso2D.setLocation(iso2D.getX() -Game.offsetConstant, iso2D.getY() + Game.offsetConstant);
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
			iso2D.setLocation(iso2D.getX() - Game.offsetConstant, iso2D.getY() + Game.offsetConstant);
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
			
			if(ui.uiContext == InterfaceController.InterfaceContext.VolatileDropDown) {
				if(!checkVolatileClick(ui.volatileObjects,e.getPoint())) {
					ui.disableVolatile();
				}
			}

			if(checkContains(mousePressedObject.getPosition(),e.getPoint())) {
				if(mousePressedObject instanceof UserInterfaceObject) {
					UserInterfaceObject uiObj = (UserInterfaceObject) mousePressedObject;
					if(uiObj.isClickable()) {
//						if(!clickedInterfaceObjects.contains(mousePressedObject)) {
//							clickedInterfaceObjects.push(uiObj);
//							uiObjectClicked(uiObj, e.getPoint());
							objectClicked(uiObj, e.getPoint());
							return;

//						}
					}
					
				}else if(mousePressedObject instanceof WorldObject && (!checkContains(Game.objectMap.get("sidebar").getPosition(),e.getPoint()))){
					

					WorldObject worldObj = (WorldObject) mousePressedObject;
					if(worldObj.isClickable()) {
						objectClicked(worldObj, e.getPoint());
						return;
					}
					
				}else {
					System.out.println("Object clicked that is not a WorldObject or UserInterfaceObject");
				}
				
			}
		}else {
			if(clickedObject != null) {
				clickedObject.setClicked(false);
			}
			if(ui.uiContext == InterfaceController.InterfaceContext.VolatileDropDown) {
				ui.disableVolatile();
			}

		}
		
//		if(ui.uiContext == InterfaceController.InterfaceContext.VolatileDropDown) {
//			if(checkVolatileClick(ui.volatileObjects,e.getPoint())){
//				
//			}
//		}

		
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

//		System.out.println("Offset constant:"+Game.worldHeight);
		iso2D.setLocation((iso2D.getX() - Game.offsetConstant), (iso2D.getY() + Game.offsetConstant));
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
	
	

	
	public void objectClicked(GameObject obj, Point mousePos) {
		
//		if(clickedObject != null) {
//			if(clickedObject != obj && clickedObject.isClickable()) {
//				if(clickedObject instanceof City) {
//					disableClick(clickedObject);
//				}
//			}
//		}
//		if((obj instanceof UserInterfaceObject)) {
		if(obj.isClicked()) {

				disableClick(obj);
				System.out.println("Disabled click on: " + obj.objID);

//			}

		}else {
			if(obj instanceof UserInterfaceObject) {
				uiObjectClicked((UserInterfaceObject)obj,mousePos);
			}else if(obj instanceof WorldObject) {
				worldObjectClicked((WorldObject)obj);
			}else {
				
				System.out.println("Unknown object clicked");
			}
		}
		
		dragEnabled = false;
		mousePressPos = null;
		mousePressedObject = null;
		Game.gameWorld.staticWorldPoint = null;

		
		
	}
	
	public void worldObjectClicked(WorldObject obj) {
		
		
		if(clickedObject != null) {
			if(clickedObject instanceof WorldObject) {
				clickedObject.setClicked(false);
			}

		}
		clickedObject = obj;


		
		if(obj.isClicked()) {

			obj.setClicked(false);
			obj = null;
			clickedObject = null;
		}else {
			obj.setClicked(true);
		}
		clickedObject.setClicked(true);
		dragEnabled = false;
		mousePressPos = null;
		mousePressedObject = null;
		

	}
	
	public void uiObjectClicked(UserInterfaceObject uiObj,Point mousePos) {
		clickedObject = uiObj;

		
	
		if(ui.uiContext == InterfaceController.InterfaceContext.VolatileDropDown) {
			if(!checkVolatileClick(ui.volatileObjects,mousePos)){
				ui.disableVolatile();
			}else {
				this.clickedInterfaceObjects.push(uiObj);
				ui.interfaceObjectClicked(uiObj);
			}
		}else {
			ui.interfaceObjectClicked(uiObj);
		}

	}
	
	public void disableClick(GameObject obj) {

		obj.setClicked(false);
		dragEnabled = false;
		mousePressPos = null;
		mousePressedObject = null;
		if(obj == clickedObject) {
			clickedObject = null;
		}
		if(obj instanceof City) {
			for(UserInterfaceObject x : clickedInterfaceObjects) {
				x.setClicked(false);
			}
			clickedInterfaceObjects.clear();
		}
		if(obj instanceof UserInterfaceObject) {
			UserInterfaceObject uiObj = (UserInterfaceObject)obj;
			if(uiObj.clearsStack) {
				for(UserInterfaceObject x : clickedInterfaceObjects) {
					x.setClicked(false);
				}
				clickedInterfaceObjects.clear();
			}
		}
		Game.gameWorld.staticWorldPoint = null;
	}
	

	
	//Returns true if the mouse is inside a volatile object
	public boolean checkVolatileClick(ArrayList<UserInterfaceObject> volatileObjects, Point mousePos) {
		
		for(UserInterfaceObject uiObj : volatileObjects) {
			if(checkContains(uiObj.getPosition(),mousePos)){
				return true;
			}
		}
		
		return false;
	}
	


	
	
	/**
	 * @param clickTag
	 * Stores all possible actions when a button is clicked
	 */
	public void callClickAction(String clickTag) {
		System.out.println("Click tags function called");



	}
}
