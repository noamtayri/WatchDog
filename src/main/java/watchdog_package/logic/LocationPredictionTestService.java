package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Position;
import main.java.watchdog_package.seviceClasses.EstimatedArea;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LocationPredictionTestService {

    public static final double MAX_DISTANCE = 100;
    public static final int TEST_SIZE = 1000;

    int successCounter = 0;
    int failureCounter = 0;
    double distanceAvg = 0;

    double failDisAvg = 0;
    double succDisAvg = 0;
    public double testPrediction() throws IOException {
        //Load all locations from DB
        List<Location> locationList = FileHandle.readFromJSON("C:\\git\\WatchDog\\json.json");
        for(int testIndex = 0; testIndex < TEST_SIZE; testIndex++) {

            //Randomly choose 2 locations
            Random rand = new Random();

            int lastLocationIndex = rand.nextInt(locationList.size());

            int requiredLocationIndex;
            long timDiff;

            int retry = 0;
            do{
                retry++;
                if(retry > 50){
                    lastLocationIndex = rand.nextInt(locationList.size());
                    retry = 0;
                }

                requiredLocationIndex = rand.nextInt(locationList.size() - lastLocationIndex) + lastLocationIndex;

                timDiff = LocationMethods.timeDiffInMilliSeconds(locationList.get(requiredLocationIndex), locationList.get(lastLocationIndex));
            }while(timDiff < 60000 || timDiff > 10800000);

            //activate the location prediction service with new location list from 0 to i

            List<Location> testedLocationList = locationList.subList(0,lastLocationIndex + 1);
            //List<EstimatedArea> estimatedAreaList = LocationPrediction.predictLocation(testedLocationList, testedLocationList.get(lastLocationIndex), timDiff);
            List<EstimatedArea> estimatedAreaList = LocationPrediction.predictLocation(locationList, locationList.get(lastLocationIndex), timDiff);

            //calculate the distance between the real location and the closest area
            if(estimatedAreaList != null && estimatedAreaList.size() > 2) {
                Position realPosition = locationList.get(requiredLocationIndex).getPosition();
                EstimatedArea closestArea = findClosestArea(realPosition, estimatedAreaList);
                double distanceFromClosestArea = LocationMethods.distance(realPosition, closestArea.getCenter());
                System.out.println();
                System.out.println("test #"+testIndex);
                System.out.println("Time Diff: " + TimeUnit.MINUTES.convert(timDiff, TimeUnit.MILLISECONDS)+"min");
                System.out.println("estimated area list size: " + estimatedAreaList.size());
                System.out.println("distance: " + distanceFromClosestArea +"meter");

                distanceAvg+=distanceFromClosestArea;

                //does the distance smaller than 100m?
                if(distanceFromClosestArea < (MAX_DISTANCE + closestArea.getRadius())){
                    successCounter++;
                    succDisAvg+=distanceFromClosestArea;
                }
                else {
                    failureCounter++;
                    failDisAvg+=distanceFromClosestArea;
                    System.out.println();
                    System.out.println("Failure!!");
                    System.out.println("Last known location: ");
                    System.out.println(locationList.get(lastLocationIndex));
                    System.out.println("Real location:");
                    System.out.println(locationList.get(requiredLocationIndex));
                    System.out.println("Distance between the last known location to the real location: "+ LocationMethods.distance(locationList.get(lastLocationIndex).getPosition()
                            ,locationList.get(requiredLocationIndex).getPosition()) + "meter");
                    System.out.println();
                }
            }
            else{
                testIndex--;
            }

        }

        double successRate = (100*((double)successCounter))/(TEST_SIZE);

        System.out.println();
        if(successRate >= 90) {
            System.out.println("Test Passed!! :)");
        }else{
            System.out.println("Test Failed!! :(");
        }

        System.out.println("succ: "+successCounter + ", fail: " + failureCounter);

        System.out.println("Average distance between the estimated location to the real location: " + (distanceAvg/TEST_SIZE)+"meter");
//        System.out.println("succ DisAvg = " + (succDisAvg/successCounter));
//        System.out.println("fail DisAvg = " + (failDisAvg/failureCounter));
        return successRate;
    }

    private EstimatedArea findClosestArea(Position realPosition, List<EstimatedArea> estimatedAreaList){
        EstimatedArea closestArea = estimatedAreaList.get(0);
        double minDistance = LocationMethods.distance(realPosition, closestArea.getCenter());
        for( EstimatedArea estimatedArea : estimatedAreaList){
            double currentDistance = LocationMethods.distance(realPosition, estimatedArea.getCenter());
            if(currentDistance < minDistance){
                minDistance = currentDistance;
                closestArea = estimatedArea;
            }
        }
        return closestArea;
    }
}
