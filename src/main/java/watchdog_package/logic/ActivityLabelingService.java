package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Trip;
import main.java.watchdog_package.seviceClasses.ActivityCluster;
import main.java.watchdog_package.logic.ActivityClusteringService.ActivityType;

import java.util.List;
import java.util.Map;

public class ActivityLabelingService {
    private final double RIDE_SPEED_THRESHOLD_IN_MPS = 12.5; //45 kph
    private final long RIDE_PART_THRESHOLD = (long) 0.2;

    private ActivityClusteringService activityClusteringService;

    public ActivityLabelingService(){
        activityClusteringService = new ActivityClusteringService();
    }

    private void setRide(Map<ActivityType, ActivityCluster> activities, long totalTripDurationInSec){
        for(ActivityType currentActivity : activities.keySet()){
            long currentDuration;
            if(currentActivity == ActivityType.RIDE){
                currentDuration = totalTripDurationInSec;
            }
            else{
                currentDuration = 0;
            }
            activities.get(currentActivity).setActivityDurationInSec(currentDuration);
        }
    }

    private void filterRide(Map<ActivityType, ActivityCluster> activities){
        //TODO: use Map<activityType, ActivityCluster>
        ActivityCluster ride = activities.get(ActivityType.RIDE);
        ActivityCluster strenuous = activities.get(ActivityType.STRENUOUS);

        strenuous.updateDuration(ride.getActivityDurationInSec());
        ride.setActivityDurationInSec(0);
    }

    private Map<ActivityType, ActivityCluster> determineTripType(Trip trip){
        Map<ActivityClusteringService.ActivityType, ActivityCluster> activities = activityClusteringService.clusterActivities(trip.getLocations());
        List<Location> locations = trip.getLocations();
        long totalTripDurationInSec = LocationMethods.timeDiffInSeconds(locations.get(0), locations.get(locations.size()-1));

        long rideDurationInSec = activities.get(ActivityType.RIDE).getActivityDurationInSec();

        if((rideDurationInSec / totalTripDurationInSec) > RIDE_PART_THRESHOLD){
            setRide(activities,totalTripDurationInSec);
        }
        else{
            filterRide(activities);
        }
        return activities;
    }

    public void labelActivities(List<Trip> tripList){
        for(Trip trip : tripList){
            System.out.println(trip);
            determineTripType(trip);
        }
    }
}
