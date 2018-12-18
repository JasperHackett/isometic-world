/**
 * 
 */

/**
 * @author Orly
 *
 */
public class Game {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		

		// TODO Auto-generated method stub
		System.out.println("Test");
		
		Renderer mainGameRenderer = new Renderer("mainGameRenderer");
		mainGameRenderer.start();

		Renderer mainGameRenderer2 = new Renderer("mainGameRenderer2");
		mainGameRenderer2.start();
		  for(int i = 0; i < 10; i++) {
			  System.out.println(i);
		  }

		
		GameWindow mainGameWindow = new GameWindow();
		
	}
	
	
	

}
