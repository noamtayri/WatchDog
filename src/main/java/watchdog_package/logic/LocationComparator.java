package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.util.Comparator;

public class LocationComparator implements Comparator<Location> {
    @Override
    public int compare(Location l1, Location l2) {
        return l1.getTime().compareTo(l2.getTime());
    }
}