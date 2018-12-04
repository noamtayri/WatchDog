package main.java.watchdog_package.logic;
import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Position;
import main.java.watchdog_package.entities.Stop;
import main.java.watchdog_package.logic.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ActivityTrackerService {

    public final long STAY_DURATION_IN_MIN = 5;
    public final double ROAMING_DISTANCE_IN_METER = 15;

    public final long MAX_TIME_GAP_HOURS = 24;
    public final double MAX_AREA_RADIOS_METER = 10;
    public final double RIDE_SPEED_THRESHOLD_IN_MPS = 12.5; //45 kph


    private List<Stop> stopList;

    public ActivityTrackerService(){
        stopList = new LinkedList<>();
    }

    public boolean isRide(Location l1, Location l2){
        boolean ret = false;
        double speedInMps = LocationMethods.speedInMps(l1,l2);
        if(speedInMps > RIDE_SPEED_THRESHOLD_IN_MPS){

        }

        return ret;
    }

    public boolean LackOfReporting(Location l1, Location l2){
        boolean ret = false;
        double distance = LocationMethods.distance(l1,l2);
        long timeGap = LocationMethods.timeDiffInHours(l1,l2);

        if (timeGap > MAX_TIME_GAP_HOURS){
            ret = true;
        }

        if(distance > MAX_AREA_RADIOS_METER){
            ret = true;
        }

        return ret;
    }

    public double diameter(List<Location> locationList){
        double diameter = 0;
        //System.out.println("size = " + locationList.size());
        for(int l1Index = 0; l1Index < locationList.size(); l1Index++){
            for(int l2Index = l1Index+1; l2Index < locationList.size(); l2Index++){
                //Utils.printTest();
                double distance = LocationMethods.distance(locationList.get(l1Index),locationList.get(l2Index));
                if(distance>diameter) {
                    diameter = distance;
                }
            }
        }

        return diameter;
    }

    public Position medoid(List<Location> locationList){
        List<Position> positionsList = Location.convertLocationsToPositions(locationList);
        return LocationMethods.midpoint(positionsList);
    }

    private int findNextPointIndexByStayDuration(List<Location> locationList) {
        Location currentLocation = locationList.get(0);

        boolean found = false;

        int locationIndex = 0;
        while(++locationIndex < locationList.size() && !found){
            //locationIndex++;
            double timeDiffInMinutes =
                    LocationMethods.timeDiffInMinutes(currentLocation, locationList.get(locationIndex));
            if(timeDiffInMinutes >= STAY_DURATION_IN_MIN){
                found = true;
            }
        }
        if(!found){
            locationIndex--;
        }
        return locationIndex;
    }

    private int findNextPointByRoamingDistance(List<Location> locationList){
        Location currentLocation = locationList.get(0);

        boolean found = false;
        int locationIndex = locationList.size()-1;
        while(locationIndex >= 1 && !found){
            //Utils.printTest();
            double diameter =
                    diameter(locationList.subList(0,locationIndex));
            if(diameter <= ROAMING_DISTANCE_IN_METER){
                found = true;
            }
            locationIndex--;
        }

        return locationIndex;
    }

    public void ExtractActivityPalces(List<Location> locationList){
        System.out.println("location list size = " + locationList.size());
        int locationListSize = locationList.size();
        int locationIndex = 0;
        while(locationIndex < locationListSize){
            System.out.println("location index = " + locationIndex);
            List<Location> restLocationList = locationList.subList(locationIndex,locationListSize);

            int nextPointIndex = locationIndex +
                    findNextPointIndexByStayDuration(restLocationList);

            List<Location> stopCandidateList = new ArrayList<>(locationList.subList(locationIndex,nextPointIndex+1));

            if(diameter(stopCandidateList) > ROAMING_DISTANCE_IN_METER) {
                //The dog is moving
                locationIndex++;
            }
            else{

                //TODO: define code as a function
                boolean moveOverRoamingDistance = false;
                while( !moveOverRoamingDistance && (++nextPointIndex < locationListSize)) {
                    stopCandidateList.add(locationList.get(nextPointIndex));
                    double diameter = diameter(stopCandidateList);
                    if((diameter > ROAMING_DISTANCE_IN_METER)){
                        moveOverRoamingDistance = true;
                        stopCandidateList.remove(stopCandidateList.get(stopCandidateList.size()-1));
                    }
                }
                nextPointIndex--;

                Position stopPosition = medoid(stopCandidateList);
                Date stopStartTime = locationList.get(locationIndex).time;
                Date stopEndTime = locationList.get(nextPointIndex).time;
                Stop stop = new Stop(stopPosition, stopStartTime, stopEndTime);
                stopList.add(stop);

                System.out.println("stop added: " + stop);

                locationIndex = nextPointIndex + 1;
            }
        }
    }
}
