package main.java.watchdog_package.entities;

import main.java.watchdog_package.logic.LocationMethods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip {

    private static int numOfTrips = 0;

    private int tripId;
    private Date startTime;
    private Date endTime;
    private List<Location> locations;

    public Trip(){
        tripId = numOfTrips++;
        locations = new ArrayList<>();
    }
    public Trip(Location location){
        this();
        addLocation(location); //set startTime & endTime according to the location time
    }

    public Date getStartTime() {
        return startTime;
    }

    private void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    private void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location newLocation){
        if(locations.isEmpty()){
            setStartTime(newLocation.getTime());
        }
        locations.add(newLocation);
        setEndTime(newLocation.getTime());
    }

    public double getTotalDistance(){
        return LocationMethods.calculateTotalDistance(getLocations());
    }


    @Override
    public String toString() {
        String retString = "trip#" + tripId + "\nstart time: " + startTime + "\nend time: " + endTime;
        /*retString+= "\n";
        for (Location location : locations){
            retString+= location + "\n";
        }*/
        return retString;
    }
}
