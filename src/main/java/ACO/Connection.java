/**
 *  Filename: Connection.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Due: 10/24/2016
 **/

package ACO;

public class Connection {
    
    private final City from;
    private final City to;
    private final int distance;
    private final int roadQuality;
    private final int riskLevel;
    
    /**
    *   Constructor for the connection object from table 2 point to point info
    *
    *   @param from The city you are from
    *   @param to The city you are going to
    *   @param distance The distance from table 2
    *   @param roadQuality The road quality from table 2
    *   @param riskLevel The risk level  from table 2
    *   
    **/
    public Connection(City from, City to, int distance, int roadQuality, int riskLevel) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.roadQuality = roadQuality;
        this.riskLevel = riskLevel;
    }
    
    public City getFrom() {
        return from;
    }
    public City getTo() {
        return to;
    }
    public int getDistance() {
        return distance;
    }
    public int getRoadQuality() {
        return roadQuality;
    }
    public int getRiskLevel() {
        return riskLevel;
    }
    public String toString() {
        return "FROM: " + from.getCityName() + ", TO: " + to.getCityName() + ", DISTANCE: " + distance + ", ROAD QUALITY: " + roadQuality + ", RISK LEVEL: " + riskLevel;
    }
}