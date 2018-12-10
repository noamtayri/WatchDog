package main.java.watchdog_package.seviceClasses;

public class ActivityPoint {
    private int id;
    private double speed;
    private int myCluster;

    public ActivityPoint(int id, double speed){
        this.id = id;
        this.speed = speed;
        myCluster = -1;
    }

    public int getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public int getMyCluster() {
        return myCluster;
    }

    public void setMyCluster(int myCluster) {
        this.myCluster = myCluster;
    }
}

