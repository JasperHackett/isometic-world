import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 */

/**
 * @author Orly
 *
 */
public class City extends Structure {

	public String name;

	public City(ArrayList<IsometricTile> tileList) {
		super(tileList);
		
		this.worldDims = new Dimension(128,64);
		this.dim = new Dimension(64,32);
		this.coords = new Point(200,200);
		this.objectImage = "citytile0";
		this.clickTag = "city";

	}
	
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Game.objectMap.getImage(objectImage), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		if(currentlyHovered || currentlyClicked) {
			g.drawImage(Game.objectMap.getImage("cityhover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		}
	}

}
