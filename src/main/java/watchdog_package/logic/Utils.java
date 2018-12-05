package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

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
}
