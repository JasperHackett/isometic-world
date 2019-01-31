import java.awt.Dimension;
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
	
	public Double resourceCapacity;
	ResourceType resourceType;
	
	Resource(ArrayList<IsometricTile> tileList,ResourceType resourceType){
		super(tileList);
		this.resourceType = resourceType;
		
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
	
	
	
	
}
