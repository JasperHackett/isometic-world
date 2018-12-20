import java.util.HashMap;

/**
 * 
 */

/**
 * @author Orly
 *
 */
public class ObjectMap<String, GameObject> extends HashMap<String, GameObject> {
	
	public ObjectMap(){
		super();
	}
	
	public void addObject(String s, GameObject obj) {
		this.put(s, obj);
	}
	
	// returns true after a successful remove operation
	// returns false if there is no object with that key in the map
	public boolean removeObject(String s){
		if (!this.containsKey(s)) {
			return false;
		}
		this.remove(s);
		return true;
	}
	
	public GameObject getObject(String s) {
		return (GameObject)this.get(s);
	}
	
	
}
