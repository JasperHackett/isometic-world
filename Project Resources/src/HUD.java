import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class HUD {
	
	Map<String,GameObject> HUDElements;
	ArrayList<GameObject> TopBarElements;
	Map<String,GameObject> ActiveContainer;
	
	HUD(){
		HUDElements = new HashMap<String,GameObject>();
	}
	
	private class cityDashboard{
		TextObject cityName;
		GameObject cityButton;
		
		cityDashboard(City city){
			cityName = new TextObject(ObjectType.DEFAULT);
//			cityName.setProperties(city.name,Color.WHITE );
			cityName.setText(city.name);
//			cityButton = new GameObject()
			
		}
		
	}
	
	
	public void addHUDElement(GameObject newHUDElement) {
		
	}

}
