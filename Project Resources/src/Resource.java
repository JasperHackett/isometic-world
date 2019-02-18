import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class Resource extends Entity{
	
	public enum ResourceType{
		IRON,
	};
	
	private ResourceStructure structure;
	public ArrayList<Resource> resourceCluster;
	public Double resourceCapacity;
	public ResourceType resourceType;
	
	Resource(ArrayList<IsometricTile> tileList,ResourceType resourceType){
		super(tileList);
		this.resourceType = resourceType;
		this.type = EntityType.resource;
		
//		currentPath = new LinkedList<Point>(Game.gameWorld.getPathBetween(new Point(4,6), new Point(41,55)));
//		this.structureOffset = 16;
//		this.isoPoint = isoPos;
		this.clickable = true;
//		Game.objectMap.getTile(isoPos);
//		this.worldPoint = Game.objectMap.getTile(isoPos).worldPoint;
		this.worldDims = new Dimension(64,32);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
		this.objectImage = "ironore";
		this.resourceCluster = new ArrayList<Resource>();
		initialiseCluster();
	}
	
	
	
	public void initialiseCluster() {
		IsometricTile origin = tileList.get(0);
		ArrayList<Resource> cluster = new ArrayList<Resource>();
		cluster.add((Resource)origin.entityOnTile);
		ArrayList<IsometricTile> visited = new ArrayList<IsometricTile>();
		
		this.resourceCluster = recurseCluster(origin, cluster, visited);
	}
	
	private ArrayList<Resource> recurseCluster(IsometricTile tile, ArrayList<Resource> cluster, ArrayList<IsometricTile> visited) {
		visited.add(tile);
		IsometricTile up = Game.objectMap.getTile(new Point(tile.isoPoint.x, tile.isoPoint.y-1));
		IsometricTile down = Game.objectMap.getTile(new Point(tile.isoPoint.x, tile.isoPoint.y+1));
		IsometricTile left = Game.objectMap.getTile(new Point(tile.isoPoint.x-1, tile.isoPoint.y));
		IsometricTile right = Game.objectMap.getTile(new Point(tile.isoPoint.x+1, tile.isoPoint.y));
		
		if (!visited.contains(up) && up.hasEntityOnTile()) {
			if (up.entityOnTile.type == EntityType.resource) {
				Resource resource = (Resource)up.entityOnTile;
				if (resource.resourceType == this.resourceType && !cluster.contains(resource)) {
					cluster.add(resource);
					cluster = recurseCluster(up, cluster, visited);
				}
			}
		}
		if (!visited.contains(down) && down.hasEntityOnTile()) {
			if (down.entityOnTile.type == EntityType.resource) {
				Resource resource = (Resource)down.entityOnTile;
				if (resource.resourceType == this.resourceType && !cluster.contains(resource)) {
					cluster.add(resource);
					cluster = recurseCluster(down, cluster, visited);
				}
			}
		}
		if (!visited.contains(left) && left.hasEntityOnTile()) {
			if (left.entityOnTile.type == EntityType.resource) {
				Resource resource = (Resource)left.entityOnTile;
				if (resource.resourceType == this.resourceType && !cluster.contains(resource)) {
					cluster.add(resource);
					cluster = recurseCluster(left, cluster, visited);
				}
			}
		}
		if (!visited.contains(right) && right.hasEntityOnTile()) {
			if (right.entityOnTile.type == EntityType.resource) {
				Resource resource = (Resource)right.entityOnTile;
				if (resource.resourceType == this.resourceType && !cluster.contains(resource)) {
					cluster.add(resource);
					cluster = recurseCluster(right, cluster, visited);
				}
			}
		}
		
		return cluster;
	}
	
	public boolean hasStructure() {
		if (structure == null) {
			return false;
		}
		return true;
	}
	
	

	/**
	 * @return 
	 *  Generates a random number for the capacity cap of the resource tile
	 * 
	 */
	public Integer generateAverageCapacity() {
		Integer capacity = new Integer(0);
		Random rn = new Random();
		
		for(int i = 0; i < 10; i++) {
			capacity = capacity + rn.nextInt() % 12;
		}
		
		
		return capacity;
	}
	
	public ResourceType getResourceType() {
		return this.resourceType;
	}
	public void setResourceType(ResourceType type) {
		this.resourceType = type;
	}
	@Override
	public void clickAction() {
		System.out.println("Clicked on a resource");
		this.currentlyClicked = true;

	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		if(currentlyHovered) {
			g.drawImage(Game.objectMap.getImage("resourcehover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		}
		if (children != null) {
			for (GameObject child : children.keySet()) {
				child.render(g);
			}
		}
	}
	
	
	
	
}
