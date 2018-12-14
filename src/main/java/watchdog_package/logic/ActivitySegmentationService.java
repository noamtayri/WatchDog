package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Position;
import main.java.watchdog_package.entities.Stop;
import main.java.watchdog_package.entities.Trip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivitySegmentationService {
    private enum SegmentType {TRIP, STOP}

    public final long MAX_TIME_INTERVAL_IN_MIN = 2;
    public final long STAY_DURATION_IN_MIN = 5;
    public final double ROAMING_DISTANCE_IN_METER = 50;

    private List<Stop> stopList;
    private List<Trip> tripList;

    public ActivitySegmentationService(){
        stopList = new ArrayList<>();
        tripList = new ArrayList<>();
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public double diameter(List<Location> locationList){
        double diameter = 0;
        for(int l1Index = 0; l1Index < locationList.size(); l1Index++){
            for(int l2Index = l1Index+1; l2Index < locationList.size(); l2Index++){
                double distance = LocationMethods.distance(locationList.get(l1Index).getPosition(),locationList.get(l2Index).getPosition());
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
        if(!found || locationIndex == locationList.size()){
            locationIndex--;
        }
        return locationIndex;
    }

    private void setTrip(List<Location> locationList, int locationIndex, SegmentType lastActivityType){
        Trip trip = new Trip();
        Location currentLocation = locationList.get(locationIndex);

        switch(lastActivityType){
            case STOP:
                tripList.add(trip);
                if( locationIndex > 0 ) {
                    Location previousLocation = locationList.get(locationIndex - 1);
                    if(LocationMethods.timeDiffInMinutes(currentLocation,previousLocation) < MAX_TIME_INTERVAL_IN_MIN) {
                        trip.addLocation(previousLocation);
                    }
                }
                break;
            case TRIP:
                if( locationIndex > 0 ) {
                    Location previousLocation = locationList.get(locationIndex - 1);
                    if(LocationMethods.timeDiffInMinutes(currentLocation,previousLocation) > MAX_TIME_INTERVAL_IN_MIN) {
                        tripList.add(trip);
                    }
                    else{
                        int tripId = tripList.size() - 1;
                        trip = tripList.get(tripId);
                    }
                }
                break;
            default:
                //Do nothing
                break;
        }
        trip.addLocation(currentLocation);
    }

    private void setStop(List<Location> locationList, List<Location> stopCandidateList, int fromIndex, int toIndex){
        if(fromIndex > 0){
            Location firstLocation = locationList.get(fromIndex);
            Location previousLocation = locationList.get(fromIndex - 1);
            if(LocationMethods.distance(previousLocation.getPosition(), firstLocation.getPosition()) < ROAMING_DISTANCE_IN_METER){
                fromIndex--;
            }
        }
        Position stopPosition = medoid(stopCandidateList);
        Date stopStartTime = locationList.get(fromIndex).getTime();
        Date stopEndTime = locationList.get(toIndex).getTime();
        Stop stop = new Stop(stopPosition, stopStartTime, stopEndTime);
        stopList.add(stop);
    }

    private int findNextPointByRoamingDistance(List<Location> locationList, List<Location> stopCandidateList){
        int nextPointIndex = 0;
        boolean moveOverRoamingDistance = false;
        while( !moveOverRoamingDistance && (++nextPointIndex < locationList.size())) {
            stopCandidateList.add(locationList.get(nextPointIndex));
            double diameter = diameter(stopCandidateList);
            if((diameter > ROAMING_DISTANCE_IN_METER)){
                moveOverRoamingDistance = true;
                stopCandidateList.remove(stopCandidateList.get(stopCandidateList.size()-1));
            }
        }
        nextPointIndex--;
        return nextPointIndex;
    }

    public void segmentActivity(List<Location> locationList){
        System.out.println("Segmenting Data...");
        //BUG: I can get trip with one location!
        SegmentType lastActivityType = SegmentType.STOP;

        int locationListSize = locationList.size();
        int locationIndex = 0;
        while(locationIndex < locationListSize){
            List<Location> restLocationList = locationList.subList(locationIndex,locationListSize);

            int nextPointIndex = locationIndex +
                    findNextPointIndexByStayDuration(restLocationList);

            List<Location> stopCandidateList = new ArrayList<>(locationList.subList(locationIndex,nextPointIndex+1));

            if(diameter(stopCandidateList) > ROAMING_DISTANCE_IN_METER) {

                setTrip(locationList,locationIndex,lastActivityType);
                lastActivityType = SegmentType.TRIP;

                locationIndex++;
            }
            else{
                nextPointIndex = locationIndex +
                        findNextPointByRoamingDistance(restLocationList,stopCandidateList);

                setStop(locationList, stopCandidateList, locationIndex, nextPointIndex);

                locationIndex = nextPointIndex + 1;
                lastActivityType = SegmentType.STOP;
            }
        }
    }

}
