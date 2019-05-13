package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.Log;
import main.java.watchdog_package.seviceClasses.TimeLine;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ActivityTrackerTestService {

    public static void testActivityTracker() throws IOException, ParseException {
        TimeLineService timeLineService = new TimeLineService();
        ConfusionMatrixService confusionMatrixService = new ConfusionMatrixService(5);

        FileHandle.handleTestData();
        List<Location> locationList = FileHandle.readFromJSON("C:\\git\\WatchDog\\testData.json");
        ActivityTrackerService activityTrackerService = new ActivityTrackerService(locationList);
        TimeLine timeLine = activityTrackerService.analyzeData();
        List<Log> logList = FileHandle.readLogFromJSON("C:\\git\\WatchDog\\TestLog.json");
        timeLineService.createActualTimeLine(logList);
        long[][] conf = confusionMatrixService.getConfusionMatrix(timeLineService.getActualTimeLine(), timeLine);
        //long[][] conf = confusionMatrixService.getConfusionMatrix(timeLine, timeLine);
        Utils.printMatrix(conf);
    }


}
