 



package ACO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;

import com.sun.javafx.geom.AreaOp.AddOp;

public class Ant {
    ArrayList<City> noFlyListCity;
	ArrayList<Road> traversedLinks;
    ArrayList<City> visitedCities;
	MiddleEarth map;
	City start, goal, currentLocation;
	double alpha, beta;
	Random rand;
	
	public Ant(City start, City goal, MiddleEarth m, double alpha, double beta) {
		traversedLinks = new ArrayList<Road>();
		visitedCities = new ArrayList<City>();
		//visitedCities.add(start);
		this.start = start;
		this.currentLocation = start;
		this.goal = goal;
		this.map = m;
		this.alpha = alpha;
		this.beta = beta;
		this.rand = new Random();
		this.noFlyListCity = new ArrayList<City>();
	}
	

	public void takeTrip() {
	    while (currentLocation != goal) {
	        //System.out.println("Step Number " + this.traversedLinks.size());
	        if (this.traversedLinks.size() == 1000) return;
	        //System.out.println();
	        for (City c : this.visitedCities) {
	            //System.out.print(c.toString() + " -> ");
	        }
	        //System.out.println();
	        this.step();
	    }
	}
	
	
	public void step() {
	    HashSet<Road> roadsConnectedToMe = map.getRoads(currentLocation);
	    HashMap<Road, Double> weights = new HashMap<Road, Double>();
	    ArrayList<Road> roads = new ArrayList<Road>();
        Double[] intervals = new Double[roadsConnectedToMe.size()];
        
        this.visitedCities.add(currentLocation);
	    
	    double denominator = 0.0;
	    
	    for (Road r : roadsConnectedToMe) {
            double tao =  Math.pow(r.getPheromoneLevel(), alpha);
            double ada =  Math.pow((1.0/ r.getDistance()), beta);
            weights.put(r, tao * ada);  
        }
	    
	    
	    // sum up the weights for each possible connection
        for (Road r : roadsConnectedToMe) {
            denominator += weights.get(r); 
        }
        
        // does the probability calculation and stores the probability  
        // to choose each possible road into the Map of probabilities
        for (Road r : roadsConnectedToMe) {
            r.setCurrentProbability(weights.get(r) / denominator);
        }
	    
        ArrayList<Road> alr = new ArrayList<Road>();
        for (Road r : roadsConnectedToMe) {
            alr.add(r);
        }
        Collections.sort(alr);
        
        int index = 0;
        for (Road r : alr) {
            roads.add(index, r);
            index++;
        }
        
        // time to populate intervals[]
        for (int i = 0; i < roads.size(); i++) {
            if (i == 0) {
                intervals[i] = roads.get(i).getCurrentProbability();
            } else {
                intervals[i] = intervals[i-1] + roads.get(i).getCurrentProbability();
            }
        }
        // now intervals[] should be populated
        
        
        Road nextRoad = rollForNextRoad(intervals, roads);
        
        // NOTE: Don't edit anything below this.
        // This part ensures I select the correct next city.
        City nextCity = nextRoad.getSource();
        if (nextCity.equals(currentLocation)) {
            nextCity = nextRoad.getTarget();
        }
        
        if (visitedCities.contains(nextCity)) {
            // back up
            nextCity = visitedCities.remove(visitedCities.size()-1);   
        }
        
        noFlyListCity.add(nextCity);
        this.currentLocation = nextCity;
        traversedLinks.add(nextRoad);
        
        
        if (nextCity.cityName.equals("Iron_Hills" )) {
            this.visitedCities.add(nextCity);
        }
            
	}
	
	
	private Road rollForNextRoad(Double[] intervals, ArrayList<Road> roads) {
	    double roll = rand.nextDouble();
	    Road nextRoad = null;
	    
        for (int i = 0; i < roads.size(); i++) {
            Double d = intervals[i];
            if (roll < d) {
                nextRoad = roads.get(i);
                break;
            }
        }
        if (nextRoad == null) {
            //System.out.println(roads.size());
            nextRoad = roads.get(rand.nextInt(roads.size()));
        }
        
        return nextRoad;
	}
	
	public ArrayList<Road> getTraversedLinks() {
        return traversedLinks;
    }
	
	public void purge() {
	    this.traversedLinks.clear();
        this.visitedCities.clear();
	}
	
	public boolean isAtGoal() {
		if (currentLocation.equals(goal)) {
			return true;
		} else {
			return false;
		}
	}			
}











