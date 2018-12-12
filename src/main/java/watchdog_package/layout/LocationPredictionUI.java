package main.java.watchdog_package.layout;

import main.java.watchdog_package.logic.FileHandle;
import main.java.watchdog_package.logic.LocationMethods;
import main.java.watchdog_package.logic.LocationPrediction;
import main.java.watchdog_package.logic.LocationPredictionService;
import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.EstimatedArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationPredictionUI {

    public static void predictLocationUI(Location lastKnownLocation) throws IOException {
        List<EstimatedArea> estimateAreas = LocationPrediction.predictLocation(lastKnownLocation);
        System.out.println();
        if( estimateAreas == null){
            System.out.println("predictLocation algorithm failed");
            return;
        }
        System.out.println("acording to the historical data, from the given location and time,\nthe probability(%) areas are:");
        for (EstimatedArea estimatedArea: estimateAreas) {
            System.out.println(estimatedArea);
        }
    }
}
