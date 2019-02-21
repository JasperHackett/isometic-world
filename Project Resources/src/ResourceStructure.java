import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class ResourceStructure extends Structure{
	


	public int resourceStored = 0; //How many tiles away resources can be collected
	public ArrayList<Resource> developedResources;
	public ArrayList<Resource> connectedResources;
	public Resource.ResourceType structureType;
	public HashMap<IsometricTile, String> tileImageMap;
	/**
	 * @param tileList
	 */
	public ResourceStructure(ArrayList<IsometricTile> tileList, Resource.ResourceType RType) {
		super(tileList);

		developedResources = new ArrayList<Resource>();
		connectedResources = new ArrayList<Resource>();
		tileImageMap = new HashMap<IsometricTile, String>();
		tileImageMap.put(this.tileList.get(0), "ironResourceStructure12");
		//		this.tileList = tileList;
//		for (IsometricTile tile : tileList) {
//			tile.setEntityOnTile(this);
//		}
		this.structureType = RType;
		if(RType.equals(Resource.ResourceType.iron)) {
			this.objectImage = "ironhut";
			this.clickTag = "ironmine";
			this.worldDims = new Dimension(128,64);
			this.dim = new Dimension(128,64);
			this.name = "Iron Mine";
			this.coords = new Point(0,0);
			
		}
		
	}

	public void addResource(Resource resource) {
		IsometricTile newTile = resource.tileList.get(0);
		this.tileList.add(newTile);
		this.tileImageMap.put(newTile, "ironResourceStructure12");
		for (IsometricTile tile : this.tileList) {
			Game.objectMap.updateMultiTiledImage(tile, ObjectMap.MultiTiledImageType.resourceStructure);
		}
	}
	
	@Override
	public void clickAction() {
//		System.out.println("Click action on: "+ this.name);
		this.currentlyClicked = true;
		Game.userInterface.passRStructureToInterfaceContainer(this, "resourcestructure");
		Game.userInterface.enableInterfaceContainer("resourcestructure",InterfaceController.InterfaceZone.TopSidePanel);
		Game.userInterface.setParentObject("resourcestructure",this);

	}
	
	@Override
	public void disableClick() {
		this.currentlyClicked = false;
		Game.userInterface.disableInterfaceContainer("resourcestructure");
		Game.userInterface.disableInterfaceContainer("workerslist");
		Game.userInterface.setParentObject("resourcestructure",null);
	}
	
	@Override
	public void render(Graphics g) {
		
		for (IsometricTile tile : this.tileList) {
			g.drawImage(Game.objectMap.getImage(tileImageMap.get(tile)), tile.coords.x + Game.xOffset, tile.coords.y + Game.yOffset - this.structureOffset, null);
			if(currentlyHovered) {
				g.drawImage(Game.objectMap.getImage("hover"), tile.coords.x + Game.xOffset, tile.coords.y + Game.yOffset - this.structureOffset, null);
			}
		}
		
//		super.render(g);
//		if(currentlyHovered || currentlyClicked) {
//			g.drawImage(Game.objectMap.getImage("2x2hover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset +32, null);
//		}
	}

	
	
	
	@Override
	public void tickAction() {
//		tickCounter++;
		if(this.currentlyClicked) {
//			UserInterfaceObject textObj = (UserInterfaceObject)Game.objectMap.get("workertickvalue");
//			textObj.setElementText(Integer.toString(tickCounter));
			
//		}
//		if(tickCounter > 0) {
//			tickCounter--;
//		}else {
//			tickCounter = workerTicks;
//			if(activeWorkers < currentWorkers) {
//				
//				sendWorker();
//			}
//
//		}
	}
	
//	public void detectResources() {
//		if(this.isoPoint == null) {
//			System.out.println("detect resources: iso point is null");
//		}
//		if(this.resourceRange > 0 && this.isoPoint != null) {
//
//			for(int i = this.isoPoint.x-resourceRange; i < this.isoPoint.x+resourceRange+2;i++) {
//				for(int j = this.isoPoint.y-resourceRange-1; j < this.isoPoint.y+resourceRange+1;j++) {
//					
//					/* Shows range that is checked:
//					 * Game.objectMap.getTile(new Point(i,j)).setHovered(true); 
//					 */
//					
//					if(Game.objectMap.getTile(new Point(i,j)).entityOnTile instanceof Resource) {
//						Resource resource = (Resource) Game.objectMap.getTile(new Point(i,j)).entityOnTile;
//						if(resource.resourceType == this.structureType) {
//							if(!resources.contains(resource)) {
//								this.resources.add(resource);
//							}
//
//						}
//					}
//				}
//				
//			}
////			resourceCount = resources.size();
//		}
//	}
	
//	public void sendWorker() {
//		if(activeWorkers < currentWorkers) {
//			for(Unit worker : workers) {
//				if(worker.actionsQueue.size() == 0) {
////					worker.setPosition(new Point(this.isoPoint.x-1 ,this.isoPoint.y-1));
//					worker.worldPoint = new Point(this.isoPoint.x-1 ,this.isoPoint.y-1);
////					this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
//					worker.setVisible(true);
//
//					worker.getResource(resources.get(nextResourceTile).getClosestNeighbour(this.isoPoint));
//					if(nextResourceTile < resources.size()-1) {
//						nextResourceTile ++;
//					}else {
//						nextResourceTile = 0;
//					}
//					Game.gameWorld.addTickingObject(worker);
////					worker.setDestination(resources.get(nextResourceTile).getClosestNeighbour(this.isoPoint));
//				}
//			}
////			Unit worker = new Unit(new Point(this.isoPoint.x-1 ,this.isoPoint.y-1),this);
////			Game.objectMap.addObject(ObjectType.WORLD, worker.toString(), worker);
////			Game.objectMap.addEntity(worker.toString(), worker,8);
////
////			worker.setProperties(new Dimension(64,32), new Point(600,200),"cube");
////			worker.getResource(resources.get(nextResourceTile).getClosestNeighbour(this.isoPoint));
////			worker.setDestination(resources.get(nextResourceTile).getClosestNeighbour(this.isoPoint));
//
//		}
//	}
//	public void workerReturn(Unit worker) {
//		Game.objectMap.removeEntity(worker);
//		this.resourceStored++;
//		if(this.currentlyClicked) {
//			UserInterfaceObject textObj = (UserInterfaceObject)Game.objectMap.get("resourcestoredvalue");
//			if(textObj != null) {
//				textObj.setElementText(Integer.toString(resourceStored));
//			}
//
//		}
////		System.out.println("tesT");
////		Game.objectMap
	}

}
