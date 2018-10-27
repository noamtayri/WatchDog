package main.java.watchdog_package.logic.entities;

import java.util.Date;


public class Location {
    public double lat;
    public double lon;
    public Date time;

    public Location(double lat, double lon, Date time) {
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    public Location() {
        lat = 0;
        lon = 0;
        time = null;
    }

    public String toString() {
        return "lat = " + lat + "\tlon = " + lon + "\ttime = " + time;
    }
}
