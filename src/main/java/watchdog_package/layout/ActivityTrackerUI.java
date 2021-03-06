package main.java.watchdog_package.layout;

import main.java.watchdog_package.logic.ActivityTracker;
import main.java.watchdog_package.logic.Utils;
import main.java.watchdog_package.seviceClasses.Log;

import java.io.IOException;
import java.util.List;

public class ActivityTrackerUI {
    public void extractActivity() throws IOException {
        Utils.printLogList(ActivityTracker.analyzeData());

    }

    public void getStatistics(List<Log> logList){
        System.out.println(ActivityTracker.getStatistics(logList));
    }
}
