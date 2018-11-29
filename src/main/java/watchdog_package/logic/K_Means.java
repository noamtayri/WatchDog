package main.java.watchdog_package.logic;

import javafx.geometry.Pos;
import main.java.watchdog_package.entities.Location;

import java.util.List;

public class K_Means {
    private int numOfClusters;
    private List<Point> pointsList;
    private List<Cluster> clusters;
    private double radius;

    public K_Means(List<Location> locationsList, double radius){
        for (Location location: locationsList) {
            pointsList.add(new Point(location));
        }
        numOfClusters = 1;
        this.radius = radius;
    }

    private void initClusters(){
        for(int clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex++){
            clusters.add(new Cluster(clusterIndex, pointsList.get(clusterIndex).position));
        }
    }

    private boolean groupPoints(){
        boolean noChange = true;
        for (Point point:
             pointsList) {
            double minDistance = distance(point.position, clusters.get(0).center);
            int closestClusterIndex = 0;
            for(int clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex++){
                double currentDistance = distance(point.position, clusters.get(clusterIndex).center);
                if(currentDistance < minDistance){
                    minDistance = currentDistance;
                    closestClusterIndex = clusterIndex;
                }
            }
            if(point.myCluster != closestClusterIndex){
                point.myCluster = closestClusterIndex;
                noChange = false;
            }
        }
        return noChange;
    }

    // distance returned in METERS
    private double distance(Position p1, Position p2) {
        int r = 6371; //Radius of the earth in km
        double dLat = LocationMethods.deg2rad(p2.lat - p1.lat);
        double dLon = LocationMethods.deg2rad(p2.lon - p1.lon);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(LocationMethods.deg2rad(p1.lat)) * Math.cos(LocationMethods.deg2rad(p2.lat)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = r * c;

        return d * 1000;
    }




    private class Cluster{
        private int id;
        private int numOfPoints;
        private Position center;

        public Cluster(int id, Position position){
            this.id = id;
            numOfPoints = 0;
            center = position;
        }
    }

    private class Point{
        private Position position;
        private int myCluster;

        public Point(Location l){
            this.position = new Position(l.lat, l.lon);
            myCluster = -1;
        }
    }

    private class Position{
        public double lat;
        public double lon;
        public Position(double let, double lon){
            this.lat = let;
            this.lon = lon;
        }
    }
}
