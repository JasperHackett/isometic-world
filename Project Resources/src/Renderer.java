
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

/**
 * @author Jasper
 *
 */
public class Renderer implements Runnable {
	private Thread t;
	private String threadName;
	private Graphics graphics;
	private JFrame mainWindow;
	public int frames;
	public static Semaphore semaphore = new Semaphore(1);



	public Renderer(String name, JFrame windowIn){
		threadName = name;
		this.mainWindow = windowIn;

	}

	public Graphics getGraphic() {
		return this.graphics;
	}

	/**
	 *
	 *
	 */
   public void start() {
	      if(t == null) {
	    	  t = new Thread(this, threadName);
	    	  t.start();
	      }
   }


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
	 *  Iterates through the set of visible GameObjects and calls their render method. Using a triple buffer
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

		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(Game.currentState == Game.STATE.Game) {

			for(IsometricTile obj : Game.objectMap.getMainDisplayTiles()) {
				if(obj != null) {
					obj.render(graphics);
				}

			}

			for (Entity obj : Game.objectMap.getMainDisplayEntitys()) {
				if (obj != null) {
					obj.render(graphics);
				}

			}
			
			for (GameObject obj : Game.objectMap.getOtherObjects().values()) {
				obj.render(graphics);
			}

		}

//		else if (Game.currentState == Game.STATE.Menu) {
//			for (GameObject obj : Game.objectMap.getMenuObjects().values()) {
//				obj.render(graphics);
//			}
//		}
		
		for (UserInterfaceObject uiObj : Game.objectMap.getEnabledUIObjects()) {
			if( uiObj != null) {
				uiObj.render(graphics);
			}

		}
		

//		Game.userInterface.renderInterfaceController(graphics);
        graphics.dispose();
        bs.show();

        semaphore.release();

	}







};
