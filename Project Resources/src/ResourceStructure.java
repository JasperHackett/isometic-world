import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class ResourceStructure extends Structure{
	
	ArrayList<Unit> workers;
	int resourceStored = 0;
	int currentWorkers = 0;
	int resourceRange = 0; //How many tiles away resources can be collected
	ArrayList<Resource> resources;
	/**
	 * @param tileList
	 */
	public ResourceStructure(ArrayList<IsometricTile> tileList, Resource.ResourceType RType) {
		super(tileList);
		workers = new ArrayList<Unit>();
		resources = new ArrayList<Resource>();
//		this.tileList = tileList;
//		for (IsometricTile tile : tileList) {
//			tile.setEntityOnTile(this);
//		}
		this.structureType = RType;
		if(RType.equals(Resource.ResourceType.IRON)) {
			this.objectImage = "ironhut";
			this.clickTag = "ironmine";
			this.worldDims = new Dimension(128,64);
			this.dim = new Dimension(128,64);
			this.name = "Iron Mine";
			this.coords = new Point(0,0);
			this.resourceRange = 4;
		}
		
	}

	Resource.ResourceType structureType;
	
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
		Game.userInterface.setParentObject("resourcestructure",null);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(currentlyHovered || currentlyClicked) {
			g.drawImage(Game.objectMap.getImage("2x2hover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset +32, null);
		}
	}
	public void addWorker() {
		this.currentWorkers ++;
	}
	
	public void detectResources() {
		if(this.isoPoint == null) {
			System.out.println("detect resources: iso point is null");
		}
		if(this.resourceRange > 0 && this.isoPoint != null) {

			for(int i = this.isoPoint.x-resourceRange; i < this.isoPoint.x+resourceRange+1;i++) {
				for(int j = this.isoPoint.y-resourceRange; j < this.isoPoint.y+resourceRange+1;j++) {
					if(Game.objectMap.getTile(new Point(i,j)).entityOnTile instanceof Resource) {
						Resource resource = (Resource) Game.objectMap.getTile(new Point(i,j)).entityOnTile;
						if(resource.resourceType == this.structureType) {
							if(!resources.contains(resource)) {
								this.resources.add(resource);
							}

						}
					}
				}
				
			}
		}
	}

}
