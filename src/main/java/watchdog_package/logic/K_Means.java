package main.java.watchdog_package.logic;

import javafx.geometry.Pos;
import main.java.watchdog_package.entities.Location;

import java.util.List;

public class K_Means {
    private int numOfClusters;
    private List<Point> pointsList;
    private List<Cluster> clusters;

    public K_Means(List<Location> locationsList){
        for (Location location: locationsList) {
            pointsList.add(new Point(location));
        }
        numOfClusters = 1;
    }

    public void initClusters(){
        for(int clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex++){
            clusters.add(new Cluster(clusterIndex, pointsList.get(clusterIndex).position));
        }
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
        public double let;
        public double lon;
        public Position(double let, double lon){
            this.let = let;
            this.lon = lon;
        }
    }
}
