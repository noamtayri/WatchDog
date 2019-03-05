package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.seviceClasses.Cluster;
import main.java.watchdog_package.seviceClasses.EstimatedArea;

import java.util.Comparator;

public class EstimateAreaComparator implements Comparator<EstimatedArea> {
    @Override
    public int compare(EstimatedArea ea1, EstimatedArea ea2) {
        double d = ea1.getPercentage() - ea2.getPercentage();
        if(d < 0)
            return 1;
        if(d > 0)
            return -1;
        return 0;
    }
}