/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class Player {
	
	public Double money;
	public Integer workers;
	public Integer availableWorkers;
	public Double labourCost;
	
	
	public Player() {
		
		workers = 0;
		availableWorkers = 0;
		money = 2000.0;
		labourCost = 0.0;
	}
	
	public void changeMoney(Double amount) {
		this.money = this.money + amount;
	}
	public void hireWorker() {
		this.availableWorkers++;
		this.workers++;
		 UserInterfaceObject textObj = (UserInterfaceObject)Game.objectMap.get("totalworkersvalue");
		 textObj.setElementText(Integer.toString(Game.player.workers));
		 textObj = (UserInterfaceObject)Game.objectMap.get("availableworkersvalue");
		 textObj.setElementText(Integer.toString(Game.player.availableWorkers));
	}
	public void employWorker() {
		availableWorkers--;
		UserInterfaceObject textObj = (UserInterfaceObject)Game.objectMap.get("availableworkersvalue");
		textObj.setElementText(Integer.toString(Game.player.availableWorkers));
	}
	
	public void tick() {
		labourCost = (workers.doubleValue() * 2);
		money = money - labourCost;
	}

}
