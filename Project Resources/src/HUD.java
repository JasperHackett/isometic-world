import java.awt.Color;
import java.awt.Graphics;
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
	
	private class CityDashboard{
		TextObject cityName;
		GameObject cityButton;
		
		CityDashboard(City city){
			cityName = new TextObject(ObjectType.DEFAULT);
//			cityName.setProperties(city.name,Color.WHITE );
			cityName.setText(city.name);
//			cityButton = new GameObject()
			
		}
		
	}
	ArrayList<CityDashboard> HUDElements;
	ArrayList<GameObject> TopBarElements;
	Map<String,GameObject> ActiveContainer;
	
	public HUD(){
		HUDElements = new ArrayList<CityDashboard>();
		TopBarElements = new ArrayList<GameObject>();
		ActiveContainer = new HashMap<String,GameObject>();
	}


	
	public void displayCityOnHUD(City city) {
		CityDashboard activeDashboard = new CityDashboard(city);
		HUDElements.add(activeDashboard);
	}
	
	public void addHUDElement(GameObject newHUDElement) {
		
	}



	public void renderHUD(Graphics g) {
		
	}


}
