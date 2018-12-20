import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
//	private Map<String,GameObject> objectMap;
	private BufferedImage testImage;
	private GameObject testGameObject;
	
	
	
	/**
	 * @param name : name of thread
	 * @param windowIn : 
	 */
	public Renderer(String name, JFrame windowIn){
		threadName = name;
		this.mainWindow = windowIn;

	}
	
	public Graphics getGraphic(){
		return this.graphics;
	}
	
//	public void addObject(String objectName, GameObject objectIn) {
//		objectMap.put(objectName, objectIn);
//	}
//	public GameObject getObject(String objectName) {
//		return objectMap.get(objectName);
//	}
	
	
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
       int frames = 0;
	   
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
   


	public void renderFrame() {
		BufferStrategy bs = mainWindow.getBufferStrategy();
        if (bs == null) {
           mainWindow.createBufferStrategy(3);
           return;
        }

        Graphics graphics = bs.getDrawGraphics();
		graphics.clearRect(0, 0, Game.width, Game.height);
		graphics.setColor(Color.black);
		for(Map.Entry<String, GameObject> obj : Game.worldObjects.entrySet()) {
			obj.getValue().render(graphics);
		}
		for(Map.Entry<String, GameObject> obj : Game.otherObjects.entrySet()) {
			obj.getValue().render(graphics);
		}
		
		

        graphics.dispose();
        bs.show();

	}
};