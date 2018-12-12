package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Stop;
import main.java.watchdog_package.seviceClasses.ActivityCluster;
import main.java.watchdog_package.seviceClasses.ActivityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityStatisticsService {

    private Map<ActivityType, Long> initStatistics(){
        Map<ActivityType, Long> activitiesDuration = new HashMap<>();
        activitiesDuration.put(ActivityType.MODERATE,(long)0);
        activitiesDuration.put(ActivityType.STRENUOUS,(long)0);
        activitiesDuration.put(ActivityType.RIDE,(long)0);
        return activitiesDuration;
    }

    private long getRestDuration(List<Stop> stops, double rideDurationInSec){
        long duration = 0;
        for(Stop stop : stops){
            duration+= LocationMethods.timeDiffInSeconds(stop.getStartTime(), stop.getEndTime());
        }
        return duration;
    }

    private Map<ActivityType, Long> getActivitiesDuration(List<Map<ActivityType, ActivityCluster>> activities, List<Stop> stops){
        Map<ActivityType, Long> activitiesDuration = initStatistics();
        for(Map<ActivityType, ActivityCluster> activitiesMap : activities){
            for(ActivityType currentActivity : activitiesMap.keySet()){
                Long duration = activitiesDuration.get(currentActivity);
                duration += activitiesMap.get(currentActivity).getActivityDurationInSec();
                activitiesDuration.put(currentActivity, duration);
            }
        }
        activitiesDuration.put(ActivityType.REST, getRestDuration(stops, activitiesDuration.get(ActivityType.RIDE)));
        //activitiesDuration.remove(ActivityType.RIDE);

        return activitiesDuration;
    }

    //TODO: get StopList as a 2nd parameter and calculate RIDE as REST
    public void calculateStatistics(List<Map<ActivityType, ActivityCluster>> activities, List<Stop> stops){
        Map<ActivityType, Long> activitiesDuration = getActivitiesDuration(activities, stops);
        for(ActivityType currentActivityType : activitiesDuration.keySet()){
            System.out.println("Activity: "+currentActivityType+" duration: "+ activitiesDuration.get(currentActivityType));
        }
    }
}
