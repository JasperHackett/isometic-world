/** 
 * 
 */
import java.awt.Color;
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
	public int width, height;
	private Color textColour;
	
	/**
	 * @param type
	 */
	public TextObject(ObjectType type, String text, Font font) {
		super(type);
		this.text = text;
		this.font = font;

	}
	public TextObject(ObjectType type, Font font) {
		super(type);
		this.font = font;

	}
	
	public void setProperties(String textIn, Color textColour, Point pointIn) {
		this.text = textIn;
		this.textColour = textColour;
		this.coords = pointIn;
		this.width = Game.graphics.getFontMetrics(font).stringWidth(text);
		this.height = Game.graphics.getFontMetrics(font).getHeight();
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(textColour);
		g.setFont(font);
		g.drawString(text, this.getPosition().getValue().x, this.getPosition().getValue().y);
	}
	
	public void setText(String text) {
		this.text = text;
		this.width = Game.graphics.getFontMetrics(font).stringWidth(text);
		this.height = Game.graphics.getFontMetrics(font).getHeight();
	}
}
