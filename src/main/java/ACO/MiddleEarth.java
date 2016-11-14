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
    *   @param cities Hashmap of cities (nodes)
    *   @param connections Connections to those cities (edges)
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
            Road r = new Road(c.getDistance());
            world.addEdge(c.getFrom(), c.getTo(), r);
        }
    }

    /**
    *   Determines the distance between 2 cities
    *
    *   @param from The city you want the distance from
    *   @param to The city you want the distance to
    *   @return The distance between two cities
    *   
    **/
    public int distanceBetween(City from, City to) {
        return world.getEdge(from, to).getDistance();
    }
    
    /**
    *   Determines the pheromone Level between 2 cities
    *
    *   @param from The city you want the distance from
    *   @param to The city you want the distance to
    *   @return The pheromone between two cities
    *   
    **/
    public double pheromoneBetween(City from, City to) {
        return world.getEdge(from, to).getPheromoneLevel();
    }
    
    /**
     *   Returns a reference to the road between the given cities.
     *
     *   @param from The city at one end of the road
     *   @param to The city at the other end of the road
     *   @return The risk between two cities
     *   
     **/
     public Road getRoad(City from, City to) {
         return world.getEdge(from, to);
     }

    /**
    *   Returns a Hashset containing references the roads attached to a given city.
    *
    *   @param location The city of the desired edges
    *   @return The hashset of roads attached to the city
    *   
    **/
    public HashSet<Road> getRoads(City location) {
        HashSet<Road> roads = new HashSet<Road>(world.edgesOf(location));
        return roads;
    }
    
    /**
     *   Returns a Hashset containing references the all the roads.
     *
     *   @return The hashset of all the roads
     *   
     **/
     public HashSet<Road> getAllRoads() {
         HashSet<Road> roads = new HashSet<Road>(world.edgeSet());
         return roads;
     }
     
     /**
      *   Returns a Hashset containing references the all cities connected to the given city
      *   
      *   @param location The city you want to know the neighbors of.
      *
      *   @return The hashset of all the cities connected to the given city.
      *   
      **/
      public HashSet<City> getAllCitiesConnectedTo(City location) {
          HashSet<City> cities = new HashSet<City>();
          for (Road r : world.edgesOf(location)) {
              cities.add(r.getSource());
              cities.add(r.getTarget());
          }
          return cities;
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
