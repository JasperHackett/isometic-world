import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Map;

import javax.swing.JFrame;


/**
 * @author Jasper
 *
 */
public class Renderer implements Runnable{
	private Thread t;
	private String threadName;
	private Graphics graphics;
	private JFrame mainWindow;
	public int frames;



	/**
	 *  
	 * 
	 */
	public Renderer(String name, JFrame windowIn){
		threadName = name;
		this.mainWindow = windowIn;

	}

	public Graphics getGraphic(){
		return this.graphics;
	}


   public void start() {
	      if(t == null) {
	    	  t = new Thread(this, threadName);
	    	  t.start();
	      }
   }

	/**
	 *  FPS counter
	 * 
	 */
   public void run() {

	   long lastTime = System.nanoTime();
	   double tickCount = 60;
	   double ns = 1000000000 / tickCount;
	   double delta = 0;
       long timer = System.currentTimeMillis();
       frames = 0;

	   while(true) {
		   long now = System.nanoTime();
		   delta += (now - lastTime) / ns;
		   lastTime = now;
		   while(delta >= 1) {
			   delta--;
		   }
		   renderFrame();
		   frames++;
           if(System.currentTimeMillis() - timer > 1000) {
               timer += 1000;
//               System.out.println("FPS: " + frames);
               frames = 0;
           }
	   }

   }

	/**
	 *  Iterates through the set of worldObjects then the set of otherObjects and renders them
	 * 
	 */
	public void renderFrame() {
		BufferStrategy bs = mainWindow.getBufferStrategy();
        if (bs == null) {
           mainWindow.createBufferStrategy(3);
           return;
        }

        Graphics graphics = bs.getDrawGraphics();
		graphics.clearRect(0, 0, Game.width, Game.height);
		graphics.setColor(Color.black);
		if(Game.currentState == Game.STATE.Game) {
			for(Map.Entry<String, WorldObject> obj : Game.objectMap.getMainDisplayObjects().entrySet()) {
				obj.getValue().render(graphics);
			}
		}else if(Game.currentState == Game.STATE.Menu) {
			for(Map.Entry<String, GameObject> obj : Game.objectMap.getMenuObjects().entrySet()) {
				obj.getValue().render(graphics);
			}
		}
		for(Map.Entry<String, GameObject> obj : Game.objectMap.getOtherObjects().entrySet()) {
			obj.getValue().render(graphics);
		}



        graphics.dispose();
        bs.show();

	}
};
