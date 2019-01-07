/** 
 * 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/**
 * @author Orly
 *
 */
public class TextObject extends GameObject {

	public String text;
	public Font font;
	public int width, height;
	
	/**
	 * @param type
	 */
	public TextObject(ObjectType type, String text, Font font) {
		super(type);
		this.text = text;
		this.font = font;
		this.width = Game.graphics.getFontMetrics(font).stringWidth(text);
		this.height = Game.graphics.getFontMetrics(font).getHeight();
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(text, this.getPosition().getValue().x, this.getPosition().getValue().y);
	}
}
