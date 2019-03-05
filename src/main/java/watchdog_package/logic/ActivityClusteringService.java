package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.ActivityCluster;
import main.java.watchdog_package.seviceClasses.ActivityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityClusteringService {
    public final double MIN_MODERATE_SPEED_IN_MPS = 0;
    public final double MAX_MODERATE_SPEED_IN_MPS = 3; //10.8 kph
    public final double MIN_STRENUOUS_SPEED_IN_MPS = 3;
    public final double MAX_STRENUOUS_SPEED_IN_MPS = 12.5; //45 kph
    public final double MIN_RIDE_SPEED_IN_MPS = 13;
    public final double MAX_RIDE_SPEED_IN_MPS = 55;

    private Map<ActivityType, ActivityCluster> activityClusters;

    public ActivityClusteringService(){
        activityClusters = new HashMap<>();
    }

    private void initClusters(){
        activityClusters.clear();

        activityClusters.put(ActivityType.MODERATE, new ActivityCluster(MIN_MODERATE_SPEED_IN_MPS,MAX_MODERATE_SPEED_IN_MPS));
        activityClusters.put(ActivityType.STRENUOUS, new ActivityCluster(MIN_STRENUOUS_SPEED_IN_MPS,MAX_STRENUOUS_SPEED_IN_MPS));
        activityClusters.put(ActivityType.RIDE, new ActivityCluster(MIN_RIDE_SPEED_IN_MPS,MAX_RIDE_SPEED_IN_MPS));
    }

    private long totalDuration(List<Long> durationList){
        long totalDuration = 0;
        for(Long duration : durationList){
            totalDuration+= duration;
        }
        return totalDuration;
    }

    private void groupPoints(List<Location> locationList){
        for(int locationIndex = 0; locationIndex < locationList.size() - 1; locationIndex++){
            Location currentLocation = locationList.get(locationIndex);
            Location nextLocation = locationList.get(locationIndex + 1);
            double speed = LocationMethods.speedInMps(currentLocation,nextLocation);

            for(ActivityType currentActivity : activityClusters.keySet()) {
                ActivityCluster currentCluster = activityClusters.get(currentActivity);
                if(currentCluster.isSpeedInRange(speed)) {
                    currentCluster.updateDuration(LocationMethods.timeDiffInSeconds(currentLocation,nextLocation));
                }
            }
        }
    }

    /*
    private void calculateClustersCenters(){
        for (ActivityCluster cluster : activityClusters) {
            double speedSumInCurrentCluster = 0;
            for (ActivityPoint point : points) {
                if(point.getMyCluster() == cluster.getId()){
                    speedSumInCurrentCluster+= point.getSpeed();
                    cluster.increaseNumOfPoints();
                }
            }
            if(cluster.getNumOfPoints() > 0) {
                cluster.setSpeed(speedSumInCurrentCluster / cluster.getNumOfPoints());
            }
        }
    }
    */

    public Map<ActivityType, ActivityCluster> clusterActivities(List<Location> locationList){
        initClusters();
        groupPoints(locationList);

        return activityClusters;
    }
}