
public class Renderer implements Runnable{
	private Thread t;
	private String threadName;
	private GameWindow mainGameWindow;
	
	public Renderer(String name, GameWindow mainGameWindow){
		threadName = name;
		this.mainGameWindow = mainGameWindow;
		
	}
	
   public void run() {
	   while(true) {
		   mainGameWindow.renderFrame();
//		   try {
//			  sleep(16);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
	   }

   }
   
   
   public void start() {
	      if(t == null) {
	    	  t = new Thread(this, threadName);
	    	  t.start();
	      }
}
};