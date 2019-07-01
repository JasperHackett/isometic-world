import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import InterfaceController.UIContainer;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class ResourceStructure extends Structure{
	

	public static final int storageCapConstant = 100;
	public static final int resourceProductionConstant = 2;
	public int resourcesStored;
	public int storageCap;
	public ArrayList<Resource> developedResources;
	public ArrayList<Resource> connectedResources;
	public Resource.ResourceType structureType;
	public HashMap<IsometricTile, String> tileImageMap;
	public Image resourceImage;
	/**
	 * @param tileList
	 */
	public ResourceStructure(ArrayList<IsometricTile> tileList, Resource.ResourceType RType) {
		super(tileList);
		storageCap = storageCapConstant * tileList.size();
		resourcesStored = 0;
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
			this.objectImage = Game.objectMap.getImage(tileImageMap.get(this.tileList.get(0)));
			this.clickTag = "ironmine";
			this.worldDims = new Dimension(128,64);
			this.dim = new Dimension(128,64);
			this.name = "Iron Mine";
			this.coords = new Point(0,0);
			this.resourceImage = Game.objectMap.getImage("ironore");
		}
		
	}

	public void addResource(Resource resource) {
		IsometricTile newTile = resource.tileList.get(0);
		this.tileList.add(newTile);
		this.tileImageMap.put(newTile, "ironResourceStructure12");
		for (IsometricTile tile : this.tileList) {
			Game.objectMap.updateMultiTiledImage(tile, ObjectMap.MultiTiledImageType.resourceStructure);
		}
		this.storageCap = storageCapConstant * tileList.size();
	}
	
	@Override
	public void clickAction() {
		System.out.println("Click action on: "+ this.name);
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
			g.drawImage(resourceImage, tile.coords.x + Game.xOffset, tile.coords.y + Game.yOffset, null);
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
		// making sure that the tick won't cause resourcesStored to go over storageCap
		int resourceCount = resourcesStored + (resourceProductionConstant * tileList.size());
		if (resourceCount > storageCap) {
			resourceCount = storageCap;
		}
		resourcesStored = resourceCount;
		
	}
}
