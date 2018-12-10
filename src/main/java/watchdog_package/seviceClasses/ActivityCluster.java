package main.java.watchdog_package.seviceClasses;

public class ActivityCluster {
    //TODO: remove the activityType variable, use it out side as a Map<activityType, ActivityCluster>
    private long activityDurationInSec;
    private double minSpeed;
    private double maxSpeed;

    public ActivityCluster(double minSpeed, double maxSpeed){
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        activityDurationInSec = 0;
    }

/*
    public void setActivityType(TripType activityType) {
        this.activityType = activityType;
        switch (activityType){
            case MODERATE:
                minSpeed = 0;
                maxSpeed = 5;
                break;
            case STRENUOUS:
                minSpeed = 5;
                maxSpeed = 12.5;
                break;
            case RIDE:
                minSpeed = 12.5;
                maxSpeed = 100;
                break;
        }
    }
*/


    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public long getActivityDurationInSec() {
        return activityDurationInSec;
    }

    public void setActivityDurationInSec(long activityDurationInSec) {
        this.activityDurationInSec = activityDurationInSec;
    }

    public void updateDuration(long durationInSec){
        setActivityDurationInSec(activityDurationInSec + durationInSec);
    }

    public boolean isSpeedInRange(Double speed){
        return (speed >= getMinSpeed()) && (speed <= getMaxSpeed());
    }

/*
    @Override
    public String toString() {
        return "ID: " + activityType;// + " speed: " + speed + " numOfPoints: " + numOfPoints;
    }
*/
}
