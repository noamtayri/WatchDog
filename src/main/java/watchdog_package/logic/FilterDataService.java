package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.util.List;

public class FilterDataService {

    public void filterData(List<Location> locationList){

    }

    /*
    public boolean LackOfReporting(Location l1, Location l2){
        boolean ret = false;
        double distance = LocationMethods.distance(l1.getPosition(),l2.getPosition());
        long timeGap = LocationMethods.timeDiffInHours(l1,l2);

        if (timeGap > MAX_TIME_GAP_HOURS){
            ret = true;
        }

        if(distance > MAX_AREA_RADIOS_METER){
            ret = true;
        }

        return ret;
    }
*/

}
