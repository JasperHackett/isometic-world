import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javafx.util.Pair;

/**
 * 
 */

/**
 * @author Jasper
 *
 */

public class ActionHandler {
	
	ActionHandler(){
		
	}
	
	static void displayControlMenu(GameObject obj){
		System.out.println("displayControlMenu");
		if(obj instanceof UserInterfaceObject) {
			UserInterfaceObject uiObj = (UserInterfaceObject) obj;
///			Game.userInterface.dropDownContainer(containerName, containerParent, objectSet, pos, spacingPos, clickTag);
			
			ArrayList<Pair<String,Action>> controlMenu = new ArrayList<Pair<String,Action>>();
			controlMenu.add(new Pair<String,Action>("Options",ActionHandler::optionsMenu));
			controlMenu.add(new Pair<String,Action>("Exit",ActionHandler::exitGame));
////			new Point((int)(window.width*0.20),4)
//			Game.userInterface.dropDown(controlMenu,new Dime
			System.out.println("POINT:::" + new Point(uiObj.coords.x,(uiObj.coords.y + uiObj.dim.height)));
			Game.userInterface.dropDown(controlMenu,new Dimension(160,20),uiObj.coords, new Point(0,20),uiObj);
		}
//		Game.userInterface.dropDown(itemList, itemSize, pos, elementSpacing, parent);
	}


	
	static void exitGame(GameObject obj){
		System.out.println("Exiting");
		System.exit(0);
		
	}
	
	static void startGame(GameObject obj) {
		System.out.println("New game button clicked");
//		Game.gameWorld = new World();
		Game.userInterface.initialiseMainGameInterface();
		Game.userInterface.disableInterfaceContainer("mainmenu");
		Game.userInterface.enableInterfaceContainer("topmenubar");
		Game.currentState = Game.STATE.Game;
	}
	

	
	static void optionsMenu(GameObject obj) {
		System.out.println("Options stub");
	}
	
//	public void DropDown
////		public void execute(Object uiObj) {
////
////		}
////	}
//	
}
