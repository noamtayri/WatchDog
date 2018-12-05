package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocationPrediction {
    private static final int LOCATION_DEVIATION_IN_METERS = 5;
    private static final int TIME_DEVIATION_IN_MIN = 120;
    private static final int CLUSTERS_RADIUS_IN_METERS = 50;

    public static void predictLocation(Location lastKnownLocation) throws IOException {
        //long msTimeDiff = Math.abs((new Date()).getTime() - lastKnownLocation.time.getTime());
        //System.out.println(msTimeDiff);
        long msTimeDiff = 1000000;
        //long msTimeDiff = Math.abs((new Date()).getTime() - lastKnownLocation.getTime().getTime());
        System.out.println("msTimeDif = " + TimeUnit.MINUTES.convert(msTimeDiff, TimeUnit.MILLISECONDS));

        List<Location> equalLastKnownLocation, possibleMatch;

        equalLastKnownLocation = LocationPredictionService.getSameLocation(lastKnownLocation, LOCATION_DEVIATION_IN_METERS);

        possibleMatch = LocationPredictionService.getLocationsPlusDeltaT(equalLastKnownLocation, msTimeDiff, TIME_DEVIATION_IN_MIN);

        System.out.println("list1 size = " + equalLastKnownLocation.size());
        /*for (Location l: equalLastKnownLocation) {
            //l.print();
            System.out.println(l);
        }*/
        System.out.println("list2 size = " + possibleMatch.size());
        /*for (Location l: possibleMatch) {
            //l.print();
            System.out.println(l);
        }*/

        K_Means kMeans = new K_Means(possibleMatch, CLUSTERS_RADIUS_IN_METERS);
        List<K_Means.Cluster> clusters = new ArrayList<>();
        clusters = kMeans.run();

        System.out.println("clusters size = " + clusters.size());

        System.out.println(clusters.get(0).getCenter());
        System.out.println(clusters.get(1).getCenter());
        System.out.println(clusters.get(2).getCenter());
    }
}
