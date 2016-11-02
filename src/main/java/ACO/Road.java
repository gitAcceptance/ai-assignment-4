/**
 *  Filename: MiddleEarth.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment: 3
 *  Due: 10/24/2016, 11:00 PM
 **/

package ACO;

import org.jgrapht.graph.DefaultEdge;

public class Road extends DefaultEdge {
    
    private final int distance;
    private float pheromoneLevel;

    /**
     * 
     */
    private static final long serialVersionUID = -9056997114300979798L;
    

    /**
    *   Constructor for the road object that is created from the connection
    *
    *   @param The distance
    *   @param The pheromone level
    *   
    **/
    public Road(int d) {
        this.distance = d;
        this.pheromoneLevel = 0;

    }
    
	/**
    *   Get the city of where the road started
    *
    *   @return The source city
    *   
    **/
    @Override
    public City getSource() {
        return (City) super.getSource();
    }
        
    /**
    *   Get the city of the roads destination
    *
    *   @return The destination of the road
    *   
    **/
    @Override
    public City getTarget() {
        return (City) super.getTarget();
    }
    
    public void evaporate(float rho) {
    	this.pheromoneLevel = this.pheromoneLevel * (1 - rho);    	
    }

    public int getDistance() {
        return distance;
    }

    public void setPheromoneLevel(int pheromoneLevel) {
		this.pheromoneLevel = pheromoneLevel;
	}
    
	public float getPheromoneLevel() {
		return pheromoneLevel;
	}

    
}