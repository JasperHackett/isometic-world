/** 
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
/**
 * @author Orly
 *
 */
public class TextObject extends GameObject {

	public String text;
	public Font font;
	public int width = 0;
	public int height = 0;
	private Color textColour;
	
	
	public TextObject(ObjectType type) {
		super(type);
	}
	
	public TextObject(ObjectType type, String text) {
		super(type);
		this.text = text;
	}
	
	/**
	 * @param type
	 */
	public TextObject(ObjectType type, String text, Font font) {
		super(type);
		this.text = text;
		this.font = font;
		this.coords = new Point();
	}
	public TextObject(ObjectType type, Font font, Color textColour) {
		super(type);
		this.font = font;
		this.textColour = textColour;
	}
	
	public void setProperties(String textIn, Color textColour, Point pointIn) {
		this.text = textIn;
		this.textColour = textColour;
		this.coords = pointIn;
		this.width = Game.graphics.getFontMetrics(font).stringWidth(text);
		this.height = Game.graphics.getFontMetrics(font).getHeight();
		this.dim = new Dimension(width,height);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(textColour);
		g.setFont(font);
		g.drawString(text, coords.x, coords.y);
	}
	
	public void setText(String text) {
		this.text = text;
		this.width = Game.graphics.getFontMetrics(font).stringWidth(text);
		this.height = Game.graphics.getFontMetrics(font).getHeight();
		this.dim = new Dimension(width,height);
	}
}
