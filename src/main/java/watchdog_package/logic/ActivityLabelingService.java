package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Trip;

import java.util.List;

public class ActivityLabelingService {
    public final double RIDE_SPEED_THRESHOLD_IN_MPS = 12.5; //45 kph

    /*
        public boolean isRide(Location l1, Location l2){
        boolean ret = false;
        double speedInMps = LocationMethods.speedInMps(l1,l2);
        if(speedInMps > RIDE_SPEED_THRESHOLD_IN_MPS){

        }

        return ret;
    }
     */

    private double getTotalDistance(Trip trip){
        return LocationMethods.calculateTotalDistance(trip.getLocations());
    }

    private double calculateVelocityInMps(Trip trip){
        double totalDistanceInMeter = getTotalDistance(trip);
        Location firstLocation = trip.getLocations().get(0);
        Location lastLocation = trip.getLocations().get(trip.getLocations().size()-1);
        long totalTimeInSec = LocationMethods.timeDiffInSeconds(firstLocation,lastLocation);

        return (totalDistanceInMeter / totalTimeInSec);
    }

    public void determineTripType(Trip trip){
        double velocityInMps = calculateVelocityInMps(trip);

        // if(velocityInMps < )
    }

    public void labelActivities(List<Trip> tripList){

    }
}
