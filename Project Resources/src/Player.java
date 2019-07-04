import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class Player {
	
	public Double money;
	public Integer workerCount;
	public Integer availableWorkers;
	public Double labourCost;
	public ArrayList<Unit> workers;
	
	public Player() {
		
		workerCount = 0;
		availableWorkers = 0;
		money = 2000.0;
		labourCost = 0.0;
		workers = new ArrayList<Unit>();
	}
	
	public void changeMoney(Double amount) {
		this.money = this.money + amount;
	}
	public void hireWorker() {
		this.availableWorkers++;
		this.workerCount++;
		
		Unit worker = new Unit(new Point(1,1));
		workers.add(worker);
//		Game.objectMap.addObject(ObjectType.WORLD, worker.toString(), worker);
//		Game.objectMap.addEntity(worker.toString(), worker,8);
////		worker.setProperties(new Dimension(64,32), new Point(600,200),"cube");
//		worker.setProperties("cube", false);
//		worker.setVisible(false);

	
	}
	public void employWorker() {
		availableWorkers--;
		UserInterfaceObject textObj = (UserInterfaceObject)Game.objectMap.get("availableworkersvalue");
		textObj.setElementText(Integer.toString(Game.player.availableWorkers));
	}
	
//	public void addWorker() {
//
//		Unit worker = new Unit(new Point(0,0));
//		workers.add(worker);
//		Game.objectMap.addObject(ObjectType.WORLD, worker.toString(), worker);
//		Game.objectMap.addEntity(worker.toString(), worker,8);
//		worker.setProperties(new Dimension(64,32), new Point(600,200),"cube");
//		worker.setVisible(false);
////		employWorker();
//
//
//	}
	
	public void tick() {
		labourCost = (workerCount.doubleValue() * 2);
		money = money - labourCost;
	}

}
