package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Stop;
import main.java.watchdog_package.entities.Trip;

import java.util.List;

public class Utils {
    public static int testIndex = 0;

    public static void printTest(){
        System.out.println("test #" + testIndex++);
    }

    public static void printLocationList(List<Location> locationList){
        for(Location location : locationList){
            System.out.println(location);
        }
    }

    public static void printStopList(List<Stop> stopList){
        for(Stop stop : stopList){
            System.out.println(stop);
        }
    }

    public static void printTripList(List<Trip> tripList){
        for(Trip trip : tripList){
            System.out.println(trip);
        }
    }

}
