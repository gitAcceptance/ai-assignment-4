



package ACO;

import java.util.ArrayList;
import java.util.HashSet;

public class Ant {
	ArrayList<City> visitedCities; // TODO make this keep track of traversed links,rather than visited cities
	MiddleEarth map;
	City start;
	City goal;
	City currentLocation;
	
	public Ant(City start, City goal, MiddleEarth m) {
		visitedCities = new ArrayList<City>();
		this.start = start;
		this.currentLocation = start;
		this.goal = goal;
		this.map = m;
	}
	
	public void step() {
		// TODO make sure the ants try to no go back to a place they've already visited
		// TODO add the new city to the list of cities you've visited
		Road smelliestRoad = null;
		for (Road r : map.getRoads(currentLocation)) {
			if (smelliestRoad == null) {
				smelliestRoad = r;
			} else if (r.getPheromoneLevel() > smelliestRoad.getPheromoneLevel()) {
				smelliestRoad = r;
			}
		}
		// This part ensures I select the correct next city.
		City nextCity = smelliestRoad.getSource();
		if (nextCity.equals(currentLocation)) {
			nextCity = smelliestRoad.getTarget();
		}
		this.currentLocation = nextCity;
	}
	
	public boolean isAtGoal() {
		if (currentLocation.equals(goal)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}











