package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.Cluster;
import main.java.watchdog_package.seviceClasses.EstimatedArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocationPrediction {
    private static final int LOCATION_DEVIATION_IN_METERS = 5;
    private static final int TIME_DEVIATION_IN_MIN = 120;
    private static final int CLUSTERS_RADIUS_IN_METERS = 50;

    public static List<EstimatedArea> predictLocation(List<Location> locationList, Location lastKnownLocation, long msTimeDiff) throws IOException {
        //long msTimeDiff = 300000; //to set deltaT manually (in testing cases)
        //long msTimeDiff = Math.abs((new Date()).getTime() - lastKnownLocation.getTime().getTime());
        //System.out.println("time: "+ msTimeDiff);
        //System.out.println("Time Diff in minutes = " + TimeUnit.MINUTES.convert(msTimeDiff, TimeUnit.MILLISECONDS));

        List<Location> equalLastKnownLocation, possibleMatch;

        equalLastKnownLocation = LocationPredictionService.getSameLocation(lastKnownLocation, locationList, LOCATION_DEVIATION_IN_METERS);

        if(equalLastKnownLocation.isEmpty()){
            //System.out.println("there are not enough data1");
            return null;
        }

        possibleMatch = LocationPredictionService.getLocationsPlusDeltaT(equalLastKnownLocation, locationList, msTimeDiff, TIME_DEVIATION_IN_MIN);

        if(possibleMatch.isEmpty()){
            //System.out.println("there are not enough data2");
            return null;
        }

        //System.out.println("list1 size = " + equalLastKnownLocation.size());

        //System.out.println("list2 size = " + possibleMatch.size());

        K_Means kMeans = new K_Means(possibleMatch, CLUSTERS_RADIUS_IN_METERS);
        List<Cluster> clusters = kMeans.run();

        //System.out.println("clusters size = " + clusters.size());

        List<EstimatedArea> clientEstimateAreas = new ArrayList<>();
        double percentPerLocation = 100d / possibleMatch.size();
        for (Cluster cluster:clusters) {
            clientEstimateAreas.add(new EstimatedArea(cluster.numOfPoints * percentPerLocation, CLUSTERS_RADIUS_IN_METERS, cluster.center));
        }
        return clientEstimateAreas;
    }
}
