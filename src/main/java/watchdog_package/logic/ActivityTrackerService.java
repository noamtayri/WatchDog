package main.java.watchdog_package.logic;
import main.java.watchdog_package.entities.Location;

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
    private ActivityStatisticsService activityStatisticsService;

    private List<Location> locationList;

    public ActivityTrackerService(List<Location> locationList){
        this.locationList = new ArrayList<>(locationList);
        filterDataService = new FilterDataService();
        activitySegmentationService = new ActivitySegmentationService();
        activityLabelingService = new ActivityLabelingService();
        activityStatisticsService = new ActivityStatisticsService();
    }

    public void analyzeData(){
        filterDataService.filterData(locationList);
        activitySegmentationService.segmentActivity(locationList);
        activityLabelingService.labelActivities(activitySegmentationService.getTripList());
        activityStatisticsService.calculateStatistics(activityLabelingService.getLabeledActivities());
    }
}
