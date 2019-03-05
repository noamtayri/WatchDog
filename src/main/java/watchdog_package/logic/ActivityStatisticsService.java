package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Stay;
import main.java.watchdog_package.entities.Movement;
import main.java.watchdog_package.seviceClasses.ActivityCluster;
import main.java.watchdog_package.seviceClasses.ActivityType;
import main.java.watchdog_package.seviceClasses.LabeledMovement;

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

    private long getRestDuration(List<Stay> StayList, double rideDurationInSec){
        long duration = 0;
        for(Stay stay : StayList){
            duration+= LocationMethods.timeDiffInSeconds(stay.getStartTime(), stay.getEndTime());
        }
        duration+= rideDurationInSec;
        return duration;
    }

    private Map<ActivityType, Long> getActivitiesDuration(List<LabeledMovement> activities, List<Stay> stayList){
        Map<ActivityType, Long> activitiesDuration = initStatistics();
        for(LabeledMovement activity : activities){
            Long duration = activitiesDuration.get(activity.getActivityType());
            duration += activity.getActivityDuration();
            activitiesDuration.put(activity.getActivityType(), duration);
        }
        activitiesDuration.put(ActivityType.REST, getRestDuration(stayList, activitiesDuration.get(ActivityType.RIDE)));
        //activitiesDuration.remove(ActivityType.RIDE);

        return activitiesDuration;
    }

    private long getTotalDuration(Map<ActivityType, Long> activities){
        long duration = 0;
        for(Long d : activities.values())
            duration+= d;
        return duration;
    }

    public void calculateStatistics(List<LabeledMovement>  activities, List<Stay> stayList){
        System.out.println("Calculating Statistics...");
        Map<ActivityType, Long> activitiesDuration = getActivitiesDuration(activities, stayList);
        for(ActivityType currentActivityType : activitiesDuration.keySet()){
            System.out.println("Activity: "+currentActivityType+" duration: " + activitiesDuration.get(currentActivityType)+ " Seconds");
            //System.out.println("Activity: "+currentActivityType+" part: " + ((activitiesDuration.get(currentActivityType) * 100) / getTotalDuration(activitiesDuration))+ "%");
        }
    }
}
