 



package ACO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;

public class Ant {
	ArrayList<Road> traversedLinks;
	ArrayList<City> vistedCities;
	MiddleEarth map;
	City start, goal, currentLocation;
	double alpha, beta;
	Random rand;
	
	public Ant(City start, City goal, MiddleEarth m, double alpha, double beta) {
		traversedLinks = new ArrayList<Road>();
		vistedCities = new ArrayList<City>();
		this.start = start;
		this.currentLocation = start;
		this.goal = goal;
		this.map = m;
		this.alpha = alpha;
		this.beta = beta;
		this.rand = new Random();
	}
	

	public void takeTrip() {
	    if (this.vistedCities.size() == 100) return;
	    while (currentLocation != goal) {
	        this.step();
	    }
	}
	
	public void step() {
	    
	    
	    
		// TODO make sure the ants try not to go back to a place they've already visited
	    this.vistedCities.add(currentLocation);
	    
		HashSet<Road> roadsConnectedToMe = map.getRoads(currentLocation);
		LinkedHashMap<Double, Road> orderedRoadMap = new LinkedHashMap<Double, Road>();
		HashMap<Road, Double> weights = new HashMap<Road, Double>();
		HashMap<Road, Double> probabilities = new HashMap<Road, Double>();
		HashMap<Double, Road> valueToRoad = new HashMap<Double, Road>();
		
		ArrayList<Road> roads = new ArrayList<Road>();
		Double[] intervals = new Double[roadsConnectedToMe.size()];
		
		Road nextRoad = null;
		double denominator = 0.0;
		
		
        for (Road r : roadsConnectedToMe) {
        	double tao =  Math.pow(r.getPheromoneLevel(), alpha);
        	double ada =  Math.pow((1.0/ r.getDistance()), beta);
        	System.out.println("tao: " + tao + "  ada: " + ada);
        	weights.put(r, tao * ada);  
		}
        
        
        // sum up the weights for each possible connection
        for (Road r : roadsConnectedToMe) {
            denominator += weights.get(r); 
        }
        

        
        		
        // does the probability calculation and stores the probability  
        // to choose each possible road into the Map of probabilities
        for (Road r : roadsConnectedToMe) {
            probabilities.put(r, (weights.get(r) / denominator));
        }
        
        for (Double d: probabilities.values()) {
            System.out.println(d);
        }
        
        
        
        // create inverted probability map
        for (Road r : roadsConnectedToMe) {
            valueToRoad.put(probabilities.get(r), r);
        }
        
        ArrayList<Double> ald = new ArrayList<Double>();
        for (Double d : probabilities.values()) {
            ald.add(d);
        }
        Collections.sort(ald);
        
        for (Double d : ald) {
            orderedRoadMap.put(d, valueToRoad.get(d));
        }
        
        int index = 0;
        for (Road r : orderedRoadMap.values()) {
            roads.add(index, r);
            index++;
        }
        // So roads is now populated with the correct ordering of the roads
        
        
        // time to populate intervals[]
        for (int i = 0; i < roads.size(); i++) {
            //System.out.println(probabilities.get(roads.get(i)));
            if (i == 0) {
                intervals[i] = probabilities.get(roads.get(i));
                
            } else {
                intervals[i] = intervals[i-1] + probabilities.get(roads.get(i));
            }
        }
        // now intervals[] should be populated
        
        double roll = rand.nextDouble();
        //System.out.println("Roll: " + roll);
        for (Double d : intervals) {
            //System.out.println(d);
        }
        
        
        for (int i = 0; i < roads.size(); i++) {
            Double d = intervals[i];
            if (roll < d) {
                nextRoad = roads.get(i);
                break;
            }
        }
        // nextRoad now contains the next road I want to move to
        
		
		// This part ensures I select the correct next city.
		City nextCity = nextRoad.getSource();
		if (nextCity.equals(currentLocation)) {
			nextCity = nextRoad.getTarget();
		}
		this.currentLocation = nextCity;
		
		traversedLinks.add(nextRoad);
						
	}
	
	
	public void purge() {
	    this.traversedLinks.clear();
        this.vistedCities.clear();
	}
	
	public boolean isAtGoal() {
		if (currentLocation.equals(goal)) {
			return true;
		} else {
			return false;
		}
	}			
}











