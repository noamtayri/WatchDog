package main.java.watchdog_package.layout;

import main.java.watchdog_package.logic.*;
import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.EstimatedArea;

import java.io.IOException;
import java.util.*;

public class LocationPredictionUI {

    public static void predictLocationUI(Location lastKnownLocation) throws IOException {
        List<EstimatedArea> estimateAreas = LocationPrediction.predictLocation(lastKnownLocation);
        System.out.println();
        if( estimateAreas == null){
            System.out.println("predictLocation algorithm failed");
            return;
        }
        Collections.sort(estimateAreas, new EstimateAreaComparator());
        System.out.println("acording to the historical data, from the given location and time,\nthe probability(%) areas are:");
        for (EstimatedArea estimatedArea: estimateAreas) {
            System.out.println(estimatedArea);
        }
    }
}
