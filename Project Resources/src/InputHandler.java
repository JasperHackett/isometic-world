import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

import javafx.util.Pair;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class InputHandler implements MouseListener, MouseMotionListener {
	
	private Point mousePressPos;
	private boolean dragEnabled;
	
	
	
	public boolean checkContains(Pair<Dimension,Point> pairIn, Point mousePosition) {
			if(pairIn.getValue().getX() < mousePosition.getX() && pairIn.getValue().getY() < mousePosition.getY() 
					&& pairIn.getValue().getX()+pairIn.getKey().getWidth() > mousePosition.getX()
					&& pairIn.getValue().getY()+pairIn.getKey().getHeight() > mousePosition.getY() ){
						
				
				return true;
			}
			return false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//			mousePressPos = e.getPoint();
		if(checkContains((new Pair<Dimension,Point>(new Dimension(1400,700), new Point(100,100))), e.getPoint())){
			if(dragEnabled == true) {
				Game.gameWorld.offsetDisplay(mousePressPos,e.getPoint());
				
			}

		}
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked at: "+ e.getX() + ", " + e.getY());
		for(Map.Entry<String, GameObject> obj : Game.objectMap.entrySet()) {
			if(obj.getValue().isClickable()){
				if(checkContains(obj.getValue().getPosition(),e.getPoint())) {
					System.out.println("Clickable object clicked.");
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		this.mousePressPos = e.getPoint();
		
		if(checkContains((new Pair<Dimension,Point>(new Dimension(1400,700), new Point(100,100))), e.getPoint())){
			dragEnabled = true;
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		dragEnabled = false;
		mousePressPos = null;
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
