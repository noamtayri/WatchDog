package main.java.watchdog_package.layout;

import main.java.watchdog_package.entities.Position;
import main.java.watchdog_package.logic.*;
import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.EstimatedArea;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocationPredictionUI {

    public static void predictLocationUI(List<Location> locationList, Location lastKnownLocation, long timeDiffMs) throws IOException, ParseException {
        List<EstimatedArea> estimateAreas = LocationPrediction.predictLocation(locationList,lastKnownLocation, timeDiffMs);
        System.out.println();
        if( estimateAreas == null){
            System.out.println("predictLocation algorithm failed");
            return;
        }
        Collections.sort(estimateAreas, new EstimateAreaComparator());

        System.out.println(LocationPredictionTestService.findClosestArea(new Position(31.559733, 34.605331), estimateAreas));
//        System.out.println("according to the historical data, from the given location and time,\nthe probability(%) areas are:");
//        for (EstimatedArea estimatedArea: estimateAreas) {
//            System.out.println(estimatedArea);
//        }
    }
}
