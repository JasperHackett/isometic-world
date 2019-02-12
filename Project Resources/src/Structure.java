
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Orly
 *
 */
public class Structure extends Entity {

	ArrayList<Unit> workers;
	int nextWorkerOut= 0;
	int currentWorkers = 0;
	int activeWorkers = 0;
	int workerTicks = 12;
	int tickCounter = 0;
	public String name;
	/**
	 * @param type
	 * @param worldDimsIn
	 * @param worldPointIn
	 */
	public Structure(ArrayList<IsometricTile> tileList) {
		super(tileList);
		name = "undefined";
		workers = new ArrayList<Unit>();
	}

	public void addWorker() {

		if(Game.player.availableWorkers > 0) {
			this.currentWorkers ++;
			Unit worker = new Unit(new Point(this.isoPoint.x-1 ,this.isoPoint.y-1),this);
			workers.add(worker);
			Game.objectMap.addObject(ObjectType.WORLD, worker.toString(), worker);
			Game.objectMap.addEntity(worker.toString(), worker,8);
			worker.setProperties(new Dimension(64,32), new Point(600,200),"cube");
			worker.setVisible(false);
			
			Game.player.employWorker();
		}else {
			System.out.println("no available workers");
		}

	}
	// public Structure(ArrayList<IsometricTile> tileList, Point masterTile,
	// StructureType type) {
	// super();
	// this.tileList = tileList;
	// this.masterTile = masterTile;
	// this.type = type;
	//
	// }
	//
	// public Structure(Point masterTile, ArrayList<IsometricTile> tileList) {
	// super();
	// this.tileList = tileList;
	// this.masterTile = masterTile;
	//
	//
	// }
//	public Structure(ArrayList<IsometricTile> tileList) {
//
//		this.tileList = tileList;
//		for (IsometricTile tile : tileList) {
//			tile.setStructureOnTile(this);
//		}
//		this.worldPoint = tileList.get(0).worldPoint;
//		this.children = new HashMap<GameObject, Pair<Integer, Integer>>();
//	}
//
//	public void addChild(GameObject child, Pair<Integer, Integer> positionOffset) {
//		this.children.put(child, positionOffset);
//
//	}
//
//	@Override
//	public void setPosition(Point worldPointIn, Point displayPanelPoint) {
//		this.coords.setLocation((displayPanelPoint.getX() + (this.worldPoint.getX() - worldPointIn.getX())),
//				((displayPanelPoint.getY() + (this.worldPoint.getY() - worldPointIn.getY()))));
//		if (children == null) {
//			return;
//		} else if (children.isEmpty()) {
//			return;
//		} else {
//			for (GameObject child : children.keySet()) {
//				child.coords.setLocation(
//						(displayPanelPoint.getX() + (this.worldPoint.getX() - worldPointIn.getX()))
//								+ children.get(child).getKey(),
//						((displayPanelPoint.getY() + (this.worldPoint.getY() - worldPointIn.getY()))
//								+ children.get(child).getValue()));
//			}
//		}
//	}
//
//	@Override
//	public void clickAction() {
//		// if(structureOnTile != null) {
//		// System.out.println("Clicked a structure containing tile");
//		// }else {
//		// System.out.println("Clicked a tile of type: " + this.tileset + ".
//		// Walkable:"+this.walkable);
//		// }
//
//		if (this.currentlyClicked == false) {
//			this.currentlyClicked = true;
//		} else {
//			this.currentlyClicked = false;
//		}
//	}
//
//	public void hoverAction() {G
//		// System.out.println("structure hovered");
//		this.currentlyHovered = true;
//
	
	
	@Override
	public void clickAction() {
//		System.out.println("Click action on: "+ this.name);
		this.currentlyClicked = true;
//		Game.userInterface.passCityToInterfaceContainer(this, "citymanager");
//		Game.userInterface.enableInterfaceContainer("citymanager");
//		Game.userInterface.createUIContainer("cityinterface", new Point(1410,36), new Point(0,50));
//		Game.userInterface.addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "cityinterface", "hellobutton", "hello", "Hello");
//		Game.userInterface.addInterfaceObject(UserInterfaceObject.UIElementType.SMALL, "cityinterface", "goodbyebutton", "goodbye", "Goodbye");
//		Game.userInterface.enableInterfaceContainer("cityinterface");
	}
//	}
//	@Override
//	public void render(Graphics g) {
//		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
//		if(currentlyHovered || currentlyClicked) {
//			g.drawImage(Game.objectMap.getImage("cityhover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
//		}
//		if (children == null) {
//			return;
//		} else if (children.isEmpty()) {
//			return;
//		} else {
//			for (GameObject child : children.keySet()) {
//				child.render(g);
//			}
//		}
//	}
}
