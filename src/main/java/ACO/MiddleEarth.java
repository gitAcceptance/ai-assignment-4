/**
 *  Filename: MiddleEarth.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment: 3
 *  Due: 10/24/2016, 11:00 PM
 **/

package ACO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.graph.SimpleGraph;

public class MiddleEarth {
    
    // Create a SimpleGraph, using JGraphT library
    public SimpleGraph<City, Road> world;
    
    /**
    *   Constructor for the MiddleEarth object
    *
    *   @param Hashmap of cities (nodes)
    *   @param Connections to those cities (edges)
    *   
    **/
    public MiddleEarth(HashMap<String, City> cities, ArrayList<Connection> connections) {
        world = new SimpleGraph<City, Road>(Road.class);
        
        // Add vertexes to the nodes (cities)
        for (City c : cities.values()) {
            world.addVertex(c);
        }
        
        // Add edges (roads) to the cities (nodes)
        for (Connection c : connections) {
            Road r = new Road(c.getDistance(), c.getRoadQuality(), c.getRiskLevel());
            world.addEdge(c.getFrom(), c.getTo(), r);
        }
    }

    /**
    *   Determines the distance between 2 cities
    *
    *   @param The city you want the distance from
    *   @param The city you want the distance to
    *   @return The distance between two cities
    *   
    **/
    public int distanceBetween(City from, City to) {
        return world.getEdge(from, to).getDistance();
    }
    
    /**
    *   Determines the quality between 2 cities
    *
    *   @param The city you want the quality from
    *   @param The city you want the quality to
    *   @return The quality between two cities
    *   
    **/
    public int qualityBetween(City from, City to) {
        return world.getEdge(from, to).getDistance();
    }

    /**
    *   Determines the risk between 2 cities
    *
    *   @param The city you want the risk from
    *   @param The city you want the risk to
    *   @return The risk between two cities
    *   
    **/
    public int riskBetween(City from, City to) {
        return world.getEdge(from, to).getDistance();
    }

    /**
    *   Hashset of the roads attached to a given city
    *
    *   @param The city of the desired edges
    *   @return The hashset of roads attached to the city
    *   
    **/
    public HashSet<Road> getRoads(City location) {
        HashSet<Road> roads = new HashSet<Road>(world.edgesOf(location));
        return roads;
    }
    
    /**
     *   Returns the number of cities on the map
     *
     *   @return int value representing the number of cities in the graph
     *   
     **/
     public int numberOfCities() {
         return world.vertexSet().size();
     }
}
