//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Point;
//
///**
// * 
// */
//
///**
// * @author Orly
// *
// */
//public class Menu {
//	
//	public Font menuFont;
//	public enum menuState{
//		Main, Load, Options;
//	}
//	/**
//	 * 
//	 */
//	public Menu() {
//		menuFont = new Font("Yu Gothic UI Semibold", Font.BOLD, 30);
//		// Menu Background
//		GameObject menuBackground = new GameObject(ObjectType.MAINMENU);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "menuBackground", menuBackground);
//		menuBackground.setProperties(new Dimension(Game.width, Game.height), new Point(0,0), "menuBackground", true);
//		
//		// New Game Button
//		GameObject newGameButton = new GameObject(ObjectType.MAINMENU);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "newGameButton", newGameButton);
//		Point p = new Point((Game.width/2) - 125,(Game.height/5) - 50);
//		newGameButton.setProperties(new Dimension(250,100), p, "menuButton1", true, "newGameButton");
//		TextObject newGameText = new TextObject(ObjectType.MAINMENU, menuFont, Color.WHITE);
//		p = new Point(Game.width/2 - Game.graphics.getFontMetrics(menuFont).stringWidth("New Game")/2, Game.height/5 + Game.graphics.getFontMetrics(menuFont).getHeight());
//		newGameText.setProperties("New Game", Color.BLACK, p);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "newGameText", newGameText);
//		
//		// Load Game Button
//		GameObject loadGameButton = new GameObject(ObjectType.MAINMENU);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "loadGameButton", loadGameButton);
//		p = new Point((Game.width/2) - 125,(Game.height/5*2) - 50);
//		loadGameButton.setProperties(new Dimension(250,100), p, "menuButton1", true, "loadGameButton");
//		TextObject loadGameText = new TextObject(ObjectType.MAINMENU, menuFont, Color.WHITE);
//		p = new Point(Game.width/2 - Game.graphics.getFontMetrics(menuFont).stringWidth("Load Game")/2, Game.height/5*2 + Game.graphics.getFontMetrics(menuFont).getHeight());
//		loadGameText.setProperties("Load Game", Color.BLACK, p);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "loadGameText", loadGameText);
//		
//		// Options Button
//		GameObject optionsButton = new GameObject(ObjectType.MAINMENU);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "optionsButton", optionsButton);
//		p = new Point((Game.width/2) - 125,(Game.height/5*3) - 50);
//		optionsButton.setProperties(new Dimension(250,100), p, "menuButton1", true, "optionsButton");
//		TextObject optionsText = new TextObject(ObjectType.MAINMENU, menuFont, Color.WHITE);
//		p = new Point(Game.width/2 - Game.graphics.getFontMetrics(menuFont).stringWidth("Options")/2, Game.height/5*3 + Game.graphics.getFontMetrics(menuFont).getHeight());
//		optionsText.setProperties("Options", Color.BLACK, p);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "optionsText", optionsText);
//		
//		// Quit Button
//		GameObject quitButton = new GameObject(ObjectType.MAINMENU);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "quitButton", quitButton);
//		p = new Point((Game.width/2) - 125,(Game.height/5*4) - 50);
//		quitButton.setProperties(new Dimension(250,100), p, "menuButton1", true, "quitButton");
//		TextObject quitText = new TextObject(ObjectType.MAINMENU, menuFont, Color.WHITE);
//		p = new Point(Game.width/2 - Game.graphics.getFontMetrics(menuFont).stringWidth("Quit")/2, Game.height/5*4 + Game.graphics.getFontMetrics(menuFont).getHeight());
//		quitText.setProperties("Quit", Color.BLACK, p);
//		Game.objectMap.addObject(ObjectType.MAINMENU, "quitText", quitText);
//	}
//
//}
