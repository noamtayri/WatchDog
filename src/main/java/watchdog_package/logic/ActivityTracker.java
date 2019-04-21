package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.ActivityType;
import main.java.watchdog_package.seviceClasses.Log;
import main.java.watchdog_package.seviceClasses.TimeLine;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ActivityTracker {
    public static List<Log> analyzeData() throws IOException {
        List<Location> locationList = FileHandle.readFromJSON("C:\\IdeaProjects\\WatchDog\\json.json");

        ActivityTrackerService activityTrackerService = new ActivityTrackerService(locationList);
        return activityTrackerService.analyzeData().getLogList();
    }

    public static Map<ActivityType,Long> getStatistics(List<Log> logList) {
        return ActivityStatisticsService.calculateStatistics(logList);
    }
}