package main.java.watchdog_package.seviceClasses;

import java.util.*;

public class TimeLine {
    private TreeMap<Date, Log> logMap;

    public TimeLine(){
        logMap = new TreeMap<>();
    }

    public Date getStartTime(){
        return logMap.firstEntry().getValue().getStartTime();
    }

    public Date getEndTime(){
        return logMap.lastEntry().getValue().getEndTime();
    }

    public ActivityType getActivityAtTime(Date time){
        Log currentLog = logMap.floorEntry(time).getValue();
        ActivityType activityType = currentLog.getActivityType();

        return activityType;
    }

    public Date getNextTimeStamp(Date currentTime){
        Date ret;
        Log prevLog = logMap.floorEntry(currentTime).getValue();

        if(currentTime.compareTo(prevLog.getEndTime()) >= 0){
            Log nextLog = logMap.higherEntry(currentTime).getValue();
            ret = nextLog.getStartTime();
        }
        else{
            ret = prevLog.getEndTime();
        }

        return ret;
    }

    public boolean addLog(Log log){
        boolean success = true;

        if(!logMap.isEmpty()){
            Log lastLog = logMap.lastEntry().getValue();
            if(lastLog.getEndTime().compareTo(log.getStartTime()) > 0){
                success = false;
            }
        }
        if(success) {
            logMap.put(log.getStartTime(), log);
        }

        return success;
    }
}
