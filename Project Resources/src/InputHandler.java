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


	@Override
	public void mouseClicked(MouseEvent e) {

//		String mouseButton = "not";
//		if (e.getButton() == MouseEvent.BUTTON1) {
//			mouseButton = "left";
//		} else if (e.getButton() == MouseEvent.BUTTON2) {
//			mouseButton = "middle";
//		} else if (e.getButton() == MouseEvent.BUTTON3) {
//			mouseButton = "right";
//		}
		
		Point iso2D = toGrid(Game.gameWorld.getWorldPosition(e.getPoint()));
		iso2D.setLocation(iso2D.getX() - 975, iso2D.getY() + 975);
		iso2D.setLocation((int) iso2D.getX()/32, (int) iso2D.getY()/32);
		
		for(UserInterfaceObject uiObj : Game.objectMap.getEnabledUIObjects()) {
			if(checkContains(uiObj.getPosition(),e.getPoint())) {
//				System.out.println(uiObj.clickTag);
				callClickAction(uiObj.clickTag);
				break;
			}
		}

		
		
		if(Game.currentState == Game.STATE.Menu) {
			for(GameObject obj : Game.objectMap.getOtherObjects().values()) {
				if(obj.isClickable()){
					if(checkContains(obj.getPosition(),e.getPoint())) {
						callClickAction(obj.clickTag);
					}
				}
			}
		}else if(Game.currentState== Game.STATE.Game) {

			if(iso2D.getX() >= 0 && iso2D.getY() >= 0 && iso2D.getX() < Game.gameWorld.isoDims.width && iso2D.getY() < Game.gameWorld.isoDims.height) {
					if(clickedObject != null) {
						if(!clickedObject.equals(Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY()))){
							clickedObject.setClicked(false);
							clickedObject = null;
						}

					}
					clickedObject = Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY());	
					if(this.clickedObject.isClicked()) {
						this.clickedObject.setClicked(false);
						this.clickedObject = null;
					}else {
						this.clickedObject.setClicked(true);
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

		IsometricTile tempTile = null;

		//Check iso coordinate is within world bounds (potentially useless)
		if(iso2D.getX() >= 0 && iso2D.getY() >= 0 && iso2D.getX() < Game.gameWorld.isoDims.width && iso2D.getY() < Game.gameWorld.isoDims.height) {
			tempTile = Game.objectMap.worldTiles.get((int) iso2D.getX() +":"+ (int) iso2D.getY());	

			if(tempTile != null) {
				if((tempTile.type == ObjectType.WORLD)){
	
					if(hoveredObject == null) {
						hoveredObject = tempTile;
					}
	
					if(tempTile.equals(hoveredObject)) {
						hoveredObject.hoverAction();
					}else {
						hoveredObject.disableHover();
						this.hoveredObject = tempTile;
						this.hoveredObject.hoverAction();
					}
				}
			}
		}
//		for(Map.Entry<String, IsometricTile> obj : Game.objectMap.worldTiles.entrySet()) {
//			if(obj.getValue().isHoverable()) {
//				if(checkContains(obj.getValue().getPosition().getValue(),obj.getValue().getPosition().getKey(),mousePos)) {
//					
					
		
//				}

//			}
//		}
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
			Game.userInterface.disableInterfaceContainer("mainmenu");
			Game.currentState = Game.STATE.Game;
//			Game.gameWorld.updateDisplay();
		}else if (clickTag.equals("exit")) {
			System.out.println("Exiting");
			System.exit(0);
		}
		
		
	}
}
