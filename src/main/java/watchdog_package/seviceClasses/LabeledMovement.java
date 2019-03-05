package main.java.watchdog_package.seviceClasses;

import main.java.watchdog_package.entities.Movement;
import main.java.watchdog_package.logic.LocationMethods;

import java.util.Date;

public class LabeledMovement extends Movement{
    private ActivityType activityType;

    public LabeledMovement(Movement movement, ActivityType activityType) {
        super(movement.getLocationList());
        this.activityType = activityType;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public long getActivityDuration(){return LocationMethods.timeDiffInSeconds(getStartTime(),getEndTime());
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
}
