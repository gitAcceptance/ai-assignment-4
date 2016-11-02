
package ACO;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ACO {
    
    HashMap<String, City> cities;
    ArrayList<Connection> connections;
    MiddleEarth antMap;
    Random rand;
    
    float alpha, beta, rho, q;
    
    public ACO(float alpha, float beta, float rho, float q) {
    	cities = new HashMap<String, City>();
        connections = new ArrayList<Connection>();
    	rand = new Random();
    	
    	this.alpha = alpha;
    	this.beta = beta;
    	this.rho = rho;
    	this.q = q;
    }
    

    public void readInFileEstimatedDistanceToGoal(City newCity) {
        try {

            InputStream resourceStream = ACO.class.getResourceAsStream("/estimatedDistanceToGoal.txt");

            Scanner inFile = new Scanner(resourceStream);

            String cityName;
            int distanceToIronHills;

            // // WHILE(The end of file is not read)
            while(inFile.hasNext()) {

                cityName = inFile.next();
                distanceToIronHills = inFile.nextInt();

                // System.out.println("City name: " + cityName + " | Distance to iron hills: " + distanceToIronHills);

                // Create new city object
                newCity = new City(cityName, distanceToIronHills);

                // Add ciiy object to arraylist
                cities.put(cityName, newCity);
            }
        }

        catch(Exception e) {
            System.out.print("estimatedDistanceToGoal.txt is not found");
            e.printStackTrace();
        }
    }

    public void readInPointToPointInformation(Connection newConnection) {
        try {

            InputStream resourceStream = ACO.class.getResourceAsStream("/pointToPointDistance.txt");

            Scanner inFile = new Scanner(resourceStream);

            String nextLine;
            String fromCityName;

            int numberOfPoints;

            String toCityName;
            int distanceInMiles;
            int roadQuality;
            int riskLevel;

            // WHILE(The end of file is not read)
            while(inFile.hasNext()) {

                fromCityName = inFile.next();

                numberOfPoints = inFile.nextInt();

                int countOfCities = 0;

                while(countOfCities < numberOfPoints) {

                    toCityName = inFile.next();

                    distanceInMiles = inFile.nextInt();

                    roadQuality = inFile.nextInt();

                    riskLevel = inFile.nextInt();

                    // System.out.println("From City: " + fromCityName + ", Num cities: " + numberOfPoints + ", To City: " + toCityName + ", Distance: " 
                    //                     + distanceInMiles + ", Road Quality: " + roadQuality + ", risk level: " + riskLevel);

                    // Create new connection object
                    newConnection = new Connection(cities.get(fromCityName), cities.get(toCityName), distanceInMiles, roadQuality, riskLevel);
                    

                    // Add connect object to arraylist
                    connections.add(newConnection);

                    countOfCities++;
                }

                if(inFile.hasNext()) {
                    inFile.nextLine();
                }
            }
            
        }
        catch(Exception e) {
            System.out.print("pointToPointDistance.txt is not found");
            e.printStackTrace();
        }
    }


    /**
     * Generate a path between the given cities using the given parameters
     * 
     * @param start The city we're starting at.
     * @param end The city we want to end up at.
     * @param alpha Weight of pheromone
     * @param beta Weight of heuristic measure
     * @param rho Trail persistence
     * @param q quantity of pheromone deposited on trail.
     * @return An ArrayList representing the path the ACO found
     */
    public ArrayList<City> optimize(City start, City end, float alpha, float beta, float rho, float q) {
    	// TODO Implement this.
    	return null;
    }

    public void initPheromones() {
    	for (Road r : this.antMap.getAllRoads()) {
    	    r.setPheromoneLevel(rand.nextInt(antMap.numberOfCities()));
    	}	
    }
    
    public void evaporatePheromones() {
    	for (Road r : this.antMap.getAllRoads()) {
    	    r.evaporate(5); // TODO pass rho into evaporate()
    	}
    }

    public void run(String[] args) {

    	City newCity = null;
        Connection newConnection = null;

        readInFileEstimatedDistanceToGoal(newCity);
        readInPointToPointInformation(newConnection);
        
        this.antMap = new MiddleEarth(cities, connections);
        
        ArrayList<Ant> antPop = new ArrayList<Ant>(); // init empty ant population
        for (int i = 0; i < 10; i++) { // add 10 ants to the population
        	antPop.add(new Ant(this.cities.get("Blue_Mountains"), this.cities.get("Iron_Hills"), antMap, alpha, beta));
        }

        // big while loop
        
        
        
        // end big while
        
    }

    public static void main(String[] args) {
        ACO configuration1 = new ACO(1, 1, 1, 1); // TODO input correct values for each run
        configuration1.run(args);
    }

}