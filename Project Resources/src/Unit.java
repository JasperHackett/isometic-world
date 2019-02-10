import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Jasper
 * 
 * 
 * 
 * Contents of this class may need to be moved to a future child class.
 *
 */
public class Unit extends Entity{

	public Point currentDestination;
	
	public enum Actions{
		GOTODEST,
		RETURN,
		GETTASK,
		WAIT;
		
	};
	LinkedList<Point> currentPath;
	LinkedList<Actions> actionsQueue;
	Integer waitTicks; //Number of ticks this unit needs to wait
	Structure parentStructure;
	
	Unit(Point isoPos){
		actionsQueue = new LinkedList<Actions>();
//		currentPath = new LinkedList<Point>(Game.gameWorld.getPathBetween(new Point(4,6), new Point(41,55)));
		this.structureOffset = 16;
		this.isoPoint = isoPos;
//		Game.objectMap.getTile(isoPos);
		this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
		this.worldDims = new Dimension(64,32);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
	}
	Unit(Point isoPos, Structure parentStructure){
		this.parentStructure = parentStructure;
		actionsQueue = new LinkedList<Actions>();
		this.structureOffset = 16;
		this.isoPoint = isoPos;
		this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
		this.worldDims = new Dimension(64,32);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
	}
	
	public void setDestination(Point destinationPos) {
		this.currentDestination = destinationPos;
		currentPath = new LinkedList<Point>(Game.gameWorld.getPathBetween(this.isoPoint, destinationPos));
		actionsQueue.add(Actions.GOTODEST);
	}
	
	public void addAction(Actions action) {
		switch(action) {
		case GETTASK:

			break;
		case GOTODEST:
			
			break;
		case RETURN:
			if(parentStructure != null) {
				setDestination(parentStructure.getClosestNeighbour(this.isoPoint));
			}else {
				System.out.println("Unit given RETURN action with no parentStructure");
			}
			break;
		case WAIT:
			break;
		default:
			break;
			
		}
		actionsQueue.add(action);
		
	}
	public void addAction(Actions action, GameObject objectIn) {
		switch(action) {
		case GETTASK:
			break;
		case GOTODEST:
			
			break;
		case RETURN:
			if(parentStructure != null) {
				setDestination(parentStructure.getClosestNeighbour(this.isoPoint));
			}else {
				System.out.println("Unit given RETURN action with no parentStructure");
			}
			break;
		case WAIT:
			break;
		default:
			break;
			
		}
		actionsQueue.add(action);
		
	}
	public void addAction(Actions action, Point pointIn) {
		switch(action) {
		case GETTASK:
			break;
		case GOTODEST:
			
			break;
		case RETURN:
			if(parentStructure != null) {
				setDestination(parentStructure.getClosestNeighbour(this.isoPoint));
			}else {
				System.out.println("Unit given RETURN action with no parentStructure");
			}
			break;
		case WAIT:
			break;
		default:
			break;
			
		}
		actionsQueue.add(action);
		
	}
	public void addAction(Actions action, Integer intIn) {
		switch(action) {
		case GETTASK:
			break;
		case GOTODEST:
			
			break;
		case RETURN:
			
			break;
		case WAIT:
			waitTicks = intIn;
			break;
		default:
			break;
			
		}
		actionsQueue.add(action);
		
	}
	
	@Override
	public void tickAction() {
		if(!actionsQueue.isEmpty()) {
			switch(actionsQueue.peek()) {
			case GOTODEST:
				if(currentPath == null) {
					System.out.println("No path set");
					return;
				}
				if(!currentPath.isEmpty()) {
					this.isoPoint = currentPath.removeLast();
					this.worldPoint = Game.objectMap.getTile(isoPoint).worldPoint;
				}else {
					System.out.println("Destination reached");
					if(parentStructure != null) {
						this.setVisible(false);
						System.out.println("unit reached dest");
					}
					actionsQueue.poll();
				}
				break;
			case GETTASK:
				break;
			case RETURN:
				break;
			case WAIT:
				if(waitTicks > 0) {
					waitTicks--;
				}else {
					waitTicks = 0;
//					actionsQueue.pop();
				}

				break;
			default:
				break;
					
			
			
			
			}
		}else {
			//Reached the destination. Unit will continue to execute here every tick
		}
		
		
		
	}
}
