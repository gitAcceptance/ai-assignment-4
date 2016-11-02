 



package ACO;

import java.util.ArrayList;
import java.util.HashSet;

public class Ant {
	ArrayList<Road> traversedLinks;
	ArrayList<City> vistedCities;
	MiddleEarth map;
	City start, goal, currentLocation;
	float alpha, beta;
	
	public Ant(City start, City goal, MiddleEarth m, float alpha, float beta) {
		traversedLinks = new ArrayList<Road>();
		vistedCities = new ArrayList<City>();
		this.start = start;
		this.currentLocation = start;
		this.goal = goal;
		this.map = m;
		this.alpha = alpha;
		this.beta = beta;
	}
	
	public void step() {
		// TODO make sure the ants try to no go back to a place they've already visited

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
		
		traversedLinks.add(smelliestRoad);
		
		
		
	}
	
	public boolean isAtGoal() {
		if (currentLocation.equals(goal)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}











