package main.java.watchdog_package.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import main.java.watchdog_package.entities.Position;

public class Location {
    public double lat;
    public double lon;
    public Date time;

    public Location(double lat, double lon, Date time) {
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    public Location() {
        lat = 0;
        lon = 0;
        time = null;
    }

    public static List<Position> convertLocationsToPositions(List<Location> locationList){
        List<Position> positionsList = new LinkedList<>();

        for(Location location : locationList){
            positionsList.add(new Position(location));
        }

        return positionsList;
    }

    public String toString() {
        return "lat = " + lat + "\tlon = " + lon + "\ttime = " + time;
    }

    public void print(){
        System.out.println(this.lat+ "," + this.lon);
    }
}
