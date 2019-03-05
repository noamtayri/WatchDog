package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocationPredictionService {

    public static List<Location> getSameLocation(Location location, int locationDeviationInMeters) throws IOException {
        List<Location> data = FileHandle.readFromJSON("C:\\Users\\USER\\Desktop\\Noam\\watchdog\\data\\json.json");
        data = data.subList(0,50000);
        List<Location> result = new ArrayList<>();

        for (Location l: data) {
            if(LocationMethods.distance(location.getPosition(), l.getPosition()) <= locationDeviationInMeters){
                result.add(l);
            }
        }

        return result;
    }

    public static List<Location> getLocationsPlusDeltaT(List<Location> list, long time, int timeDeviationInSec) throws IOException {
        List<Location> data = FileHandle.readFromJSON("C:\\Users\\USER\\Desktop\\Noam\\watchdog\\data\\json.json");
        data = data.subList(0,50000);
        List<Location> result = new ArrayList<>();

        for (Location l1: list) {
            Date locationPlusTime = new Date(l1.getTime().getTime() + time);
            for (Location l2: data) {
                long msDiff = Math.abs(locationPlusTime.getTime() - l2.getTime().getTime());
                if(checkIfToInsertLocation(l2, result, msDiff, timeDeviationInSec))
                    result.add(l2);
            }
        }

        return result;
    }

    public static boolean checkIfToInsertLocation(Location l2, List<Location> result, long msDiff, int timeDeviationInSec){
        int acceptableTimeDiff = 0;
        while(acceptableTimeDiff < timeDeviationInSec){
            if((TimeUnit.SECONDS.convert(msDiff, TimeUnit.MILLISECONDS)) != acceptableTimeDiff)
                acceptableTimeDiff++;
            else {
                if(result.isEmpty())
                    return true;
                else{
                    long diffBetweenLastLocation = Math.abs(result.get(result.size()-1).getTime().getTime() - l2.getTime().getTime());
                    if((TimeUnit.SECONDS.convert(diffBetweenLastLocation, TimeUnit.MILLISECONDS) > 300)){
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

}
