import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javafx.util.Pair;

/**
 *
 */

/**
 * @author Orly
 *
 */
public class City extends Structure {

	public String name;
//	public final Font cityFont = new Font("Callibri", Font.BOLD, 15);

	public City(ArrayList<IsometricTile> tileList, String name) {
		super(tileList);

		this.worldDims = new Dimension(128,64);
		this.dim = new Dimension(192,96);
		this.coords = new Point(200,200);
		this.objectImage = "citytile0";
		this.clickTag = "city";
		this.name = name;

		// creating and adding child component for city name
		TextObject cityName = new TextObject(ObjectType.CHILD);
		cityName.setTextProperties(name,Game.objectMap.getFont("primarygamefont"), Color.WHITE, new Point(0,0));
		Dimension offset = new Dimension(((int)this.dim.getWidth()/2) - (cityName.width/2), -32);
		this.addChild((GameObject)cityName, offset);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		if(currentlyHovered || currentlyClicked) {
			g.drawImage(Game.objectMap.getImage("cityhover"), coords.x + Game.xOffset, coords.y + Game.yOffset - this.structureOffset, null);
		}
	}

	@Override
	public void clickAction() {
		System.out.println("Click action on: "+this.objectImage);
		this.currentlyClicked = true;

	}

}
