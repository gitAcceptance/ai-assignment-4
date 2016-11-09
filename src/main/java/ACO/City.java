/**
 *  Filename: City.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment: 3
 *  Due: 10/24/2016, 11:00 PM
 **/

package ACO;

import java.io.*;
import java.util.*;

public class City {

    String cityName;
    int distanceToIronHills;
    City cameFrom = null;
    int f = 0;
    int g = 0;
    int h = 0;

    /**
    *   Constructor for city
    *
    *   @param Name of the city
    *   @param Distance to iron hills
    *   
    **/
    public City(String name, int distance) {
        this.cityName = name;
        this.distanceToIronHills = distance;
    }

    /**
    *   Get the name of the city
    *
    *   @return Name of the city
    *   
    **/
    public String getCityName() {
        return cityName;
    }

    /**
    *   Get the distance to iron hills from the city
    *
    *   @return Distance to iron hills
    *   
    **/
    public int getDistanceToIronHills() {
        return distanceToIronHills;
    }

    /**
    *   Check if a city name is equal to another city name
    *
    *   @param A city object
    *   @return True if equal, false if not equal
    *   
    **/
    public boolean equals(City city) {
        if(this.cityName.equals(city.cityName)) {
            return true;
        }
        return false;
    }

    /**
    *   Displays a nicely formatted string of the city object
    *
    *   @return Formatted string
    *   
    **/
    public String toString() {
        return "CITY NAME: " + cityName;
    }

	public City getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(City cameFrom) {
		this.cameFrom = cameFrom;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
}