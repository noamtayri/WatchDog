package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Position;

import java.util.ArrayList;
import java.util.List;

public class FilterDataService {
    public final long MAX_TIME_INTERVAL_IN_MIN = 1;

    private final double[] LOCATIONS_WEIGHT = {1,2,5,2,1};

    private List<Location> extendedLocationList;
    private List<Location> filteredLocationList;

    public FilterDataService(){
        filteredLocationList = new ArrayList<>();
    }

    public List<Location> getFilteredLocationList() {
        return filteredLocationList;
    }

    private double getFilterWeightSum(){
        double sum = 0;
        for(int weightIndex = 0; weightIndex < LOCATIONS_WEIGHT.length; weightIndex++){
            sum+=LOCATIONS_WEIGHT[weightIndex];
        }
        return sum;
    }

    private void extendEdges(List<Location> locationList){
        extendedLocationList = new ArrayList<>(locationList);

        int locationListSize = locationList.size();
        int extensionSize = LOCATIONS_WEIGHT.length / 2;

        List<Location> startEdges = new ArrayList<>(locationList.subList(0,extensionSize + 1));
        extendedLocationList.addAll(0,startEdges);

        List<Location> endEdges = new ArrayList<>(locationList.subList(locationListSize - extensionSize, locationListSize));
        extendedLocationList.addAll(locationListSize,endEdges);
    }

    private boolean areLocationSuccessive(List<Location> locationList){
        boolean ret = true;
        for(int locationIndex = 0; locationIndex < locationList.size() - 1; locationIndex++) {
            Location currentLocation = locationList.get(locationIndex);
            Location nextLocation = locationList.get(locationIndex + 1);
            if (LocationMethods.timeDiffInMinutes(currentLocation, nextLocation) > MAX_TIME_INTERVAL_IN_MIN) {
                ret = false;
            }
        }
        return ret;
    }

    private Location filterLocations(List<Location> locationList){
        double latSum = 0;
        double lonSum = 0;
        for(int locationIndex = 0; locationIndex < LOCATIONS_WEIGHT.length; locationIndex++){
            Position currentPosition = locationList.get(locationIndex).getPosition();
            double currentWeight = LOCATIONS_WEIGHT[locationIndex];
            latSum+= currentPosition.getLat()*currentWeight;
            lonSum+= currentPosition.getLon()*currentWeight;
        }
        double filterWeightSum = getFilterWeightSum();
        Position filteredPosition = new Position(latSum/filterWeightSum, lonSum/filterWeightSum);

        int mainLocationIndex = (LOCATIONS_WEIGHT.length / 2) + 1;
        Location mainLocation = locationList.get(mainLocationIndex);
        mainLocation.setPosition(filteredPosition);

        return mainLocation;
    }

    public void filterData(List<Location> locationList){
        System.out.println("Filtering Data");
        extendEdges(locationList);
        int filterSize = LOCATIONS_WEIGHT.length;
        for(int locationIndex = 0; locationIndex < locationList.size(); locationIndex++) {
            List<Location> currentLocations = new ArrayList<>(extendedLocationList.subList(locationIndex, locationIndex + filterSize));
            if(areLocationSuccessive(currentLocations)){
                filteredLocationList.add(filterLocations(currentLocations));
            }
        }
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
