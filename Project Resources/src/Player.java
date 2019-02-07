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
	public Double labourCost;
	
	
	public Player() {
		
		workers = 0;
		money = 2000.0;
		labourCost = 0.0;
	}
	
	public void changeMoney(Double amount) {
		this.money = this.money + amount;
	}
	
	public void tick() {
		labourCost = (workers.doubleValue() * 2);
		money = money - labourCost;
	}

}
