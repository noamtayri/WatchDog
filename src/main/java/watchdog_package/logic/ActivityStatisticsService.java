package main.java.watchdog_package.logic;

import main.java.watchdog_package.seviceClasses.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityStatisticsService {

    private static Map<ActivityType, Long> initStatistics(){
        Map<ActivityType, Long> activitiesDuration = new HashMap<>();
        activitiesDuration.put(ActivityType.MODERATE,(long)0);
        activitiesDuration.put(ActivityType.STRENUOUS,(long)0);
        activitiesDuration.put(ActivityType.RIDE,(long)0);
        return activitiesDuration;
    }

    public static Map<ActivityType, Long> calculateStatistics(List<Log> logList){
        Map<ActivityType, Long> activitiesDuration = initStatistics();
        for(Log log : logList){
            Long duration = LocationMethods.timeDiffInSeconds(log.getStartTime(),log.getEndTime());
            activitiesDuration.put(log.getActivityType(),duration);
        }
        return  activitiesDuration;
    }
}
