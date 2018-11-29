package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocationPrediction {

    public static void predictLocation(Location lastKnownLocation) throws IOException {
        long msTimeDiff = Math.abs((new Date()).getTime() - lastKnownLocation.time.getTime());
        System.out.println("msTimeDif = " + TimeUnit.MINUTES.convert(msTimeDiff, TimeUnit.MILLISECONDS));

        List<Location> equalLastKnownLocation, possibleMatch;

        equalLastKnownLocation = LocationPredictionService.getSameLocation(lastKnownLocation);

        possibleMatch = LocationPredictionService.getLocationsPlusDeltaT(equalLastKnownLocation, msTimeDiff);

        System.out.println("list1 = " + equalLastKnownLocation.size());
        for (Location l:
                equalLastKnownLocation) {
            //l.print();
            System.out.println(l);
        }
        System.out.println("list2 = " + possibleMatch.size());
        for (Location l:
                possibleMatch) {
            //l.print();
            System.out.println(l);
        }

    }
}
