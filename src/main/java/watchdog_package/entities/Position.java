package main.java.watchdog_package.entities;

public class Position {
    public double lat;
    public double lon;
    public Position(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public Position(Location location){
        this(location.lat,location.lon);
    }

    @Override
    public String toString() {
        return "lat = " + lat + ", lon = " + lon;
    }
}
