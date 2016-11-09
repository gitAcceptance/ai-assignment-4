 



package ACO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
	
	// grab all the roads we can travel from a specific node 
	
	public void step() {
		// TODO make sure the ants try to no go back to a place they've already visited

		HashSet<Road> connections = map.getRoads(currentLocation);
		
		HashMap<Road, Double> weights = new HashMap<Road, Double>();
		HashMap<Road, Double> probabilities = new HashMap<Road, Double>();
		HashMap<Double, Road> decider = new HashMap<Double, Road>();
		
		ArrayList<double> rangeHolder = new ArrayList<double>();
		
		Road nextRoad = null;
		
		int countConnections = 0;
		int choice = 0;
		double denominator = 0.0;
		
		
        for (Road r : connections) {
            
        	double tao =  Math.pow(r.getPheromoneLevel(), alpha);
        	double ada =  Math.pow((1.0/ r.getDistance()), beta);
        	
        	weights.put(r, tao * ada);  

		}
        
        // sum up the weights for each possible connection
        for (Road r : connections) {
            denominator += weights.get(r); 
            countConnections++;
        }
        
        		
        // does the probability calculation and stores the probability  
        // to choose each possible road into the Map of probabilities
        for (Road r : connections) probabilities.put(r, weights.get(r) / denominator);
        
        
        for (Road r : connections) decider.put(weights.get(r), r);
        
        // places a range representation of the decimal probability into 
        // an array to create a range to random choose from 
        for (Road r : connections) rangeHolder.add(weights.get(r));
        
        
        
        Arrays.sort(rangeHolder);
        

        for (int i = 0; i < countConnections; i++) {
            if (rand.nextDouble() > rangeHolder.get(i)) {
                choice = i;

            }                
        }
            
        
        
        
            
        
		
      
		
		Road nextRoad = null;
		
		
		// This part ensures I select the correct next city.
		City nextCity = nextRoad.getSource();
		if (nextCity.equals(currentLocation)) {
			nextCity = nextRoad.getTarget();
		}
		this.currentLocation = nextCity;
		
		traversedLinks.add(nextRoad);
						
	}
	
	public boolean isAtGoal() {
		if (currentLocation.equals(goal)) {
			return true;
		} else {
			return false;
		}
	}			
}











