package main.java.watchdog_package.logic;

import main.java.watchdog_package.logic.entities.Location;

import java.util.concurrent.TimeUnit;

public class LocationMethods {

    // distance returned in METERS
    public static double distance(Location l1, Location l2) {
        int r = 6371; //Radius of the earth in km
        double dLat = deg2rad(l2.lat - l1.lat);
        double dLon = deg2rad(l2.lon - l1.lon);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(l1.lat)) * Math.cos(deg2rad(l2.lat)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = r * c;

        return d * 1000;
    }

    private static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    public static long timeDiffInDays(Location l1, Location l2) {
        long msDiff = Math.abs(l2.time.getTime() - l1.time.getTime());
        return TimeUnit.DAYS.convert(msDiff, TimeUnit.MILLISECONDS);
    }

    public static long timeDiffInHours(Location l1, Location l2) {
        long msDiff = Math.abs(l2.time.getTime() - l1.time.getTime());
        return TimeUnit.HOURS.convert(msDiff, TimeUnit.MILLISECONDS);
    }

    public static long timeDiffInMinutes(Location l1, Location l2) {
        long msDiff = Math.abs(l2.time.getTime() - l1.time.getTime());
        return TimeUnit.MINUTES.convert(msDiff, TimeUnit.MILLISECONDS);
    }

    public static long timeDiffInSeconds(Location l1, Location l2) {
        long msDiff = Math.abs(l2.time.getTime() - l1.time.getTime());
        return TimeUnit.SECONDS.convert(msDiff, TimeUnit.MILLISECONDS);
    }
}
