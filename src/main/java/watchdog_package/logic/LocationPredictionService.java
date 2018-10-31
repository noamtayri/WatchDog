package main.java.watchdog_package.logic;

import main.java.watchdog_package.logic.entities.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocationPredictionService {

    public static List<Location> getSameLocation(Location location) throws IOException {
        List<Location> data = FileHandle.readFromJSON("C:\\Users\\USER\\Desktop\\Noam\\watchdog\\data\\json.json");
        List<Location> result = new ArrayList<>();

        for (Location l: data) {
            if(LocationMethods.distance(location, l) <= 3){
                result.add(l);
            }
        }

        return result;
    }

    public static List<Location> getLocationsPlusDeltaT(List<Location> list, long time) throws IOException {
        List<Location> data = FileHandle.readFromJSON("C:\\Users\\USER\\Desktop\\Noam\\watchdog\\data\\json.json");
        List<Location> result = new ArrayList<>();

        for (Location l1: list) {
            Date locationPlusTime = new Date(l1.time.getTime() + time);
            for (Location l2: data) {
                long msDiff = Math.abs(locationPlusTime.getTime() - l2.time.getTime());
                if(TimeUnit.SECONDS.convert(msDiff, TimeUnit.MILLISECONDS) < 120){
                    result.add(l2);
                }
            }
        }

        return result;
    }

}
