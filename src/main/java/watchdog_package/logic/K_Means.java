package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.util.ArrayList;
import java.util.List;

public class K_Means {
    private int numOfClusters;
    private List<Point> pointsList = new ArrayList<>();
    private List<Cluster> clusters = new ArrayList<>();
    private double radius;

    public K_Means(List<Location> locationsList, double radius){
        for (Location location: locationsList) {
            pointsList.add(new Point(location));
        }
        numOfClusters = 1;
        this.radius = radius;
    }

    public List<Cluster> run(){
        boolean allClustersInRadius = false;
        while(!allClustersInRadius){
            initClusters();
            boolean pointsDidNotSwitchClusters = false;
            while(!pointsDidNotSwitchClusters){
                pointsDidNotSwitchClusters = groupPoints();
                calculateClustersCenters();
            }
            allClustersInRadius = isAllClusterInRadiusRange();
            if(!allClustersInRadius){
                numOfClusters++;
                clusters.clear();
            }
        }
        return clusters;
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

    private void calculateClustersCenters(){
        for (Cluster cluster:
             clusters) {
            List<Position> positionsInCurrentCluster = new ArrayList<>();
            for (Point point:
                 pointsList) {
                if(point.myCluster == cluster.id){
                    positionsInCurrentCluster.add(point.position);
                    cluster.numOfPoints++;
                }
            }
            cluster.center = midpoint(positionsInCurrentCluster);
        }
    }

    private boolean isAllClusterInRadiusRange(){
        for (Cluster cluster:
             clusters) {
            for (Point point:
                 pointsList) {
                if(point.myCluster == cluster.id){
                    if(distance(point.position, cluster.center) > radius){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Calculation of the geographic midpoint (also known as the geographic center,
     * or center of gravity) for two or more points on the earth's surface Ref:
     * http://www.geomidpoint.com/calculation.html
     *
     * @param points
     * @return
     */
    private Position midpoint(List<Position> points) {
        double Totweight = 0;
        double xt = 0;
        double yt = 0;
        double zt = 0;
        for (Position point : points) {
            Double latitude = point.lat;
            Double longitude = point.lon;

            /**
             * Convert Lat and Lon from degrees to radians.
             */
            double latn = latitude * Math.PI / 180;
            double lonn = longitude * Math.PI / 180;

            /**
             * Convert lat/lon to Cartesian coordinates
             */
            double xn = Math.cos(latn) * Math.cos(lonn);
            double yn = Math.cos(latn) * Math.sin(lonn);
            double zn = Math.sin(latn);

            /**
             * Compute weight (by time) If locations are to be weighted equally,
             * set wn to 1
             */
            double years = 0;
            double months = 0;
            double days = 0;
            double wn = true ? 1 : (years * 365.25) + (months * 30.4375) + days;

            /**
             * Compute combined total weight for all locations.
             */
            Totweight = Totweight + wn;
            xt += xn * wn;
            yt += yn * wn;
            zt += zn * wn;
        }

        /**
         * Compute weighted average x, y and z coordinates.
         */
        double x = xt / Totweight;
        double y = yt / Totweight;
        double z = zt / Totweight;

        /**
         * If abs(x) < 10-9 and abs(y) < 10-9 and abs(z) < 10-9 then the
         * geographic midpoint is the center of the earth.
         */
        double lat = -0.001944;
        double lon = -78.455833;
        if (Math.abs(x) < Math.pow(10, -9) && Math.abs(y) < Math.pow(10, -9) && Math.abs(z) < Math.pow(10, -9)) {
        } else {

            /**
             * Convert average x, y, z coordinate to latitude and longitude.
             * Note that in Excel and possibly some other applications, the
             * parameters need to be reversed in the atan2 function, for
             * example, use atan2(X,Y) instead of atan2(Y,X).
             */
            lon = Math.atan2(y, x);
            double hyp = Math.sqrt(x * x + y * y);
            lat = Math.atan2(z, hyp);

            /**
             * Convert lat and lon to degrees.
             */
            lat = lat * 180 / Math.PI;
            lon = lon * 180 / Math.PI;
        }
        return new Position(lat, lon);
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

    public class Cluster{
        private int id;
        private int numOfPoints;
        private Position center;

        public Cluster(int id, Position position){
            this.id = id;
            numOfPoints = 0;
            center = position;
        }

        public Position getCenter() {
            return center;
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
        public Position(double lat, double lon){
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public String toString() {
            return "lat = " + lat + ", lon = " + lon;
        }
    }
}
