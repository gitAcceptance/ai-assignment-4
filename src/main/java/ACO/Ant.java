



package ACO;

import java.util.ArrayList;

public class Ant {
	ArrayList<City> visitedCities;
	City start;
	City goal;
	City currentLocation;
	
	public Ant(City start, City goal) {
		visitedCities = new ArrayList<City>();
		this.start = start;
		this.currentLocation = start;
		this.goal = goal;
	}
	
	public boolean isAtGoal() {
		if (currentLocation.equals(goal)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}











