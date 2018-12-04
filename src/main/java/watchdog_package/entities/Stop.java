package main.java.watchdog_package.entities;

import java.util.Date;

public class Stop {
    static int numOfStops = 0;

    private int id;
    private Position position;
    private Date startTime;
    private Date endTime;


    public Stop(Position position, Date startTime, Date endTime) {
        id = numOfStops++;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "id = " + id + "\nposition:" + position + "\nstart time: "+startTime + "\n" +"end time: "+endTime + "\n";
    }
}
