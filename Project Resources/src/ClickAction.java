/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class ClickAction {
	
	public interface Action {
		public void execute(Object uiObj);
	}
	public static void callClickAction(Runnable action) {
		action.run();
	}
	
	static void ExitGame(){
		System.out.println("Exiting");
		System.exit(0);
		
	}
	
	static void StartGame() {
		System.out.println("New game button clicked");
//		Game.gameWorld = new World();
		Game.userInterface.initialiseMainGameInterface();
		Game.userInterface.disableInterfaceContainer("mainmenu");
		Game.userInterface.enableInterfaceContainer("topmenubar");
		Game.currentState = Game.STATE.Game;
	}
//		public void execute(Object uiObj) {
//
//		}
//	}
	
}
