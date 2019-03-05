package main.java.watchdog_package.logic;
import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.ActivityType;
import main.java.watchdog_package.seviceClasses.TimeLine;

import java.util.ArrayList;
import java.util.List;


public class ActivityTrackerService {
/*
    public final long MAX_TIME_GAP_HOURS = 24;
    public final double MAX_AREA_RADIOS_METER = 10;
*/
    private FilterDataService filterDataService;
    private ActivitySegmentationService activitySegmentationService;
    private ActivityLabelingService activityLabelingService;
    private TimeLineService timeLineService;
    private ConfusionMatrixService confusionMatrixService;
    private ActivityStatisticsService activityStatisticsService;

    private List<Location> locationList;

    public ActivityTrackerService(List<Location> locationList){
        this.locationList = new ArrayList<>(locationList);
        filterDataService = new FilterDataService();
        activitySegmentationService = new ActivitySegmentationService();
        activityLabelingService = new ActivityLabelingService();
        timeLineService = new TimeLineService();
        confusionMatrixService = new ConfusionMatrixService(4);
        activityStatisticsService = new ActivityStatisticsService();
    }

    public void analyzeData(){
        filterDataService.filterData(locationList);
        activitySegmentationService.segmentActivity(filterDataService.getFilteredLocationList());
        activityLabelingService.labelActivities(activitySegmentationService.getMovementList());
        //timeLineService.createTimeLine(activitySegmentationService.getStayList(),activityLabelingService.getLabeledActivities());
        //TimeLine timeLine = timeLineService.getTimeLine();
        //long[][] conf = confusionMatrixService.getConfusionMatrix(timeLine, timeLine);
        //Utils.printMatrix(conf);
        activityStatisticsService.calculateStatistics(activityLabelingService.getLabeledActivities(), activitySegmentationService.getStayList());
    }
}
