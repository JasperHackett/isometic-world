import java.awt.Color;
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
public class Warehouse extends Structure{

	/**
	 * @param tileList
	 */

	
	public Warehouse(ArrayList<IsometricTile> tileList) {
		super(tileList);
		
		this.objectImage = "warehouse";
		this.name = "Warehouse";
		this.worldDims = new Dimension(128,64);
		this.dim = new Dimension(192,96);
		this.coords = new Point(200,200);
		this.clickTag = "warehouse";

		
	
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void clickAction() {
//		System.out.println("Click action on: "+ this.name);
		this.currentlyClicked = true;
		Game.userInterface.passWarehouseToInterfaceContainer(this, "warehouse");
		Game.userInterface.enableInterfaceContainer("warehouse",InterfaceController.InterfaceZone.TopSidePanel);
		Game.userInterface.setParentObject("warehouse",this);

	}
	
	@Override
	public void disableClick() {
		this.currentlyClicked = false;
		Game.userInterface.disableInterfaceContainer("warehouse");
		Game.userInterface.disableInterfaceContainer("workerslist");
		Game.userInterface.setParentObject("warehouse",null);
	}
	
	
	public void render(Graphics g) {
		super.render(g);
		if(currentlyHovered || currentlyClicked) {
			g.drawImage(Game.objectMap.getImage("cityhover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset , null);
		}
	}

}
