package main.java.watchdog_package.logic;
import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.Log;
import main.java.watchdog_package.seviceClasses.TimeLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActivityTrackerService {
    private FilterDataService filterDataService;
    private ActivitySegmentationService activitySegmentationService;
    private ActivityLabelingService activityLabelingService;
    private TimeLineService timeLineService;
    private ConfusionMatrixService confusionMatrixService;

    private List<Location> locationList;

    public ActivityTrackerService(List<Location> locationList){
        this.locationList = new ArrayList<>(locationList);
        filterDataService = new FilterDataService();
        activitySegmentationService = new ActivitySegmentationService();
        activityLabelingService = new ActivityLabelingService();
        timeLineService = new TimeLineService();
        confusionMatrixService = new ConfusionMatrixService(5);
    }

    public TimeLine analyzeData() {
        filterDataService.filterData(locationList);
        activitySegmentationService.segmentActivity(filterDataService.getFilteredLocationList());
        activityLabelingService.labelActivities(activitySegmentationService.getMovementList());
        timeLineService.createTimeLine(activitySegmentationService.getStayList(),activityLabelingService.getLabeledActivities());
        TimeLine timeLine = timeLineService.getAnalyzedTimeLine();

        return timeLine;
    }

    //This function is used for testing only
    public void activityTrackerTest() throws IOException {
        TimeLine timeLine = analyzeData();
        List<Log> logList = FileHandle.readLogFromJSON("C:\\git\\WatchDog\\Log.json");
        timeLineService.createActualTimeLine(logList);
        long[][] conf = confusionMatrixService.getConfusionMatrix(timeLineService.getActualTimeLine(), timeLine);
        //long[][] conf = confusionMatrixService.getConfusionMatrix(timeLine, timeLine);
        Utils.printMatrix(conf);

    }
}