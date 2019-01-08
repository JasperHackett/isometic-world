import java.awt.Color;
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
public class Renderer implements Runnable{
	private Thread t;
	private String threadName;
	private Graphics graphics;
	private JFrame mainWindow;
	public int frames;
	public static Semaphore semaphore = new Semaphore(1);



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
	 *  
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
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(Game.currentState == Game.STATE.Game) {
//			for(WorldObject obj : Game.objectMap.getMainDisplayObjects()) {
//				if(obj != null) {
//					obj.render(graphics);
//				}
//
//			}
			for(IsometricTile obj : Game.objectMap.getMainDisplayTiles()) {
				if(obj != null) {
					obj.render(graphics);
				}
	
			}
			
			for(Structure obj : Game.objectMap.getMainDisplayStructures()) {
				if(obj != null) {
					obj.render(graphics);
				}
	
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
        
        semaphore.release();

	}
	

	public Point toGrid(Point pointIn) {
		Point tempPoint = new Point(0,0);
		
		tempPoint.x = (2*pointIn.y + pointIn.x) / 2;
		tempPoint.y = (2*pointIn.y - pointIn.x) / 2;
		
		return(tempPoint);
	}
	public Point toIsometric(Point pointIn) {
		Point tempPoint = new Point(0,0);
		tempPoint.x =  pointIn.x - pointIn.y;
		tempPoint.y = (pointIn.x + pointIn.y) /2;
		
		
		return(tempPoint);
	}
};








