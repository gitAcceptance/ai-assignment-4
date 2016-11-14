
package ACO;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ACO {
    
    HashMap<String, City> cities;
    ArrayList<Connection> connections;
    MiddleEarth antMap;
    Random rand;
    
    double alpha, beta, rho, q;
    
    public ACO(double alpha, double beta, double rho, double q) {
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

                // Add city object to arraylist
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
    public ArrayList<City> optimize(City start, City end, double alpha, double beta, double rho, double q) {
    	// TODO Implement this.
    	return null;
    }
    
    /**
     * places random initial pheromone levels across every edge 
     */

    public void initPheromones() {
    	for (Road r : this.antMap.getAllRoads()) {
    	    //r.setPheromoneLevel(rand.nextDouble() * 3.0); // TODO Is this correct?
    	    r.setPheromoneLevel(100.0000000000000);
    	}	
    }
    
    public void applyPheromones(ArrayList<Ant> ants) {
        // TODO apply the pheromones
        for (Ant a : ants) {
            double pheromonesPerRoad = this.q / a.getTraversedLinks().size();
            
            if (a.getTraversedLinks().size() > 30) continue;
            
            for (Road r : a.getTraversedLinks()) {
                r.setPheromoneLevel(r.getPheromoneLevel() + pheromonesPerRoad);
            }
        }
    }
    
    public void evaporatePheromones() {
    	for (Road r : this.antMap.getAllRoads()) {
    	    r.evaporate(rho);
    	}
    }

    public void run() {

    	City newCity = null;
        Connection newConnection = null;

        readInFileEstimatedDistanceToGoal(newCity);
        readInPointToPointInformation(newConnection);
        
        this.antMap = new MiddleEarth(cities, connections);
        this.initPheromones();
        
        ArrayList<Ant> antPop = new ArrayList<Ant>(); // init empty ant population
        

        int cycle = 0;
        
        while (cycle < 100) {
            antPop.clear();
            for (int i = 0; i < 10; i++) { // add 10 ants to the population
                // TODO don't forget this is set to one ant
                antPop.add(new Ant(this.cities.get("Blue_Mountains"), this.cities.get("Iron_Hills"), antMap, alpha, beta));
            }
            for (Ant a : antPop) {
                a.takeTrip();
            }
            
            this.applyPheromones(antPop);
            System.out.println();
            for (Road r : this.antMap.getAllRoads()) {
                //System.out.println(r.getSource().toString() + " <-" + r.getPheromoneLevel() + "-> " + r.getTarget().toString());
            }
            
            this.evaporatePheromones();
            System.out.println();
            for (Road r : this.antMap.getAllRoads()) {
                System.out.println(r.getSource().toString() + " <-" + r.getPheromoneLevel() + "-> " + r.getTarget().toString());
            }
            
            cycle++;
        }
        
        System.out.println("we made it");
        FileWriter fw = null;
        PrintWriter writer = null;
        try {
            fw = new FileWriter("output.txt", true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        int antLabel = 1;
        for (Ant a : antPop) {
            if (antLabel == 1) {
                try {
                    writer = new PrintWriter(fw);
                    writer.println("*************************************************");
                    writer.println("Alpha:" + this.alpha + " Beta:" + this.beta + " Rho:" + this.rho);
                    writer.println("Path length: " + a.visitedCities.size());
                    for (int i = 0; i < a.visitedCities.size(); i++) {
                        if (!(i == a.visitedCities.size() - 1)) {
                            writer.print(a.visitedCities.get(i) + " -> ");
                        } else {
                            writer.print(a.visitedCities.get(i));
                        }
                    }
                    writer.println();
                }
                catch (Exception e) {
                    System.out.println("output.txt not created");
                    e.printStackTrace();
                }
            }
            
            
            
            System.out.println("Ant " + antLabel + ": ");
            System.out.println("Path length: " + a.visitedCities.size());
            for (int i = 0; i < a.visitedCities.size(); i++) {
                if (!(i == a.visitedCities.size() - 1)) {
                    System.out.print(a.visitedCities.get(i) + " -> ");
                } else {
                    System.out.print(a.visitedCities.get(i));
                }
            }
            antLabel++;
            System.out.println();
        }
        
        writer.close();
        
        
    }

    public static void main(String[] args) {
        // java ACO <alpha> <beta> <rho> <q>
        ACO configuration1 = null;
    	
        if (args.length == 4) {
            try {
                configuration1 = new ACO(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            } catch (NullPointerException npe) {
                System.out.println("I, Andrew Valancius, messed something up.");
                npe.printStackTrace();
            } catch (NumberFormatException nfe) {
                System.out.println("The arguments given are formatted incorrectly.");
            }
        } else {
            System.out.println("Incorrect number of arguments supplied.");
            System.exit(0);
        }
    	//System.out.println(configuration1.alpha + ", " + configuration1.beta + ", " + configuration1.rho + ", " + configuration1.q);
        configuration1.run();
    }

    
    
}


