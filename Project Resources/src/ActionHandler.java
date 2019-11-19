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
		System.out.println("Action called: displayControlMenu");
		if(obj instanceof UserInterfaceObject) {
			UserInterfaceObject uiObj = (UserInterfaceObject) obj;
///			Game.userInterface.dropDownContainer(containerName, containerParent, objectSet, pos, spacingPos, clickTag);
			Game.userInterface.volatileObjects.add(uiObj);
			ArrayList<Pair<String,Action>> controlMenu = new ArrayList<Pair<String,Action>>();
			controlMenu.add(new Pair<String,Action>("Options",ActionHandler::optionsMenu));
			controlMenu.add(new Pair<String,Action>("Exit",ActionHandler::exitGame));
			
			Game.userInterface.dropDown(controlMenu,new Dimension(160,20),new Point(uiObj.coords.x, uiObj.coords.y + uiObj.dim.height), new Point(0,20),uiObj);
//			System.out.println("POINT:::ss0" + uiObj.coords);
		}
//		Game.userInterface.dropDown(itemList, itemSize, pos, elementSpacing, parent);
	}

	static void displayConstructionMenu(GameObject obj) {
		System.out.println("Action called: displayConstructionMenu");
		Game.userInterface.setRightPanel("constructionrightpanel");
		obj.setClicked(false);
	}
	
	static void displayWorkersMenu(GameObject obj) {
		System.out.println("Action called: displayWorkersMenu");
		Game.userInterface.populatePlayerData(Game.player, "workersrightpanel");
		Game.userInterface.setRightPanel("workersrightpanel");
		obj.setClicked(false);
		
	}
	static void displayCitiesMenu(GameObject obj) {
		System.out.println("Action called: displayCitiesMenu");
		Game.userInterface.setRightPanel("citiesrightpanel");
		obj.setClicked(false);
	}
	
	static void selectCity(GameObject obj) {
		
		if(obj instanceof City) {
			City city = (City) obj;
			Game.userInterface.populateCityData(city,"cityrightpanel");
			Game.userInterface.setRightPanel("cityrightpanel");
			System.out.println("Clicked a reference to: "+city.name);
		}

	}
	
	//obj is the city being adopted
	static void adoptCity(GameObject obj) {
		

		System.out.println("TEST");
		if(!(obj instanceof UserInterfaceObject)) {
			System.out.println("Invalid ActionHandler obj parameter, expected City");
		}else {
			UserInterfaceObject uiObj = (UserInterfaceObject) obj;
			System.out.println(uiObj.worldReference);
			City city = (City)uiObj.worldReference;
			Game.gameWorld.adoptCity(city);
		}

	}
	
	static void hireWorker(GameObject obj) {
		Game.player.hireWorker();
//		Game.userInterface.add
		Game.userInterface.updateContainerValues();
		obj.setClicked(false);
	}
	
	static void assignWorker(GameObject obj) {
		Game.userInterface.showAssignWorkerDialogue(obj);
		obj.setClicked(false);
	}
	static void assignWorkerStart(GameObject obj) {
//		Game.userInterface.showAssignWorkerDialogue(obj);
//		obj.setClicked(false);
	}
	static void disableAssignTaskBox(GameObject obj) {
		Game.userInterface.disableInterfaceContainer("workerassigntask");
		obj.setClicked(false);
	}

	
	
	static void exitGame(GameObject obj){
		System.out.println("Exiting");
		System.exit(0);
		
	}
	
	static void startGame(GameObject obj) {
		System.out.println("Action called: startGame");
//		Game.gameWorld = new World();
		Game.userInterface.initialiseMainGameInterface();
		Game.userInterface.disableInterfaceContainer("mainmenu");
		Game.userInterface.enableInterfaceContainer("topmenubar");
		Game.currentState = Game.STATE.Game;
		Game.gameWorld.updateDisplay();
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
