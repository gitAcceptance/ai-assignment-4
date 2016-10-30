
package ACO;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ACO {
    
    HashMap<String, City> cities = new HashMap<String, City>();
    ArrayList<Connection> connections = new ArrayList<Connection>();

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



    public ArrayList<City> ACO(City start, City end, float alpha, float beta, float rho, float q) {
    	// TODO Implement this.
    	return null;
    }



    public void run(String[] args) {

    	City newCity = null;
        Connection newConnection = null;

        readInFileEstimatedDistanceToGoal(newCity);
        readInPointToPointInformation(newConnection);
        
        MiddleEarth me = new MiddleEarth(cities, connections);
        
        
        

        
    }

    public static void main(String[] args) {
        ACO ant = new ACO();
        ant.run(args);
    }

}