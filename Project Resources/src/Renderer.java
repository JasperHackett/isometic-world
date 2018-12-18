
public class Renderer implements Runnable{
	private Thread t;
	private String threadName;
	
	public Renderer(String name){
		threadName = name;
		
	}
	
   public void run() {
	      for(int i = 0; i < 10; i++) {
	    	  System.out.println(i);
	      }
   }
   
   
   public void start() {
	      if(t == null) {
	    	  t = new Thread(this, threadName);
	    	  t.start();
	      }
}
};