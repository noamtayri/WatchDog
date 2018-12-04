package main.java.watchdog_package.entities;

import java.util.Date;

public class Stop {
    private Position position;
    Date startTime;
    Date endTime;


    public Stop(Position position, Date startTime, Date endTime) {
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return position + "\nstart time: "+startTime + "\n" +"end time: "+endTime + "\n";
    }
}
