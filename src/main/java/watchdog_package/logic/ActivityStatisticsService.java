package main.java.watchdog_package.logic;

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
    private Map<ActivityType, Long> getActivitiesDuration(List<Map<ActivityType, ActivityCluster>> activities){
        Map<ActivityType, Long> activitiesDuration = initStatistics();
        for(Map<ActivityType, ActivityCluster> activitiesMap : activities){
            for(ActivityType currentActivity : activitiesMap.keySet()){
                Long duration = activitiesDuration.get(currentActivity);
                duration += activitiesMap.get(currentActivity).getActivityDurationInSec();
            }
        }
        return activitiesDuration;
    }

    //TODO: get StopList as a 2nd parameter and calculate RIDE as REST
    public void calculateStatistics(List<Map<ActivityType, ActivityCluster>> activities){
        Map<ActivityType, Long> activitiesDuration = getActivitiesDuration(activities);
        for(ActivityType currentActivityType : activitiesDuration.keySet()){
            System.out.println("Activity: "+currentActivityType+" duration: "+ activitiesDuration.get(currentActivityType));
        }
    }
}
