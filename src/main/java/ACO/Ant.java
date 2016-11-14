 



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
    ArrayList<Road> noFlyListRoads;
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
		visitedCities.add(start);
		this.start = start;
		this.currentLocation = start;
		this.goal = goal;
		this.map = m;
		this.alpha = alpha;
		this.beta = beta;
		this.rand = new Random();
		this.noFlyListRoads = new ArrayList<Road>();
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
	
	
	private Road getSmelliestRoadConnectedToMe() {
	    HashSet<Road> roadsConnectedToMe = map.getRoads(currentLocation);
	    Road smelliestRoad = null;
	    for (Road r : roadsConnectedToMe) {
	        if (smelliestRoad == null) {
	            smelliestRoad = r;
	        }
	        if (smelliestRoad.getPheromoneLevel() < r.getPheromoneLevel()) {
	            smelliestRoad = r;
	        }
	    }
	    return smelliestRoad;
	}
	
	
	public void step() {
	    
	    // TODO need to fix this whole fucking method
	    // start by pushing the list of probabilities into the Road object and make it an array that doesn't care about duplicates
	    // you can do this
	    
		// TODO make sure the ants try not to go back to a place they've already visited
	    
	    
	    
	    
	    
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
            //System.out.println(r.toString() + "level: " + r.getPheromoneLevel());
        	double tao =  Math.pow(r.getPheromoneLevel(), alpha);
        	double ada =  Math.pow((1.0/ r.getDistance()), beta);
        	//System.out.println("tao: " + tao + "  ada: " + ada);
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
            r.setCurrentProbability(weights.get(r) / denominator);
        }
        
        for (Double d: probabilities.values()) {
            //System.out.println(d);
        }
        
        
        
        // create inverted probability map
        for (Road r : roadsConnectedToMe) {
            valueToRoad.put(probabilities.get(r), r);
        }
        
        ArrayList<Road> alr = new ArrayList<Road>();
        for (Road r : roadsConnectedToMe) {
            if (this.noFlyListRoads.contains(r)) {
                continue;
            } else if (this.noFlyListCity.contains(r.getTarget())) {
                continue;
                
                
            } else {
                alr.add(r);
            }
        }
        Collections.sort(alr);
        
        
       
        
        int index = 0;
        for (Road r : alr) {
            roads.add(index, r);
            index++;
        }
        // So roads is now populated with the correct ordering of the roads
        
        if (roads.size() == 0) {
            this.noFlyListRoads.clear();
            this.noFlyListCity.clear();
            //System.out.println("cleared");
            return;
        }
        
        
        // time to populate intervals[]
        for (int i = 0; i < roads.size(); i++) {
            //System.out.println(probabilities.get(roads.get(i)));
            if (i == 0) {
                //intervals[i] = probabilities.get(roads.get(i));
                intervals[i] = roads.get(i).getCurrentProbability();
            } else {
                //intervals[i] = intervals[i-1] + probabilities.get(roads.get(i));
                intervals[i] = intervals[i-1] + roads.get(i).getCurrentProbability();
            }
        }
        // now intervals[] should be populated
        
        
        nextRoad = rollForNextRoad(intervals, roads);
        /*
        if (noFlyList.contains(nextRoad)) {
            for (Road r : roadsConnectedToMe) {
                if (!noFlyList.contains(r)) {
                    nextRoad = r;
                    break;
                }
            }
        }
        */
        // nextRoad now contains the next road I want to move to
        
        boolean iHaveTakenAllPaths = true;
        for (Road r : roadsConnectedToMe) {
            if (!traversedLinks.contains(r)) {
                iHaveTakenAllPaths = false;
                break;
            }
        }
        /*
        if (traversedLinks.contains(nextRoad) && !iHaveTakenAllPaths) {
            for (Road r : roadsConnectedToMe) {
                if (!traversedLinks.contains(r)) {
                    nextRoad = r;
                    break;
                }
            }
        }
        */
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // NOTE: Don't edit anything below this.
		// This part ensures I select the correct next city.
		City nextCity = nextRoad.getSource();
		if (nextCity.equals(currentLocation)) {
			nextCity = nextRoad.getTarget();
		}
		
		//System.out.println("City name: \"" + nextCity.getCityName() + "\"");
		if (nextCity.getCityName().equals("Isengard") || nextCity.getCityName().equals("Lothlorien") || nextCity.getCityName().equals("Dol_Guldur")) {
		    return;
		}
		
		noFlyListRoads.add(nextRoad);
		noFlyListCity.add(nextCity);
		
		
		this.visitedCities.add(nextCity);
        this.currentLocation = nextCity;
        traversedLinks.add(nextRoad);
        
		
		for (Road r : roadsConnectedToMe) {
            r.clearProbability();
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











